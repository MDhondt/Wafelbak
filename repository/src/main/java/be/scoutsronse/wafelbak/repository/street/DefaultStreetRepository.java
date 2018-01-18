package be.scoutsronse.wafelbak.repository.street;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.IndexedBaseRepository;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Collection;

@Repository
class DefaultStreetRepository extends IndexedBaseRepository<Street, StreetIndex> implements StreetRepository {

    @Inject
    private StreetIndex index;

    @Override
    public Street findBy(StreetId id) {
        return index.findBy(id);
    }

    @Override
    public Street findBy(String name, ClusterId cluster) {
        return index.findBy(name, cluster);
    }

    @Override
    public Collection<Street> findBy(ClusterId cluster) {
        return index.findBy(cluster);
    }

    @Override
    protected StreetIndex index() {
        return index;
    }
}