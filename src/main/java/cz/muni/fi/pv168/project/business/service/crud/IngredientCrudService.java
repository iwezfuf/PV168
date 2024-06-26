package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.dao.InvalidDataDeletionException;

import java.util.List;

/**
 * Crud operations for the {@link Ingredient} entity.
 */
public final class IngredientCrudService implements CrudService<Ingredient> {

    private final Repository<Ingredient> ingredientRepository;
    private final Validator<Ingredient> ingredientValidator;
    private final GuidProvider guidProvider;

    public IngredientCrudService(Repository<Ingredient> IngredientRepository, Validator<Ingredient> ingredientValidator,
                                 GuidProvider guidProvider) {
        this.ingredientRepository = IngredientRepository;
        this.ingredientValidator = ingredientValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public ValidationResult create(Ingredient newEntity) {
        var validationResult = ingredientValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (ingredientRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Ingredient with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            ingredientRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Ingredient entity) {
        ingredientRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public boolean deleteByGuid(String guid) {
        try {
            ingredientRepository.deleteByGuid(guid);
        } catch (InvalidDataDeletionException e) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteAll() {
        ingredientRepository.deleteAll();
    }
}
