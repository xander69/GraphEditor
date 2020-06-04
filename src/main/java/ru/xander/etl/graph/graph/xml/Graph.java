package ru.xander.etl.graph.graph.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@XmlRootElement(name = "Graph")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GraphType", propOrder = {"global"})
public class Graph {

    @XmlAttribute(required = true)
    private long id;
    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String author;
    @XmlAttribute(required = true)
    private String created;
    @XmlAttribute(required = true)
    private String guiVersion = "5.1.1";
    @XmlAttribute(required = true)
    private String licenseCode;
    @XmlElement(name = "Global", required = true)
    private Global global;

}
