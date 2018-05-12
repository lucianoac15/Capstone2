package br.com.lucianoac.receita;

import br.com.lucianoac.receita.api.NetworkModule;
import br.com.lucianoac.receita.screen.MainActivity;
import br.com.lucianoac.receita.util.SortingDialogFragment;
import br.com.lucianoac.receita.screen.RecipeDetailActivity;
import br.com.lucianoac.receita.screen.RecipeDetailFragment;
import br.com.lucianoac.receita.screen.RecipesGridFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(RecipesGridFragment recipesGridFragment);

    void inject(MainActivity mainActivity);

    void inject(SortingDialogFragment sortingDialogFragment);

    void inject(RecipeDetailActivity recipeDetailActivity);

    void inject(RecipeDetailFragment recipeDetailFragment);

}
