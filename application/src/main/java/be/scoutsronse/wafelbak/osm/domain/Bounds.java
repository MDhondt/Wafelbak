package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "bounds")
public class Bounds {

    @XmlAttribute(name = "minlat")
    private Double minlat;
    @XmlAttribute(name = "minlon")
    private Double minlon;
    @XmlAttribute(name = "maxlat")
    private Double maxlat;
    @XmlAttribute(name = "maxlon")
    private Double maxlon;

    public Double minimumLatitude() {
        return minlat;
    }

    public Double minimumLongitude() {
        return minlon;
    }

    public Double maximumLatitude() {
        return maxlat;
    }

    public Double maximumLongitude() {
        return maxlon;
    }
}