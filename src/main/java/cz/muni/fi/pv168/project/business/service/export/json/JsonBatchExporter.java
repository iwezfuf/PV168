package cz.muni.fi.pv168.project.business.service.export.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.serializers.IngredientSerializer;
import cz.muni.fi.pv168.project.business.service.export.serializers.RecipeCategorySerializer;
import cz.muni.fi.pv168.project.business.service.export.serializers.RecipeIngredientAmountSerializer;
import cz.muni.fi.pv168.project.business.service.export.serializers.RecipeSerializer;
import cz.muni.fi.pv168.project.business.service.export.serializers.UnitSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JsonBatchExporter implements BatchExporter {
    Format format = new Format("json", List.of("json", "JSON"));

    @Override
    public void exportBatch(Batch batch, String filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Recipe.class, new RecipeSerializer());
            module.addSerializer(Ingredient.class, new IngredientSerializer());
            module.addSerializer(Unit.class, new UnitSerializer());
            module.addSerializer(RecipeCategory.class, new RecipeCategorySerializer());
            module.addSerializer(RecipeIngredientAmount.class, new RecipeIngredientAmountSerializer());
            objectMapper.registerModule(module);
            objectMapper.writeValue(out, batch);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Format getFormat() {
        return format;
    }
}
