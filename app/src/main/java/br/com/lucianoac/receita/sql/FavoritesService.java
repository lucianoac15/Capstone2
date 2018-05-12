package br.com.lucianoac.receita.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import br.com.lucianoac.receita.dto.RecipeObject;

public class FavoritesService {

    private final Context context;

    @Inject
    public FavoritesService(Context context) {
        this.context = context.getApplicationContext();
    }

    public void addToFavorites(RecipeObject movie) {
        context.getContentResolver().insert(RecipesContract.MovieEntry.CONTENT_URI, movie.toContentValues());
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipesContract.COLUMN_MOVIE_ID_KEY, movie.getId());
        context.getContentResolver().insert(RecipesContract.Favorites.CONTENT_URI, contentValues);
    }

    public void removeFromFavorites(RecipeObject movie) {
        context.getContentResolver().delete(
                RecipesContract.Favorites.CONTENT_URI,
                RecipesContract.COLUMN_MOVIE_ID_KEY + " = " + movie.getId(),
                null
        );
    }

    public boolean isFavorite(RecipeObject movie) {
        boolean favorite = false;
        Cursor cursor = context.getContentResolver().query(
                RecipesContract.Favorites.CONTENT_URI,
                null,
                RecipesContract.COLUMN_MOVIE_ID_KEY + " = " + movie.getId(),
                null,
                null
        );
        if (cursor != null) {
            favorite = cursor.getCount() != 0;
            cursor.close();
        }
        return favorite;
    }
}
