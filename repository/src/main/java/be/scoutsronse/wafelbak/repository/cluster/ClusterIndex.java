package be.scoutsronse.wafelbak.repository.cluster;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.repository.Index;
import be.scoutsronse.wafelbak.repository.api.ClusterRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toMapByKey;
import static com.google.common.collect.Lists.newArrayList;

@Component
class ClusterIndex extends Index<Cluster> {

    @Inject
    private ClusterRepository repository;

    private Map<ClusterId, Cluster> byId;

    synchronized Cluster findBy(ClusterId id) {
        return byId().get(id);
    }

    private Map<ClusterId, Cluster> byId() {
        initialize();
        return byId;
    }

    @Override
    protected void doClear() {
        byId = null;
    }

    @Override
    protected void doInitialize() {
        Collection<Cluster> clusters = repository.findAll();
        byId = clusters.stream().collect(toMapByKey(Cluster::id));
    }

    @Override
    protected void doSave(Cluster cluster) {
        byId().put(cluster.id(), cluster);
    }

    @Override
    protected void doDelete(Cluster cluster) {
        byId().remove(cluster.id());
    }

    @Override
    protected Cluster findExisting(Cluster cluster) {
        return findBy(cluster.id());
    }

    @Override
    public Collection<Cluster> findAll() {
        return newArrayList(byId().values());
    }
}