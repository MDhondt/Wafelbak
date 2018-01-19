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

    public Long id() {
        return id.longValueExact();
    }

    public String user() {
        return user;
    }

    public Long uid() {
        return uid.longValueExact();
    }

    public Boolean visible() {
        return visible;
    }

    public Long version() {
        return version.longValueExact();
    }

    public Long changeSet() {
        return changeset.longValueExact();
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }
}