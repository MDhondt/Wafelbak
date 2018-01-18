package be.scoutsronse.wafelbak.repository.api;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;

public interface ClusterRepository extends BaseRepository<Cluster> {

    Cluster findBy(ClusterId id);
}