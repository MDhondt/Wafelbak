package be.scoutsronse.wafelbak.osm;

import be.scoutsronse.wafelbak.osm.domain.Way;
import com.sothawo.mapjfx.CoordinateLine;

import java.util.Map;

public interface OSMService {

    Map<Way, CoordinateLine> getStreetsOfRonse();

    CoordinateLine borderOfRonse();
}