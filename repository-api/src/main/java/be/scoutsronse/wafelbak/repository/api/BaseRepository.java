package be.scoutsronse.wafelbak.repository.api;

import java.util.Collection;

public interface BaseRepository<Entity> {

    void save(Entity aggregate);

    void save(Collection<Entity> aggregates);

    Entity update(Entity aggregate);

    void update(Collection<Entity> aggregates);

    void delete(Entity aggregate);

    void delete(Collection<Entity> aggregates);

    Collection<Entity> findAll();
}