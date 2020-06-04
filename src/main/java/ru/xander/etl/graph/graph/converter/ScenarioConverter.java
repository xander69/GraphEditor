package ru.xander.etl.graph.graph.converter;

import org.springframework.util.StringUtils;
import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.xml.Attr;
import ru.xander.etl.graph.graph.xml.Field;
import ru.xander.etl.graph.graph.xml.Global;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.graph.xml.GraphParameter;
import ru.xander.etl.graph.graph.xml.GraphParameters;
import ru.xander.etl.graph.graph.xml.Metadata;
import ru.xander.etl.graph.graph.xml.Record;
import ru.xander.etl.graph.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.xander.etl.graph.graph.converter.Converter.*;

/**
 * @author Alexander Shakhov
 */
class ScenarioConverter {

    public static final String ATTRIBUTE_DISPLAY_NAME = "displayName";

    public Graph convertToGraph(EtlScenario scenario) {
        Graph graph = new Graph();
        graph.setId(scenario.getId());
        graph.setName(scenario.getName());
        graph.setAuthor(scenario.getAuthor());
        graph.setCreated(Utils.formatDateTime(scenario.getCreated()));
        graph.setLicenseCode("LICENSED");

        List<GraphParameter> graphParameterList = new ArrayList<>();

        Global global = new Global();
        GraphParameters graphParameters = new GraphParameters();
        graphParameters.setGraphParameterList(graphParameterList);

        graphParameterList.add(createScenarioNameParameter(scenario));
        graphParameterList.add(createScenarioVersionParameter(scenario));
        graphParameterList.add(createScenarioDescriptionParameter(scenario));
        graphParameterList.add(createScenarioOuterSystemParameter(scenario));
        if (!StringUtils.isEmpty(scenario.getAuthor())) {
            graphParameterList.add(createScenarioAuthorParameter(scenario));
        }
        if (scenario.getCreated() != null) {
            graphParameterList.add(createScenarioCreatedParameter(scenario));
        }
        if (scenario.getUpdated() != null) {
            graphParameterList.add(createScenarioUpdatedParameter(scenario));
        }

        global.setMetadataList(Collections.singletonList(createMetadataSignal()));
        global.setGraphParameters(graphParameters);
        graph.setGlobal(global);

        return graph;
    }

    private Metadata createMetadataSignal() {
        Metadata metadata = new Metadata();
        metadata.setId("signal");

        List<Field> fields = new ArrayList<>();
        Field field = new Field();
        field.setName("dummy");
        field.setType("boolean");
        field.setDefaultValue("true");
        fields.add(field);

        Record record = new Record();
        record.setName("signal");
        record.setFieldList(fields);

        metadata.setRecord(record);
        return metadata;
    }

    private GraphParameter createScenarioNameParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_NAME);
        parameter.setValue(scenario.getName());
        parameter.setRequired(true);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Имя сценария")));
        return parameter;
    }

    private GraphParameter createScenarioVersionParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_VERSION);
        parameter.setValue(String.valueOf(scenario.getVersion()));
        parameter.setRequired(true);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Версия сценария")));
        return parameter;
    }

    private GraphParameter createScenarioDescriptionParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_DESCRIPTION);
        parameter.setValue(scenario.getDescription());
        parameter.setRequired(true);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Описание сценария")));
        return parameter;
    }

    private GraphParameter createScenarioOuterSystemParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_OUTER_SYSTEM);
        parameter.setValue(scenario.getOuterSystem());
        parameter.setRequired(true);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Код системы-источника")));
        return parameter;
    }

    private GraphParameter createScenarioAuthorParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_AUTHOR);
        parameter.setValue(scenario.getAuthor());
        parameter.setRequired(false);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Автор сценария")));
        return parameter;
    }

    private GraphParameter createScenarioCreatedParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_CREATED);
        parameter.setValue(Utils.formatDateTime(scenario.getCreated()));
        parameter.setRequired(false);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Дата создания")));
        return parameter;
    }

    private GraphParameter createScenarioUpdatedParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_UPDATED);
        parameter.setValue(Utils.formatDateTime(scenario.getUpdated()));
        parameter.setRequired(false);
        parameter.setAttrList(Collections.singletonList(createAttr(ATTRIBUTE_DISPLAY_NAME, "Дата последней правки")));
        return parameter;
    }

    private Attr createAttr(String name, String value) {
        Attr attr = new Attr();
        attr.setName(name);
        attr.setValue(value);
        return attr;
    }

}
