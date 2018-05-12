package br.com.lucianoac.receita;

import br.com.lucianoac.receita.api.NetworkModule;
import br.com.lucianoac.receita.screen.MainActivity;
import br.com.lucianoac.receita.util.SortingDialogFragment;
import br.com.lucianoac.receita.screen.MovieDetailActivity;
import br.com.lucianoac.receita.screen.MovieDetailFragment;
import br.com.lucianoac.receita.screen.MoviesGridFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(MoviesGridFragment moviesGridFragment);

    void inject(MainActivity mainActivity);

    void inject(SortingDialogFragment sortingDialogFragment);

    void inject(MovieDetailActivity movieDetailActivity);

    void inject(MovieDetailFragment movieDetailFragment);

}
