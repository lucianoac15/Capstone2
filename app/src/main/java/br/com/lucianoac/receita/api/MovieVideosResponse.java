package br.com.lucianoac.receita.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.lucianoac.receita.dto.VideoObject;

public class MovieVideosResponse {
    @SerializedName("id")
    private long movieId;

    @SerializedName("results")
    private ArrayList<VideoObject> results;

    public MovieVideosResponse(long movieId, ArrayList<VideoObject> results) {
        this.movieId = movieId;
        this.results = results;
    }

    public long getMovieId() {
        return movieId;
    }

    public ArrayList<VideoObject> getResults() {
        return results;
    }
}
