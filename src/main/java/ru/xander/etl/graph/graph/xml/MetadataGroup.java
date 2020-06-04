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
@XmlType(name = "MetadataGroupType", propOrder = {"metadataList"})
public class MetadataGroup {

    @XmlAttribute(required = true)
    private String id;
    @XmlAttribute(required = true)
    private String name;
    @XmlElement(name = "Metadata", required = true)
    private List<Metadata> metadataList;

}
