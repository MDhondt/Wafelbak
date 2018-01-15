package be.scoutsronse.wafelbak.osm;

import be.scoutsronse.wafelbak.osm.domain.OSM;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.File;

import static javax.xml.bind.JAXBContext.newInstance;

@Component
public class OSMParser {

    public static synchronized OSM parse(File osmFile) {
        try {
            return (OSM) newInstance(OSM.class).createUnmarshaller().unmarshal(osmFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}