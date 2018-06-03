package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"bounds", "node", "way", "relation"})
@XmlRootElement(name = "osm")
public class OSM {

    @XmlElement(required = true)
    private Bounds bounds;
    private List<Node> node;
    private List<Way> way;
    private List<Relation> relation;
    @XmlAttribute(name = "version")
    private Float version;
    @XmlAttribute(name = "generator")
    private String generator;

    public Bounds bounds() {
        return bounds;
    }

    public List<Node> nodes() {
        return new ArrayList<>(node);
    }

    public List<Way> ways() {
        return new ArrayList<>(way);
    }

    public List<Relation> relations() {
        return new ArrayList<>(relation);
    }

    public Float version() {
        return version;
    }

    public String generator() {
        return generator;
    }
}