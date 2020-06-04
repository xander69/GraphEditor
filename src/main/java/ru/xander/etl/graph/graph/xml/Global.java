package ru.xander.etl.graph.graph.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalType", propOrder = {"metadataGroupList", "metadataList", "graphParameters", "dictionary"})
public class Global {

    @XmlElement(name = "MetadataGroup")
    private List<MetadataGroup> metadataGroupList;
    @XmlElement(name = "Metadata")
    private List<Metadata> metadataList;
    @XmlElement(name = "GraphParameters", required = true)
    private GraphParameters graphParameters;
    @XmlElement(name = "Dictionary", required = true)
    private Dictionary dictionary;

}
