package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.tech.stacktrace.StackTraceUtils;

import java.util.Collection;

public abstract class IndexedBaseRepository<Entity, Index extends be.scoutsronse.wafelbak.repository.Index<Entity>> extends BaseRepositoryImpl<Entity> {

    protected abstract Index index();

    @Override
    public void save(Entity entity) {
        super.save(entity);
        index().save(entity);
    }

    @Override
    public Entity update(Entity entity) {
        entity = super.update(entity);
        index().update(entity);
        return entity;
    }

    @Override
    public void delete(Entity entity) {
        super.delete(entity);
        index().delete(entity);
    }

    @Override
    public Collection<Entity> findAll() {
        return StackTraceUtils.calledBy(index().getClass()) ? super.findAll() : index().findAll();
    }
}