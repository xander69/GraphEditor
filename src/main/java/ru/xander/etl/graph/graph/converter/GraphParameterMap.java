package ru.xander.etl.graph.graph.converter;

import org.springframework.util.StringUtils;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.graph.xml.GraphParameter;
import ru.xander.etl.graph.util.Utils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
public class GraphParameterMap {

    private final Map<String, GraphParameter> parameterMap;

    private GraphParameterMap(Map<String, GraphParameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getValue(String parameterName) {
        return getValue(parameterName, null);
    }

    public String getValue(String parameterName, String defaultValue) {
        GraphParameter graphParameter = parameterMap.get(parameterName);
        if (graphParameter == null) {
            return defaultValue;
        }
        return graphParameter.getValue();
    }

    public Integer getIntValue(String parameterName) {
        return getIntValue(parameterName, null);
    }

    public Integer getIntValue(String parameterName, Integer defaultValue) {
        GraphParameter graphParameter = parameterMap.get(parameterName);
        if (graphParameter == null) {
            return defaultValue;
        }
        if (StringUtils.isEmpty(graphParameter.getValue())) {
            return null;
        }
        return Integer.parseInt(graphParameter.getValue());
    }

    public LocalDateTime getDateTimeValue(String parameterValue) {
        GraphParameter graphParameter = parameterMap.get(parameterValue);
        if (graphParameter == null) {
            return null;
        }
        return Utils.parseDateTime(graphParameter.getValue());
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
