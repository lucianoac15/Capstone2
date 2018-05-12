package br.com.lucianoac.receita.api;

import br.com.lucianoac.receita.dto.MovieObject;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TheMovieDbService {

    @GET("discover/movie")
    Observable<SearchResponse<MovieObject>> discoverMovies(@Query("sort_by") String sortBy,
                                                           @Query("page") Integer page);

    @GET("search/movie")
    Observable<SearchResponse<MovieObject>> searchMovies(@Query("query") String query,
                                                         @Query("page") Integer page);
    @GET("movie/{id}/videos")
    Observable<MovieVideosResponse> getMovieVideos(@Path("id") long movieId);

    @GET("movie/{id}/reviews")
    Observable<MovieReviewsResponse> getMovieReviews(@Path("id") long movieId);


}
