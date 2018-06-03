package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "nd")
public class Nd {

    @XmlAttribute(name = "ref")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger ref;

    public Long reference() {
        return ref.longValueExact();
    }
}