package ru.xander.etl.graph.graph.converter;

import ru.xander.etl.graph.graph.xml.Attr;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.graph.xml.GraphParameter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.xander.etl.graph.graph.converter.Converter.ATTRIBUTE_VALUE;

/**
 * @author Alexander Shakhov
 */
public class GraphParameterMap {

    private final Map<String, GraphParameter> parameterMap;

    private GraphParameterMap(Map<String, GraphParameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getValue(String parameterName) {
        GraphParameter parameter = parameterMap.get(parameterName);
        if (parameter == null){
            return null;
        }
        Attr attr = getAttr(parameter, ATTRIBUTE_VALUE);
        if (attr == null) {
            return parameter.getValue();
        }
        return attr.getValue();
    }

    private Attr getAttr(GraphParameter graphParameter, String attrName) {
        List<Attr> attrList = graphParameter.getAttrList();
        if (attrList == null) {
            return null;
        }
        for (Attr attr : attrList) {
            if (attrName.equals(attr.getName())) {
                return attr;
            }
        }
        return null;
    }

    public static GraphParameterMap forGraph(Graph graph) {
        Map<String, GraphParameter> parameterMap;
        if (graph.getGlobal() == null) {
            parameterMap = Collections.emptyMap();
        } else if (graph.getGlobal().getGraphParameters() == null) {
            parameterMap = Collections.emptyMap();
        } else {
            List<GraphParameter> graphParameterList = graph.getGlobal().getGraphParameters().getGraphParameterList();
            if (graphParameterList == null) {
                parameterMap = Collections.emptyMap();
            } else {
                parameterMap = graphParameterList.stream().collect(Collectors.toMap(GraphParameter::getName, p -> p));
            }
        }
        return new GraphParameterMap(parameterMap);
    }
}
