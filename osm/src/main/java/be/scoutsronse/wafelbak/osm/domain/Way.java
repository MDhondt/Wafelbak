package be.scoutsronse.wafelbak.osm.domain;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"nd", "tag"})
@XmlRootElement(name = "way")
public class Way {

    @XmlElement(required = true)
    private List<Nd> nd;
    private List<Tag> tag;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger id;
    @XmlAttribute(name = "user")
    private String user;
    @XmlAttribute(name = "uid")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger uid;
    @XmlAttribute(name = "visible")
    private Boolean visible;
    @XmlAttribute(name = "version")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger version;
    @XmlAttribute(name = "changeset")
    @XmlSchemaType(name = "unsignedLong")
    private BigInteger changeset;
    @XmlAttribute(name = "timestamp")
    @XmlSchemaType(name = "dateTime")
    private LocalDateTime timestamp;

    public List<Nd> nds() {
        return new ArrayList<>(nd);
    }

    public Map<String, String> tags() {
        return ofNullable(tag).map(t -> t.stream().collect(toMap(Tag::key, Tag::value))).orElse(new HashMap<>());
    }

    public Integer id() {
        return id.intValueExact();
    }

    public String user() {
        return user;
    }

    public Integer uid() {
        return uid.intValueExact();
    }

    public Boolean visible() {
        return visible;
    }

    public Integer version() {
        return version.intValueExact();
    }

    public Integer changeSet() {
        return changeset.intValueExact();
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }
}