package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link javax.swing.table.TableModel} for {@link T} objects.
 */
public class EntityTableModel<T extends Entity> extends AbstractTableModel {
    private List<T> entities;
    private final CrudService<T> entityCrudService;
    private final List<Column<T, ?>> columns;

    public EntityTableModel(CrudService<T> entityCrudService, List<Column<T, ?>> columns) {
        this.entityCrudService = entityCrudService;
        this.entities = new ArrayList<>(entityCrudService.findAll());
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(entity);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null) {
            var entity = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, entity);
            updateRow(entity);
        }
    }

    public void deleteRow(int rowIndex) {

        var entityToBeDeleted = getEntity(rowIndex);
        boolean deletionResult = entityCrudService.deleteByGuid(entityToBeDeleted.getGuid());

        if (deletionResult) {
            entities.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        } else {
            JOptionPane.showMessageDialog(null, "Cannot delete the entity because it is currently used in one or more places.", "Deletion error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRow(T entity) {
        entityCrudService.create(entity).intoException();
        int newRowIndex = entities.size();
        entities.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(T entity) {
        entityCrudService.update(entity)
                .intoException();
        int rowIndex = entities.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public boolean nameExist(String name) {
        return entities.stream().anyMatch(e -> e.getName().equals(name));
    }

    public void refresh() {
        this.entities = new ArrayList<>(entityCrudService.findAll());
        fireTableDataChanged();
    }

    public T getEntity(int rowIndex) {
        return entities.get(rowIndex);
    }

    public Collection<T> getEntities() {
        return entities;
    }
}
