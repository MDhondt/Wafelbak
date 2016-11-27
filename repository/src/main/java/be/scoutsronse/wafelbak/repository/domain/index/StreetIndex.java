package be.scoutsronse.wafelbak.repository.domain.index;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.repository.Index;

import java.util.Collection;

public class StreetIndex extends Index<Street> {

    @Override
    protected void doClear() {

    }

    @Override
    protected void doInitialize() {

    }

    @Override
    protected void doSave(Street street) {

    }

    @Override
    protected void doDelete(Street street) {

    }

    @Override
    protected Street findExisting(Street street) {
        return null;
    }

    @Override
    public Collection<Street> findAll() {
        return null;
    }
}