package be.scoutsronse.wafelbak.repository;

import be.scoutsronse.wafelbak.repository.api.BaseRepository;
import org.hibernate.event.spi.EventSource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

import static net.jodah.typetools.TypeResolver.resolveRawArgument;

@Transactional
public abstract class BaseRepositoryImpl<Aggregate> implements BaseRepository<Aggregate> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Aggregate> findAll() {
        Class<Aggregate> entityClass = (Class<Aggregate>) resolveRawArgument(BaseRepositoryImpl.class, getClass());

        CriteriaQuery<Aggregate> cq = entityManager.getCriteriaBuilder().createQuery(entityClass);
        Root<Aggregate> rootEntry = cq.from(entityClass);
        CriteriaQuery<Aggregate> all = cq.select(rootEntry);
        TypedQuery<Aggregate> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void save(Aggregate aggregate) {
        flushWhenDeletionsQueued();
        entityManager.persist(aggregate);
    }

    @Override
    public void save(Collection<Aggregate> aggregates) {
        aggregates.forEach(this::save);
    }

    @Override
    public Aggregate update(Aggregate aggregate) {
        Aggregate merged = entityManager.merge(aggregate);
        flushWhenDeletionsQueued();
        return merged;
    }

    @Override
    public void update(Collection<Aggregate> aggregates) {
        aggregates.forEach(this::update);
    }

    @Override
    public void delete(Aggregate aggregate) {
        entityManager.remove(managed(aggregate));
    }

    @Override
    public void delete(Collection<Aggregate> aggregates) {
        aggregates.forEach(this::delete);
    }

    private Aggregate managed(Aggregate aggregate) {
        return isManaged(aggregate) ? aggregate : entityManager.merge(aggregate);
    }

    private boolean isManaged(Aggregate aggregate) {
        return entityManager.contains(aggregate);
    }

    private void flushWhenDeletionsQueued() {
        EventSource eventSource = entityManager.unwrap(EventSource.class);
        if (eventSource.getActionQueue().numberOfDeletions() != 0) {
            entityManager.flush();
        }
    }
}