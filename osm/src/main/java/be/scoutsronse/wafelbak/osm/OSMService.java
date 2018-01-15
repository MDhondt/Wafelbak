package be.scoutsronse.wafelbak.osm;

import be.scoutsronse.wafelbak.osm.domain.OSM;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
class OSMService implements OpenStreetMapService {

    private final File ronseInput = new File(getClass().getResource("/ronse.osm").getFile());
    private OSM ronse;

    @PostConstruct
    private void parseRonse() {
        ronse = OSMParser.parse(ronseInput);
    }

    @Override
    public OSM getRonse() {
        return ronse;
    }
}