package be.scoutsronse.wafelbak.service.impl;

import be.scoutsronse.wafelbak.domain.id.WayId;
import be.scoutsronse.wafelbak.osm.OSMService;
import be.scoutsronse.wafelbak.service.WayService;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static be.scoutsronse.wafelbak.domain.id.WayId.aWayId;
import static java.util.stream.Collectors.*;

@Service
class WayServiceImpl implements WayService {

    private final OSMService osmService;
    private final Map<WayId, CoordinateLine> coordinateLinesByWay;

    @Inject
    private WayServiceImpl(OSMService osmService) {
        this.osmService = osmService;
        coordinateLinesByWay = osmService.getStreetsOfRonse()
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