package be.scoutsronse.wafelbak.repository.domain.repository;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.repository.IndexedBaseRepository;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import be.scoutsronse.wafelbak.repository.domain.index.StreetIndex;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class DefaultStreetRepository extends IndexedBaseRepository<Street, StreetIndex> implements StreetRepository {

    @Inject
    private StreetIndex index;

    @Override
    public void save(Street street) {
        super.save(street);
    }

    @Override
    protected StreetIndex index() {
        return index;
    }
}