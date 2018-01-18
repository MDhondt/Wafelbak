package be.scoutsronse.wafelbak.repository.cluster;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.repository.IndexedBaseRepository;
import be.scoutsronse.wafelbak.repository.api.ClusterRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
class DefaultClusterRepository extends IndexedBaseRepository<Cluster, ClusterIndex> implements ClusterRepository {

    @Inject
    private ClusterIndex index;

    @Override
    public Cluster findBy(ClusterId id) {
        return index.findBy(id);
    }

    @Override
    protected ClusterIndex index() {
        return index;
    }
}