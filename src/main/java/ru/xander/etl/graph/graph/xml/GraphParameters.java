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
@XmlType(name = "GraphParametersType", propOrder = {"graphParameterList"})
public class GraphParameters {

    @XmlElement(name = "GraphParameter", required = true)
    private List<GraphParameter> graphParameterList;

}
