package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.panels.filter.IngredientFilterPanel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import java.util.function.Consumer;

public class IngredientTablePanel extends EntityTablePanelSidePanel<Ingredient> {

    private final IngredientFilterPanel ingredientFilterPanel;

    public IngredientTablePanel(EntityTableModel<Ingredient> entityTableModel, Validator<Ingredient> ingredientValidator, Class<? extends EntityDialog<Ingredient>> ingredientDialog, Consumer<Integer> onSelectionChange, EntityTableModelProviderWithCrud etmp) {
        super(entityTableModel, Ingredient.class, ingredientValidator, ingredientDialog, onSelectionChange, etmp);

        this.ingredientFilterPanel = new IngredientFilterPanel(this);
        this.getSideScrollPane().setViewportView(ingredientFilterPanel);
    }

}
