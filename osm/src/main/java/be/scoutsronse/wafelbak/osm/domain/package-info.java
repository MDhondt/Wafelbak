@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
})
package be.scoutsronse.wafelbak.osm.domain;

import be.scoutsronse.wafelbak.tech.adapter.xml.LocalDateTimeXmlAdapter;
import be.scoutsronse.wafelbak.tech.adapter.xml.LocalDateXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.time.LocalDateTime;