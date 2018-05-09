package br.com.lucianoac.receita.services;

import java.util.List;

import br.com.lucianoac.receita.services.response.RecipeDetail;
import br.com.lucianoac.receita.services.response.RecipeItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IReceitaService {
    String appId="f5be09a9";
    String appKey="e37ec89b7edd12395493892a0940cf18";

    @GET("/api/recipes?_app_id="+appId+"&_app_key="+appKey+"&q={filter}")
    Call<List<RecipeItem>> listarReceitas(@Path("q") String filter);

    @GET("/api/recipe/{recipeId}?_app_id="+appId+"&_app_key="+appKey+"&q={filter}")
    Call<RecipeDetail> detalharReceita(@Path("recipeId") String recipeId);
}
