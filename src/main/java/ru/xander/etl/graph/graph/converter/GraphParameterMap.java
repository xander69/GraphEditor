package ru.xander.etl.graph.graph.converter;

import org.springframework.util.StringUtils;
import ru.xander.etl.graph.graph.etl.EtlParameter;
import ru.xander.etl.graph.graph.etl.EtlParameterType;
import ru.xander.etl.graph.graph.xml.Attr;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.graph.xml.GraphParameter;
import ru.xander.etl.graph.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.xander.etl.graph.graph.converter.Converter.*;

/**
 * @author Alexander Shakhov
 */
public class GraphParameterMap {

    private final Map<String, Map<String, String>> parameterAttributesMap;

    private GraphParameterMap(Map<String, Map<String, String>> parameterAttributesMap) {
        this.parameterAttributesMap = parameterAttributesMap;
    }

    public String getValue(String parameterName) {
        Map<String, String> attributes = parameterAttributesMap.get(parameterName);
        if (attributes == null) {
            return null;
        }
        return attributes.get(ATTRIBUTE_VALUE);
    }

    public List<EtlParameter> getParametersByType(String byType) {
        List<EtlParameter> parameters = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : parameterAttributesMap.entrySet()) {
            Map<String, String> attributes = entry.getValue();
            String paramType = attributes.get(ATTRIBUTE_PARAM_TYPE);
            if (byType.equals(paramType)) {
                EtlParameter parameter = EtlParameter.builder()
                        .order(Utils.parseInt(attributes.get(ATTRIBUTE_ORDER)))
                        .name(entry.getKey())
                        .displayName(attributes.get(ATTRIBUTE_DISPLAY_NAME))
                        .description(attributes.get(ATTRIBUTE_DESCRIPTION))
                        .type(getDataType(attributes.get(ATTRIBUTE_DATA_TYPE)))
                        .format(attributes.get(ATTRIBUTE_FORMAT))
                        .enumValues(attributes.get(ATTRIBUTE_ENUM_VALUES))
                        .enumSeparator(attributes.get(ATTRIBUTE_ENUM_SEPARATOR))
                        .enumMultiple(Boolean.parseBoolean(attributes.get(ATTRIBUTE_ENUM_MULTIPLE)))
                        .value(attributes.get(ATTRIBUTE_VALUE))
                        .required(Boolean.parseBoolean(attributes.get(ATTRIBUTE_REQUIRED)))
                        .secure(Boolean.parseBoolean(attributes.get(ATTRIBUTE_SECURE)))
                        .build();
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    private EtlParameterType getDataType(String dataType) {
        if (StringUtils.isEmpty(dataType)) {
            return EtlParameterType.STRING;
        }
        return EtlParameterType.valueOf(dataType);
    }

    public static GraphParameterMap forGraph(Graph graph) {
        Map<String, Map<String, String>> parameterAttributesMap;
        if (graph.getGlobal() == null) {
            parameterAttributesMap = Collections.emptyMap();
        } else if (graph.getGlobal().getGraphParameters() == null) {
            parameterAttributesMap = Collections.emptyMap();
        } else {
            List<GraphParameter> graphParameterList = graph.getGlobal().getGraphParameters().getGraphParameterList();
            if (graphParameterList == null) {
                parameterAttributesMap = Collections.emptyMap();
            } else {
                parameterAttributesMap = new HashMap<>();
                for (GraphParameter graphParameter : graphParameterList) {
                    Map<String, String> attributes = new HashMap<>();
                    attributes.put(ATTRIBUTE_FORMAT, graphParameter.getFormat());
                    attributes.put(ATTRIBUTE_VALUE, graphParameter.getValue());
                    attributes.put(ATTRIBUTE_REQUIRED, String.valueOf(graphParameter.isRequired()));
                    attributes.put(ATTRIBUTE_SECURE, String.valueOf(graphParameter.isSecure()));
                    List<Attr> attrList = graphParameter.getAttrList();
                    if (attrList != null) {
                        for (Attr attr : attrList) {
                            attributes.put(attr.getName(), attr.getValue());
                        }
                    }
                    parameterAttributesMap.put(graphParameter.getName(), attributes);
                }
            }
        }
        return new GraphParameterMap(parameterAttributesMap);
    }
}
