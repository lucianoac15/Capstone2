package br.com.lucianoac.receita.api;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.lucianoac.receita.dto.ReviewObject;

public class MovieReviewsResponse {
    @SerializedName("id")
    private long movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<ReviewObject> results;

    @SerializedName("total_pages")
    private int totalPages;

    public MovieReviewsResponse(long movieId, int page, ArrayList<ReviewObject> results, int totalPages) {
        this.movieId = movieId;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
    }

    public long getMovieId() {
        return movieId;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<ReviewObject> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
