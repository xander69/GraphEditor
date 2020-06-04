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
@XmlType(name = "RecordType", propOrder = {"fieldList"})
public class Record {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String type = "delimited";
    @XmlAttribute(required = true)
    private String fieldDelimiter = ";";
    @XmlAttribute(required = true)
    private String recordDelimiter = "\\n";
    @XmlAttribute(required = true)
    private String previewAttachmentCharset = "UTF-8";
    @XmlElement(name = "Field", required = true)
    private List<Field> fieldList;

}
