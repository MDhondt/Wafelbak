package be.scoutsronse.wafelbak.repository.api;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;

import java.util.Collection;

public interface StreetRepository extends BaseRepository<Street> {

    Street findBy(StreetId id);

    Street findBy(String name, ClusterId clusterId);

    Collection<Street> findBy(ClusterId clusterId);
}