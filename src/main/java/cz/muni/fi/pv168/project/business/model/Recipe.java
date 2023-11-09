package cz.muni.fi.pv168.project.business.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marek Eibel
 */
public class Recipe extends Entity {

    private String name;
    private String description;
    private final int id;
    private int preparationTime;
    private int numOfServings;
    private String instructions;
    private RecipeCategory category;
    private static int idCounter = 0;

    /**
     * Represents pairs ingredientId : ingredient amount
     */
    private ArrayList<RecipeIngredientAmount> ingredients;
    private static List<Recipe> listOfRecipes = new ArrayList<>();

    public static List<Recipe> getListOfRecipes() {
        return listOfRecipes;
    }

    public Recipe(String name, String description, int preparationTime, int numOfServings,
                  String instructions, RecipeCategory category, ArrayList<RecipeIngredientAmount> ingredients) {

        this.name = name;
        this.description = description;
        this.id = Recipe.idCounter++;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.category = category;
        this.ingredients = ingredients;
        if (listOfRecipes == null) {
            listOfRecipes = new ArrayList<>();
        }
        listOfRecipes.add(this);
    }

    public Recipe() {
        this.name = "";
        this.description = "";
        this.id = Recipe.idCounter++;
        this.preparationTime = 0;
        this.numOfServings = 0;
        this.instructions = "";
        this.category = new RecipeCategory();
        this.ingredients = new ArrayList<>();
        if (listOfRecipes == null) {
            listOfRecipes = new ArrayList<>();
        }
        listOfRecipes.add(this);
    }

    public int getRecipeId() {
        return id;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getName() {
        return name;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public ArrayList<RecipeIngredientAmount> getIngredients() {
        return ingredients;
    }

    public String getIngredientsString() {
        StringBuilder result = new StringBuilder();
        for (var ingredientAmount : ingredients) {
            result.append(ingredientAmount.getIngredient().getName() + ": " + ingredientAmount.getAmount() + "\n");
        }
        return result.toString();
    }

    public RecipeIngredientAmount getIngredientAmount(Ingredient ingredient) {
        for (var ingredientAmount : ingredients) {
            if (ingredientAmount.getIngredient().equals(ingredient)) {
                return ingredientAmount;
            }
        }
        return null;
    }

    public Set<Ingredient> getIngredientsSet() {
        HashSet<Ingredient> result = new HashSet<>();
        for (var ingredientAmount : ingredients) {
            result.add(ingredientAmount.getIngredient());
        }
        return result;
    }

    public void addIngredient(Ingredient ingredient, int amount) {
        RecipeIngredientAmount ingredientAmount = getIngredientAmount(ingredient);
        if (ingredientAmount != null) {
            ingredientAmount.setAmount(ingredientAmount.getAmount() + amount);
        } else {
            ingredients.add(new RecipeIngredientAmount(ingredient, amount));
        }
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public int getNutritionalValue() {
        int result = 0;
        for (RecipeIngredientAmount ingredientAmount : ingredients) {
            result += ingredientAmount.getAmount() * ingredientAmount.getIngredient().getNutritionalValue();
        }
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public static int getNumOfRecipes() {
        return listOfRecipes.size();
    }

    @Override
    public String getGuid() {
        return "recipe-" + id;
    }

    public static boolean isInRecipe(Ingredient ingr) {
        for (Recipe rec : getListOfRecipes()) {
            if (rec.getIngredientsSet().contains(ingr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInRecipe(RecipeCategory recipeCategory) {
        for (Recipe rec : getListOfRecipes()) {
            if (rec.getCategory() == recipeCategory) {
                return true;
            }
        }
        return false;
    }
}