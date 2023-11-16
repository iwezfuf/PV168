package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import java.util.function.Consumer;

public class RecipeTablePanel extends EntityTablePanel<Recipe> {
    public RecipeTablePanel(EntityTableModel<Recipe> entityTableModel, Validator<Recipe> recipeValidator, Class<? extends EntityDialog<Recipe>> recipeDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, Recipe.class, recipeValidator, recipeDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Recipe> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }
}