package br.com.lucianoac.receita.services.response;

public class RecipeItem {

    private String sourceDisplayName;
    private String id;
    private String rating;
    private String[] smallImageUrls;

    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    public void setSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getSmallImageUrls() {
        return smallImageUrls;
    }

    public void setSmallImageUrls(String[] smallImageUrls) {
        this.smallImageUrls = smallImageUrls;
    }

}
