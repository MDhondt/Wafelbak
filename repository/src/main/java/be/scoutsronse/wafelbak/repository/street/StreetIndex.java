package be.scoutsronse.wafelbak.repository.street;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.Index;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toMapByKey;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.groupingBy;

@Component
class StreetIndex extends Index<Street> {

    @Inject
    private StreetRepository repository;

    private Map<StreetId, Street> byId;
    private Map<String, List<Street>> byName;
    private Map<ClusterId, List<Street>> byCluster;

    synchronized Street findBy(StreetId streetId) {
        return byId().get(streetId);
    }

    synchronized Street findBy(String name, ClusterId cluster) {
        return byName().get(name).stream().filter(street -> street.cluster().id().equals(cluster)).findFirst().orElse(null);
    }

    synchronized Collection<Street> findBy(ClusterId cluster) {
        return newArrayList(byCluster().get(cluster));
    }

    private Map<StreetId, Street> byId() {
        initialize();
        return byId;
    }

    private Map<String, List<Street>> byName() {
        initialize();
        return byName;
    }

    private Map<ClusterId, List<Street>> byCluster() {
        initialize();
        return byCluster;
    }

    @Override
    protected void doClear() {
        byId = null;
        byName = null;
        byCluster = null;
    }

    @Override
    protected void doInitialize() {
        Collection<Street> streets = repository.findAll();
        byId = streets.stream().collect(toMapByKey(Street::id));
        byName = streets.stream().collect(groupingBy(Street::name));
        byCluster = streets.stream().collect(groupingBy(street -> street.cluster().id()));
    }

    @Override
    protected void doSave(Street street) {
        byId().put(street.id(), street);
        List<Street> streetsForName = byName().get(street.name());
        if (streetsForName != null)
            streetsForName.add(street);
        else
            byName().put(street.name(), newArrayList(street));
        List<Street> streetsForCluster = byCluster().get(street.cluster().id());
        if (streetsForCluster != null)
            streetsForCluster.add(street);
        else
            byCluster().put(street.cluster().id(), newArrayList(street));
    }

    @Override
    protected void doDelete(Street street) {
        byId().remove(street.id());
        List<Street> streetByName = byName().get(street.name());
        if (streetByName != null && streetByName.size() > 1)
            streetByName.remove(street);
        else if (streetByName != null && (streetByName.contains(street) || streetByName.isEmpty()))
            byName().remove(street.name());
        List<Street> streetsForCluster = byCluster().get(street.cluster().id());
        if (streetsForCluster != null && streetsForCluster.size() > 1)
            streetsForCluster.remove(street);
        else if (streetsForCluster != null && (streetsForCluster.contains(street) || streetsForCluster.isEmpty()))
            byCluster().remove(street.cluster().id());
    }

    @Override
    protected synchronized Street findExisting(Street street) {
        return findBy(street.id());
    }

    @Override
    public synchronized Collection<Street> findAll() {
        return newArrayList(byId().values());
    }
}