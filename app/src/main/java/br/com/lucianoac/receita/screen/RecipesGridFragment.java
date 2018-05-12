package br.com.lucianoac.receita.screen;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import br.com.lucianoac.receita.PopularRecipesApp;
import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.api.RecipesService;
import br.com.lucianoac.receita.api.SearchResponse;
import br.com.lucianoac.receita.api.TheRecipeDbService;
import br.com.lucianoac.receita.dto.RecipeObject;
import br.com.lucianoac.receita.util.SortHelper;
import br.com.lucianoac.receita.util.SortingDialogFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RecipesGridFragment extends AbstractRecipesGridFragment {

    private static final String LOG_TAG = "RecipesGridFragment";
    private static final int SEARCH_QUERY_DELAY_MILLIS = 400;

    @Inject
    RecipesService recipesService;
    @Inject
    SortHelper sortHelper;

    @Inject
    TheRecipeDbService theRecipeDbService;

    private RecyclerViewOnScrollListener recyclerViewOnScrollListener;
    private SearchView searchView;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RecipesService.BROADCAST_UPDATE_FINISHED)) {
                if (!intent.getBooleanExtra(RecipesService.EXTRA_IS_SUCCESSFUL_UPDATED, true)) {
                    Snackbar.make(swipeRefreshLayout, R.string.error_failed_to_update_movies,Snackbar.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                recyclerViewOnScrollListener.setLoading(false);
                updateGridLayout();
            } else if (action.equals(SortingDialogFragment.BROADCAST_SORT_PREFERENCE_CHANGED)) {
                recyclerView.smoothScrollToPosition(0);
                restartLoader();
            }
        }
    };

    public static RecipesGridFragment create() {
        return new RecipesGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ((PopularRecipesApp) getActivity().getApplication()).getNetworkComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecipesService.BROADCAST_UPDATE_FINISHED);
        intentFilter.addAction(SortingDialogFragment.BROADCAST_SORT_PREFERENCE_CHANGED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        if (recyclerViewOnScrollListener != null) {
            recyclerViewOnScrollListener.setLoading(recipesService.isLoading());
        }
        swipeRefreshLayout.setRefreshing(recipesService.isLoading());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_recipes_grid, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
        if (searchViewMenuItem != null) {
            searchView = (SearchView) searchViewMenuItem.getActionView();
            MenuItemCompat.setOnActionExpandListener(searchViewMenuItem,
                    new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            recyclerView.setAdapter(null);
                            initMoviesGrid();
                            restartLoader();
                            swipeRefreshLayout.setEnabled(true);
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            return true;
                        }
                    });
            setupSearchView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_show_sort_by_dialog:
                showSortByDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    @NonNull
    protected Uri getContentUri() {
        return sortHelper.getSortedMoviesUri();
    }

    @Override
    protected void onCursorLoaded(Cursor data) {
        getAdapter().changeCursor(data);
        if (data == null || data.getCount() == 0) {
            refreshMovies();
        }
    }

    @Override
    protected void onRefreshAction() {
        refreshMovies();
    }

    @Override
    protected void onMoviesGridInitialisationFinished() {
        recyclerViewOnScrollListener = new RecyclerViewOnScrollListener(getGridLayoutManager()) {
            @Override
            public void onLoadMore() {

                if(isOnline()) {
                    updateLayoutNoNetwork(false);
                    swipeRefreshLayout.setRefreshing(true);
                    recipesService.loadMoreMovies();
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                    updateLayoutNoNetwork(true);
                }
            }
        };
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private void setupSearchView() {
        if (searchView == null) {
            Log.e(LOG_TAG, "SearchView is not initialized");
            return;
        }
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        RxSearchView.queryTextChanges(searchView)
                .debounce(SEARCH_QUERY_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .filter(query -> query.length() > 0)
                .doOnNext(query -> Log.d("search", query))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .switchMap(query -> theRecipeDbService.searchMovies(query, null))
                .map(SearchResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecipeObject>>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error", e);
                    }

                    @Override
                    public void onNext(List<RecipeObject> movies) {
                        RecipesSearchAdapter adapter = new RecipesSearchAdapter(getContext(), movies);
                        adapter.setOnItemClickListener((itemView, position) ->
                                getOnItemSelectedListener().onItemSelected(adapter.getItem(position))
                        );
                        recyclerView.setAdapter(adapter);
                        updateGridLayout();
                    }
                });

        searchView.setOnSearchClickListener(view -> {
            recyclerView.setAdapter(null);
            recyclerView.removeOnScrollListener(recyclerViewOnScrollListener);
            updateGridLayout();
            swipeRefreshLayout.setEnabled(false);
        });
    }

    private void refreshMovies() {
        if(isOnline()) {
            updateLayoutNoNetwork(false);
            swipeRefreshLayout.setRefreshing(true);
            recipesService.refreshMovies();
        }else{
            updateLayoutNoNetwork(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showSortByDialog() {
        DialogFragment sortingDialogFragment = new SortingDialogFragment();
        sortingDialogFragment.show(getFragmentManager(), SortingDialogFragment.TAG);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
