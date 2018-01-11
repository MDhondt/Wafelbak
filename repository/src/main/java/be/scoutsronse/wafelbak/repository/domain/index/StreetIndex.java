package be.scoutsronse.wafelbak.repository.domain.index;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.Index;
import be.scoutsronse.wafelbak.repository.api.StreetRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toMapByKey;
import static com.google.common.collect.Lists.newArrayList;

@Component
public class StreetIndex extends Index<Street> {

    @Inject
    private StreetRepository repository;

    private Map<StreetId, Street> byId;

    synchronized Street findBy(StreetId streetId) {
        return byId().get(streetId);
    }

    private Map<StreetId, Street> byId() {
        initialize();
        return byId;
    }

    @Override
    protected void doClear() {
        byId = null;
    }

    @Override
    protected void doInitialize() {
        Collection<Street> streets = repository.findAll();
        byId = streets.stream().collect(toMapByKey(Street::id));
    }

    @Override
    protected void doSave(Street street) {
        byId().put(street.id(), street);
    }

    @Override
    protected void doDelete(Street street) {
        byId().remove(street.id());
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