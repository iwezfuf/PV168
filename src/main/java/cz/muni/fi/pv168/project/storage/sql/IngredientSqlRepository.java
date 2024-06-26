package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.dao.InvalidDataDeletionException;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Repository} for {@link Ingredient} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class IngredientSqlRepository implements Repository<Ingredient> {

    private final DataAccessObject<IngredientEntity> ingredientDao;
    private final EntityMapper<IngredientEntity, Ingredient> ingredientMapper;

    public IngredientSqlRepository(
            DataAccessObject<IngredientEntity> ingredientDao,
            EntityMapper<IngredientEntity, Ingredient> ingredientMapper) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
    }


    @Override
    public List<Ingredient> findAll() {
        return ingredientDao
                .findAll()
                .stream()
                .map(ingredientMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Ingredient newEntity) {
        ingredientDao.create(ingredientMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Ingredient entity) {
        var existingIngredient = ingredientDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Ingredient not found, guid: " + entity.getGuid()));
        var updatedIngredientEntity = ingredientMapper
                .mapExistingEntityToDatabase(entity, existingIngredient.id());

        ingredientDao.update(updatedIngredientEntity);
    }

    @Override
    public void deleteByGuid(String guid) throws InvalidDataDeletionException {

        try {
            ingredientDao.deleteByGuid(guid);
        } catch (DataStorageException exception) {
            throw new InvalidDataDeletionException("Unable to delete ingredient by guid.", exception);
        }
    }

    @Override
    public void deleteAll() {
        ingredientDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return ingredientDao.existsByGuid(guid);
    }

    @Override
    public Optional<Ingredient> findByGuid(String guid) {
        return ingredientDao
                .findByGuid(guid)
                .map(ingredientMapper::mapToBusiness);
    }
}
