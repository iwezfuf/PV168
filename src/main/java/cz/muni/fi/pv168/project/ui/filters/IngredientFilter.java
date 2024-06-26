package cz.muni.fi.pv168.project.ui.filters;


import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;

public class IngredientFilter implements AbstractFilter {

    private final Range nutritionValueRange;

    public IngredientFilter(Range nutritionValueRange) {
        this.nutritionValueRange = nutritionValueRange;
    }

    private boolean evaluateIngredient(Ingredient ingredient) {
        return nutritionValueRange.isValueInRange(ingredient.getNutritionalValue());
    }

    @Override
    public RowFilter<EntityTableModel<Ingredient>, Integer> getRowFilter() {
        return new RowFilter<EntityTableModel<Ingredient>, Integer>() {
            @Override
            public boolean include(Entry<? extends EntityTableModel<Ingredient>, ? extends Integer> entry) {
                Ingredient row = entry.getModel().getEntity(entry.getIdentifier());
                return evaluateIngredient(row);
            }
        };
    }
}
