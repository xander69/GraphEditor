package ru.xander.etl.graph.graph.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldType", propOrder = {"attrList"})
public class Field {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String type = "string";
    @XmlAttribute
    private Integer size;
    @XmlAttribute
    private Integer scale;
    @XmlAttribute
    private String format;
    @XmlAttribute(name = "default")
    private String defaultValue;

    @XmlElement(name = "attr")
    private List<Attr> attrList;

}
