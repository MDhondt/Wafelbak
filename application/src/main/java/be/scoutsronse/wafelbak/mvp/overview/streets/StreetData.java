package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import com.sothawo.mapjfx.CoordinateLine;

import java.util.Collection;
import java.util.HashSet;

class StreetData extends ClusterItem {

    private StreetId id;
    private ClusterId clusterId;
    private Collection<CoordinateLine> coordinateLines = new HashSet<>();

    StreetData(Street street, Collection<CoordinateLine> coordinateLines) {
        super(street.name());
        id = street.id();
        clusterId = street.cluster().id();
        this.coordinateLines.addAll(coordinateLines);
    }

    StreetId id() {
        return id;
    }

    ClusterId clusterId() {
        return clusterId;
    }

    Collection<CoordinateLine> coordinateLines() {
        return new HashSet<>(coordinateLines);
    }
}