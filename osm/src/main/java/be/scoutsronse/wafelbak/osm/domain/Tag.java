package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "tag")
public class Tag {

    @XmlAttribute(name = "k")
    private String k;
    @XmlAttribute(name = "v")
    private String v;

    public String key() {
        return k;
    }

    public String value() {
        return v;
    }
}