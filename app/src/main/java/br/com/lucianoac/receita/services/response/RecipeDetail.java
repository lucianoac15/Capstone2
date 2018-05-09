package br.com.lucianoac.receita.services.response;

public class RecipeDetail {

    private String yield;
    private String totalTime;
    private String name;
    private String id;
    private String numberOfServings;
    private String rating;
    private String[] ingredientLines;
    private Flavors flavors;
    private NutritionEstimates[] nutritionestimates;
    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public NutritionEstimates[] getNutritionestimates() {
        return nutritionestimates;
    }

    public void setNutritionestimates(NutritionEstimates[] nutritionestimates) {
        this.nutritionestimates = nutritionestimates;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String[] ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public Flavors getFlavors() {
        return flavors;
    }

    public void setFlavors(Flavors flavors) {
        this.flavors = flavors;
    }
}
