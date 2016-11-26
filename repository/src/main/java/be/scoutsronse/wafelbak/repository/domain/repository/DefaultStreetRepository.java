package be.scoutsronse.wafelbak.repository.domain.repository;

import be.scoutsronse.wafelbak.entity.Street;
import be.scoutsronse.wafelbak.repository.IndexedBaseRepository;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import be.scoutsronse.wafelbak.repository.domain.index.StreetIndex;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultStreetRepository extends IndexedBaseRepository<Street, StreetIndex> implements StreetRepository {

    @Override
    public void save(Street street) {
        super.save(street);
    }

    @Override
    protected StreetIndex index() {
        return null;
    }
}