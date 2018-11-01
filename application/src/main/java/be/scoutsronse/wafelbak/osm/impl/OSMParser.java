package be.scoutsronse.wafelbak.osm.impl;

import be.scoutsronse.wafelbak.osm.domain.OSM;

import javax.xml.bind.JAXBException;
import java.io.InputStream;

import static javax.xml.bind.JAXBContext.newInstance;

class OSMParser {

    static synchronized OSM parse(InputStream osmFile) {
        try {
            return (OSM) newInstance(OSM.class).createUnmarshaller().unmarshal(osmFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}