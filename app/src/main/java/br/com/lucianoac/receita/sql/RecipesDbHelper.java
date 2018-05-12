package br.com.lucianoac.receita.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipesDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_SCHEMA_VERSION = 4;
    private static final String SQL_DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public RecipesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipesContract.MovieEntry.SQL_CREATE_TABLE);
        db.execSQL(RecipesContract.MostPopularMovies.SQL_CREATE_TABLE);
        db.execSQL(RecipesContract.HighestRatedMovies.SQL_CREATE_TABLE);
        db.execSQL(RecipesContract.MostRatedMovies.SQL_CREATE_TABLE);
        db.execSQL(RecipesContract.Favorites.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS + RecipesContract.MovieEntry.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS + RecipesContract.MostPopularMovies.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS + RecipesContract.HighestRatedMovies.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS + RecipesContract.MostRatedMovies.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS + RecipesContract.Favorites.TABLE_NAME);
        onCreate(db);
    }
}
