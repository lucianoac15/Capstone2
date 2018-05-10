package br.com.lucianoac.receita.services.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeList {

    @SerializedName("matches")
    private List<RecipeItem> listrecipe;

    public List<RecipeItem> getListrecipe() {
        return listrecipe;
    }

    public void setListrecipe(List<RecipeItem> listrecipe) {
        this.listrecipe = listrecipe;
    }
}
