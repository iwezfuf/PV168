package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeDeserializer extends JsonDeserializer<Recipe> {
    public RecipeDeserializer() {
    }

    @Override
    public Recipe deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String guid = node.get("guid").asText();
        String name = node.get("name").asText();
        String description = node.get("description").asText();

        int preparationTime = node.get("preparationTime").asInt();
        int numOfServings = node.get("numOfServings").asInt();

        String instructions = node.get("instructions").asText();

        RecipeCategoryDeserializer recipeCategoryDeserializer = new RecipeCategoryDeserializer();
        RecipeCategory recipeCategory = recipeCategoryDeserializer.deserialize(node.get("category").traverse(jsonParser.getCodec()), deserializationContext);
        RecipeIngredientAmountDeserializer recipeIngredientAmountDeserializer = new RecipeIngredientAmountDeserializer();

        ArrayList<RecipeIngredientAmount> ingredients = new ArrayList<>();
        for (JsonNode ingredient : node.get("ingredients")) {
            ingredients.add(recipeIngredientAmountDeserializer.deserialize(ingredient.traverse(jsonParser.getCodec()), deserializationContext));
        }
        return new Recipe(guid, name, description, preparationTime, numOfServings, instructions, recipeCategory, ingredients);
    }
}
