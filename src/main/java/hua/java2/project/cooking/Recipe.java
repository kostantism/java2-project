package hua.java2.project.cooking;

public class Recipe {

    private String name;
    private Ingredient[] ingredients;
    private Cookware[] cookwares;
    private Step[] steps;

    public Recipe(String name, Ingredient[] ingredients, Cookware[] cookwares, Step[] steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.cookwares = cookwares;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Cookware[] getCookwares() {
        return cookwares;
    }

    public void setCookwares(Cookware[] cookwares) {
        this.cookwares = cookwares;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }
}
