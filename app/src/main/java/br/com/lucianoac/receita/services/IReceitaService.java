package br.com.lucianoac.receita.services;

import java.util.List;

import br.com.lucianoac.receita.services.response.RecipeDetail;
import br.com.lucianoac.receita.services.response.RecipeItem;
import br.com.lucianoac.receita.services.response.RecipeList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IReceitaService {
    String appId="f5be09a9";
    String appKey="e37ec89b7edd12395493892a0940cf18";

    @GET("api/recipes?_app_id=f5be09a9&_app_key=e37ec89b7edd12395493892a0940cf18")
    Call<RecipeList> listarReceitas(@Query("q") String filter);

    @GET("api/recipe/{recipeId}?_app_id="+appId+"&_app_key="+appKey+"&q={filter}")
    Call<RecipeDetail> detalharReceita(@Path("recipeId") String recipeId);
}
