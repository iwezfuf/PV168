package cz.muni.fi.pv168.project.business.repository;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.storage.sql.dao.InvalidDataDeletionException;

import java.util.List;
import java.util.Optional;

/**
 * Represents a repository for any entity.
 *
 * @param <T> the type of the entity.
 */
public interface Repository<T extends Entity> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Persist given {@code newEntity}.
     */
    void create(T newEntity);

    /**
     * Update given {@code entity}.
     */
    void update(T entity);

    /**
     * Delete entity with given {@code guid}.
     *
     * @throws InvalidDataDeletionException if the deletion cannot be executed
     */
    void deleteByGuid(String guid) throws InvalidDataDeletionException;

    /**
     * Find entity with given {@code guid}.
     *
     * @return optional with found entity, or empty optional if no entity with given {@code guid} is found
     */
    Optional<T> findByGuid(String guid);

    /**
     * Delete all entities.
     */
    void deleteAll();

    /**
     * Check if there is an existing Entity with given {@code guid}
     *
     * @return true, if an Entity with given {@code} is found, false otherwise
     */
    boolean existsByGuid(String guid);
}
