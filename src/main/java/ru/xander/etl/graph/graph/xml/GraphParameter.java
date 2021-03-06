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
@XmlType(name = "GraphParameterType", propOrder = {"attrList"})
public class GraphParameter {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String format;
    @XmlAttribute
    private String value;
    @XmlAttribute
    private boolean required;
    @XmlAttribute
    private boolean secure;

    @XmlElement(name = "attr")
    private List<Attr> attrList;

}
