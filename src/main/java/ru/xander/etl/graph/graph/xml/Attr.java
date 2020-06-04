package ru.xander.etl.graph.graph.xml;

import lombok.Getter;
import lombok.Setter;
import ru.xander.etl.graph.util.AdapterCData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttrType")
public class Attr {

    @XmlAttribute(required = true)
    private String name;
    @XmlValue
    @XmlJavaTypeAdapter(AdapterCData.class)
    private String value;

}
