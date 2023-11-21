package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;

/**
 * @author Marek Eibel
 */
public class ReplaceImportStrategy implements ImportStrategy {
    @Override
    public void executeImport(CrudService<Recipe> recipeCrudService, CrudService<Ingredient> ingredientCrudService,
                              CrudService<Unit> unitCrudService, CrudService<RecipeCategory> recipeCategoryCrudService,
                              BatchImporter batchImporter, String filePath) {

        recipeCrudService.deleteAll();
        recipeCategoryCrudService.deleteAll();
        ingredientCrudService.deleteAll();
        unitCrudService.deleteAll();

        var batch = batchImporter.importBatch(filePath);

        batch.recipes().forEach(recipe -> ImportStrategy.createRecipe(recipe, recipeCrudService));
        batch.ingredients().forEach(ingredient -> ImportStrategy.createIngredient(ingredient, ingredientCrudService));
        batch.units().forEach(unit -> ImportStrategy.createUnit(unit, unitCrudService));
        batch.recipeCategories().forEach(recipeCategory -> ImportStrategy.createRecipeCategory(recipeCategory, recipeCategoryCrudService));

    }
}
