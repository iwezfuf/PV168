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
    private int preparationTime;
    private int numOfServings;
    private String instructions;
    private RecipeCategory category;
    private List<RecipeIngredientAmount> ingredients;

    public Recipe(String guid, String name, String description, int preparationTime, int numOfServings,
                  String instructions, RecipeCategory category, ArrayList<RecipeIngredientAmount> ingredients) {
        super(guid);
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String description, int preparationTime, int numOfServings,
                  String instructions, RecipeCategory category, ArrayList<RecipeIngredientAmount> ingredients) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Recipe() {
        this.name = "";
        this.description = "";
        this.preparationTime = 0;
        this.numOfServings = 1;
        this.instructions = "";
        this.category = new RecipeCategory();
        this.ingredients = new ArrayList<>();
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

    @Override
    public String getName() {
        return name;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public List<RecipeIngredientAmount> getIngredients() {
        return ingredients;
    }

    public Set<Ingredient> getIngredientsSet() {
        HashSet<Ingredient> result = new HashSet<>();
        for (var ingredientAmount : ingredients) {
            result.add(ingredientAmount.getIngredient());
        }
        return result;
    }

    public int getNutritionalValue() {
        int result = 0;
        for (RecipeIngredientAmount ingredientAmount : ingredients) {
            result += ingredientAmount.getAmount() * ingredientAmount.getIngredient().getNutritionalValue();
        }
        return result;
    }

    public void setIngredients(List<RecipeIngredientAmount> ingredients) {
        this.ingredients = ingredients;
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

    @Override
    public String toString() {
        String builder = "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime=" + preparationTime +
                ", numOfServings=" + numOfServings +
                ", instructions='" + instructions + '\'' +
                ", category=" + category +
                ", ingredients=" + ingredients.size() +
                ", guid='" + guid + '\'' +
                '}';
        return builder;
    }

}

