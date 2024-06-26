package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.dao.InvalidDataDeletionException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Repository} for {@link Recipe} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class RecipeSqlRepository implements Repository<Recipe> {

    private final DataAccessObject<RecipeEntity> recipeDao;
    private final EntityMapper<RecipeEntity, Recipe> recipeMapper;

    public RecipeSqlRepository(
            DataAccessObject<RecipeEntity> recipeDao,
            EntityMapper<RecipeEntity, Recipe> recipeMapper) {
        this.recipeDao = recipeDao;
        this.recipeMapper = recipeMapper;
    }


    @Override
    public List<Recipe> findAll() {
        return recipeDao
                .findAll()
                .stream()
                .map(recipeMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Recipe newEntity) {
        recipeDao.create(recipeMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Recipe entity) {
        var existingRecipe = recipeDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Recipe not found, guid: " + entity.getGuid()));
        var updatedRecipeEntity = recipeMapper
                .mapExistingEntityToDatabase(entity, existingRecipe.id());

        recipeDao.update(updatedRecipeEntity);
    }

    @Override
    public void deleteByGuid(String guid) throws InvalidDataDeletionException {

        try {
            recipeDao.deleteByGuid(guid);
        } catch (DataStorageException exception) {
            throw new InvalidDataDeletionException("Unable to delete recipe by guid.", exception);
        }
    }

    @Override
    public void deleteAll() {
        recipeDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return recipeDao.existsByGuid(guid);
    }

    @Override
    public Optional<Recipe> findByGuid(String guid) {
        return recipeDao
                .findByGuid(guid)
                .map(recipeMapper::mapToBusiness);
    }
}
