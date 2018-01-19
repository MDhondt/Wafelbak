package be.scoutsronse.wafelbak.mvp.way;

import be.scoutsronse.wafelbak.domain.id.WayId;
import be.scoutsronse.wafelbak.mvp.util.OpenStreetMapUtils;
import be.scoutsronse.wafelbak.osm.OpenStreetMapService;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static be.scoutsronse.wafelbak.domain.id.WayId.aWayId;
import static java.util.stream.Collectors.*;

@Component
class WayServiceImpl implements WayService {

    @Inject
    private OpenStreetMapService osmService;
    @Inject
    private OpenStreetMapUtils osmUtils;

    private Map<WayId, CoordinateLine> coordinateLinesByWay;

    @PostConstruct
    private void init() {
        coordinateLinesByWay = osmUtils.getStreetsOfRonse(osmService.getRonse())
                                       .entrySet().stream()
                                       .collect(toMap(entry -> aWayId(entry.getKey().id()), Map.Entry::getValue));
    }

    @Override
    public synchronized Collection<CoordinateLine> findCoordinateLinesBy(Collection<WayId> wayIds) {
        return wayIds.stream()
                       .map(coordinateLinesByWay::get)
                       .filter(Objects::nonNull)
                       .map(CoordinateLine::getCoordinateStream)
                       .map(str -> str.collect(toList()))
                       .map(CoordinateLine::new)
                       .collect(toSet());
    }
}