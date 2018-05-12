package br.com.lucianoac.receita.screen;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import br.com.lucianoac.receita.sql.RecipesContract;


public class FavoritesGridFragment extends AbstractRecipesGridFragment {

    public static FavoritesGridFragment create() {
        return new FavoritesGridFragment();
    }

    @Override
    @NonNull
    protected Uri getContentUri() {
        return RecipesContract.Favorites.CONTENT_URI;
    }

    @Override
    protected void onCursorLoaded(Cursor data) {
        getAdapter().changeCursor(data);
    }

    @Override
    protected void onRefreshAction() {

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onMoviesGridInitialisationFinished() {
    }
}
