package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ExportService}
 */
public class GenericExportService implements ExportService {

    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Unit> unitCrudService;
    private final CrudService<RecipeCategory> recipeCategoryCrudService;
    private final CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService;
    private final FormatMapping<BatchExporter> exporters;

    public GenericExportService(
            CrudService<Ingredient> IngredientCrudService,
            CrudService<Recipe> RecipeCrudService,
            CrudService<Unit> UnitCrudService,
            CrudService<RecipeCategory> recipeCategoryCrudService,
            CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService,
            Collection<BatchExporter> exporters
    ) {
        this.ingredientCrudService = IngredientCrudService;
        this.recipeCrudService = RecipeCrudService;
        this.unitCrudService = UnitCrudService;
        this.recipeCategoryCrudService = recipeCategoryCrudService;
        this.recipeIngredientAmountCrudService = recipeIngredientAmountCrudService;
        this.exporters = new FormatMapping<>(exporters);
    }

    @Override
    public Collection<Format> getFormats() {
        return exporters.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var exporter = getExporter(filePath);

        var batch = new Batch(recipeCrudService.findAll(), ingredientCrudService.findAll(), unitCrudService.findAll(), recipeCategoryCrudService.findAll(), recipeIngredientAmountCrudService.findAll());
        exporter.exportBatch(batch, filePath);
    }

    private BatchExporter getExporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = exporters.findByExtension(extension);
        if (importer == null)
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        return importer;
    }
}
