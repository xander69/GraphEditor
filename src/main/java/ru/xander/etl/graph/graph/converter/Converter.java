package ru.xander.etl.graph.graph.converter;

import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.xml.Graph;

/**
 * @author Alexander Shakhov
 */
public class Converter {

    public static final String PARAMETER_SCENARIO_NAME = "SCENARIO_NAME";
    public static final String PARAMETER_SCENARIO_VERSION = "SCENARIO_VERSION";
    public static final String PARAMETER_SCENARIO_DESCRIPTION = "SCENARIO_DESCRIPTION";
    public static final String PARAMETER_SCENARIO_OUTER_SYSTEM = "SCENARIO_OUTER_SYSTEM";
    public static final String PARAMETER_SCENARIO_AUTHOR = "SCENARIO_AUTHOR";
    public static final String PARAMETER_SCENARIO_CREATED = "SCENARIO_CREATED";
    public static final String PARAMETER_SCENARIO_UPDATED = "SCENARIO_UPDATED";

    public static final String ATTRIBUTE_PARAM_TYPE = "paramType";
    public static final String ATTRIBUTE_VALUE = "value";
    public static final String ATTRIBUTE_REQUIRED = "required";
    public static final String ATTRIBUTE_SECURE = "secure";
    public static final String ATTRIBUTE_ORDER = "order";
    public static final String ATTRIBUTE_DISPLAY_NAME = "displayName";
    public static final String ATTRIBUTE_DESCRIPTION = "description";
    public static final String ATTRIBUTE_DATA_TYPE = "dataType";
    public static final String ATTRIBUTE_FORMAT = "format";
    public static final String ATTRIBUTE_ENUM_VALUES = "enumValues";
    public static final String ATTRIBUTE_ENUM_SEPARATOR = "enumSeparator";
    public static final String ATTRIBUTE_ENUM_MULTIPLE = "enumMultiple";

    public static final String PARAM_TYPE_SYSTEM = "SYSTEM";
    public static final String PARAM_TYPE_STARTUP = "STARTUP";
    public static final String PARAM_TYPE_CONTEXT = "CONTEXT";
    public static final String PARAM_TYPE_INTERNAL = "INTERNAL";

    public static Graph scenarioToGraph(EtlScenario scenario) {
        return new ScenarioConverter().convertToGraph(scenario);
    }

    public static EtlScenario graphToScenario(Graph graph) {
        return new GraphConverter().convertToScenario(graph);
    }

}
