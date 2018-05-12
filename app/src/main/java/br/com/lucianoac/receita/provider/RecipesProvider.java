package br.com.lucianoac.receita.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.HashSet;

import br.com.lucianoac.receita.sql.RecipesContract;
import br.com.lucianoac.receita.sql.RecipesDbHelper;

public class RecipesProvider extends ContentProvider {

    static final int MOVIES = 100;
    static final int MOVIE_BY_ID = 101;
    static final int MOST_POPULAR_MOVIES = 201;
    static final int HIGHEST_RATED_MOVIES = 202;
    static final int MOST_RATED_MOVIES = 203;
    static final int FAVORITES = 300;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private static final String FAILED_TO_INSERT_ROW_INTO = "Failed to insert row into ";
    private static final String MOVIE_ID_SELECTION =
            RecipesContract.MovieEntry.TABLE_NAME + "." + RecipesContract.MovieEntry._ID + " = ? ";


    private RecipesDbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES + "/#", MOVIE_BY_ID);

        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES + "/" +
                RecipesContract.PATH_MOST_POPULAR, MOST_POPULAR_MOVIES);
        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES + "/" +
                RecipesContract.PATH_HIGHEST_RATED, HIGHEST_RATED_MOVIES);
        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES + "/" +
                RecipesContract.PATH_MOST_RATED, MOST_RATED_MOVIES);

        uriMatcher.addURI(authority, RecipesContract.PATH_MOVIES + "/" +
                RecipesContract.PATH_FAVORITES, FAVORITES);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new RecipesDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case MOVIES:
                return RecipesContract.MovieEntry.CONTENT_DIR_TYPE;
            case MOVIE_BY_ID:
                return RecipesContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOST_POPULAR_MOVIES:
                return RecipesContract.MostPopularMovies.CONTENT_DIR_TYPE;
            case HIGHEST_RATED_MOVIES:
                return RecipesContract.HighestRatedMovies.CONTENT_DIR_TYPE;
            case MOST_RATED_MOVIES:
                return RecipesContract.MostRatedMovies.CONTENT_DIR_TYPE;
            case FAVORITES:
                return RecipesContract.Favorites.CONTENT_DIR_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        Cursor cursor;
        checkColumns(projection);
        switch (match) {
            case MOVIES:
                cursor = dbHelper.getReadableDatabase().query(
                        RecipesContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_BY_ID:
                cursor = getMovieById(uri, projection, sortOrder);
                break;
            case MOST_POPULAR_MOVIES:
                cursor = getMoviesFromReferenceTable(RecipesContract.MostPopularMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case HIGHEST_RATED_MOVIES:
                cursor = getMoviesFromReferenceTable(RecipesContract.HighestRatedMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case MOST_RATED_MOVIES:
                cursor = getMoviesFromReferenceTable(RecipesContract.MostRatedMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case FAVORITES:
                cursor = getMoviesFromReferenceTable(RecipesContract.Favorites.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            default:
                return null;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        Uri returnUri;
        long id;
        switch (match) {
            case MOVIES:
                id = db.insertWithOnConflict(RecipesContract.MovieEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = RecipesContract.MovieEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case MOST_POPULAR_MOVIES:
                id = db.insert(RecipesContract.MostPopularMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = RecipesContract.MostPopularMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case HIGHEST_RATED_MOVIES:
                id = db.insert(RecipesContract.HighestRatedMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = RecipesContract.HighestRatedMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case MOST_RATED_MOVIES:
                id = db.insert(RecipesContract.MostRatedMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = RecipesContract.MostRatedMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case FAVORITES:
                id = db.insert(RecipesContract.Favorites.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = RecipesContract.Favorites.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int rowsUpdated;
        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(RecipesContract.MovieEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int rowsDeleted;
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(RecipesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_BY_ID:
                long id = RecipesContract.MovieEntry.getIdFromUri(uri);
                rowsDeleted = db.delete(RecipesContract.MovieEntry.TABLE_NAME,
                        MOVIE_ID_SELECTION, new String[]{Long.toString(id)});

                break;
            case MOST_POPULAR_MOVIES:
                rowsDeleted = db.delete(RecipesContract.MostPopularMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case HIGHEST_RATED_MOVIES:
                rowsDeleted = db.delete(RecipesContract.HighestRatedMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case MOST_RATED_MOVIES:
                rowsDeleted = db.delete(RecipesContract.MostRatedMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITES:
                rowsDeleted = db.delete(RecipesContract.Favorites.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long id = db.insertWithOnConflict(RecipesContract.MovieEntry.TABLE_NAME,
                                null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        if (id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private Cursor getMovieById(Uri uri, String[] projection, String sortOrder) {
        long id = RecipesContract.MovieEntry.getIdFromUri(uri);
        String selection = MOVIE_ID_SELECTION;
        String[] selectionArgs = new String[]{Long.toString(id)};
        return dbHelper.getReadableDatabase().query(
                RecipesContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesFromReferenceTable(String tableName, String[] projection, String selection,
                                               String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(
                tableName + " INNER JOIN " + RecipesContract.MovieEntry.TABLE_NAME +
                        " ON " + tableName + "." + RecipesContract.COLUMN_MOVIE_ID_KEY +
                        " = " + RecipesContract.MovieEntry.TABLE_NAME + "." + RecipesContract.MovieEntry._ID
        );

        return sqLiteQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(
                    RecipesContract.MovieEntry.getColumns()));
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection.");
            }
        }
    }

}
