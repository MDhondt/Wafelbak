package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "member")
public class Member {

    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "ref")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger ref;
    @XmlAttribute(name = "role")
    private String role;

    public String type() {
        return type;
    }

    public Integer reference() {
        return ref.intValueExact();
    }

    public String role() {
        return role;
    }
}