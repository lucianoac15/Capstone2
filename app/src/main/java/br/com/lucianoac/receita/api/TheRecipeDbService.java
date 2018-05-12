package br.com.lucianoac.receita.api;

import br.com.lucianoac.receita.dto.RecipeObject;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TheRecipeDbService {

    @GET("discover/movie")
    Observable<SearchResponse<RecipeObject>> discoverMovies(@Query("sort_by") String sortBy,
                                                            @Query("page") Integer page);

    @GET("search/movie")
    Observable<SearchResponse<RecipeObject>> searchMovies(@Query("query") String query,
                                                          @Query("page") Integer page);
    @GET("movie/{id}/videos")
    Observable<RecipeVideosResponse> getMovieVideos(@Path("id") long movieId);

    @GET("movie/{id}/reviews")
    Observable<RecipeReviewsResponse> getMovieReviews(@Path("id") long movieId);


}
