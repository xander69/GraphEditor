package ru.xander.etl.graph.graph.converter;

import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.xml.Graph;

import static ru.xander.etl.graph.graph.converter.Converter.*;

/**
 * @author Alexander Shakhov
 */
class GraphConverter {

    public EtlScenario convertToScenario(Graph graph) {
        GraphParameterMap parameterMap = GraphParameterMap.forGraph(graph);

        return EtlScenario.builder()
                .id(graph.getId())
                .name(parameterMap.getValue(PARAMETER_SCENARIO_NAME, graph.getName()))
                .author(parameterMap.getValue(PARAMETER_SCENARIO_AUTHOR, graph.getAuthor()))
                .version(parameterMap.getIntValue(PARAMETER_SCENARIO_VERSION))
                .description(parameterMap.getValue(PARAMETER_SCENARIO_DESCRIPTION))
                .outerSystem(parameterMap.getValue(PARAMETER_SCENARIO_OUTER_SYSTEM))
                .created(parameterMap.getDateTimeValue(PARAMETER_SCENARIO_CREATED))
                .updated(parameterMap.getDateTimeValue(PARAMETER_SCENARIO_UPDATED))
                .build();
    }
}
