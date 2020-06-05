package ru.xander.etl.graph.graph.converter;

import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.util.Utils;

import static ru.xander.etl.graph.graph.converter.Converter.*;

/**
 * @author Alexander Shakhov
 */
class GraphConverter {

    public EtlScenario convertToScenario(Graph graph) {
        GraphParameterMap parameterMap = GraphParameterMap.forGraph(graph);

        return EtlScenario.builder()
                .id(graph.getId())
                .name(Utils.nvl(parameterMap.getValue(PARAMETER_SCENARIO_NAME), graph.getName()))
                .author(Utils.nvl(parameterMap.getValue(PARAMETER_SCENARIO_AUTHOR), graph.getAuthor()))
                .version(Utils.parseInt(parameterMap.getValue(PARAMETER_SCENARIO_VERSION)))
                .description(parameterMap.getValue(PARAMETER_SCENARIO_DESCRIPTION))
                .outerSystem(parameterMap.getValue(PARAMETER_SCENARIO_OUTER_SYSTEM))
                .created(Utils.parseDateTime(parameterMap.getValue(PARAMETER_SCENARIO_CREATED)))
                .updated(Utils.parseDateTime(parameterMap.getValue(PARAMETER_SCENARIO_UPDATED)))
                .startupParams(parameterMap.getParametersByType(PARAM_TYPE_STARTUP))
                .contextParams(parameterMap.getParametersByType(PARAM_TYPE_CONTEXT))
                .internalParams(parameterMap.getParametersByType(PARAM_TYPE_INTERNAL))
                .stages(parameterMap.getStages())
                .build();
    }
}
