package ru.xander.etl.graph.graph.converter;

import org.springframework.util.StringUtils;
import ru.xander.etl.graph.graph.etl.EtlParameter;
import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.etl.EtlStage;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.xander.etl.graph.graph.converter.Converter.*;

/**
 * @author Alexander Shakhov
 */
class ScenarioConverter {

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

        for (EtlParameter startupParam : scenario.getStartupParams()) {
            graphParameterList.add(createStartupParameter(startupParam));
        }

        for (EtlParameter contextParam : scenario.getContextParams()) {
            graphParameterList.add(createContextParameter(contextParam));
        }

        for (EtlParameter internalParam : scenario.getInternalParams()) {
            graphParameterList.add(createInternalParameter(internalParam));
        }

        for (EtlStage stage : scenario.getStages()) {
            graphParameterList.add(createStage(stage));
            if (stage.getParameters() != null) {
                for (EtlParameter stageParameter : stage.getParameters()) {
                    graphParameterList.add(createStageParameter(stageParameter, stage));
                }
            }
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
        parameter.setRequired(true);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, scenario.getName()),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Имя сценария")));
        return parameter;
    }

    private GraphParameter createScenarioVersionParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_VERSION);
        parameter.setRequired(true);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, String.valueOf(scenario.getVersion())),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Версия сценария")));
        return parameter;
    }

    private GraphParameter createScenarioDescriptionParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_DESCRIPTION);
        parameter.setRequired(true);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, scenario.getDescription()),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Описание сценария")));
        return parameter;
    }

    private GraphParameter createScenarioOuterSystemParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_OUTER_SYSTEM);
        parameter.setRequired(true);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, scenario.getOuterSystem()),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Код системы-источника")));
        return parameter;
    }

    private GraphParameter createScenarioAuthorParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_AUTHOR);
        parameter.setRequired(false);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, scenario.getAuthor()),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Автор сценария")));
        return parameter;
    }

    private GraphParameter createScenarioCreatedParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_CREATED);
        parameter.setRequired(false);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, Utils.formatDateTime(scenario.getCreated())),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Дата создания")));
        return parameter;
    }

    private GraphParameter createScenarioUpdatedParameter(EtlScenario scenario) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(PARAMETER_SCENARIO_UPDATED);
        parameter.setRequired(false);
        parameter.setAttrList(Arrays.asList(
                createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_SYSTEM),
                createAttr(ATTRIBUTE_VALUE, Utils.formatDateTime(scenario.getUpdated())),
                createAttr(ATTRIBUTE_DISPLAY_NAME, "Дата последней правки")));
        return parameter;
    }

    private GraphParameter createStartupParameter(EtlParameter startupParam) {
        return createParameter(startupParam, PARAM_TYPE_STARTUP);
    }

    private GraphParameter createContextParameter(EtlParameter startupParam) {
        return createParameter(startupParam, PARAM_TYPE_CONTEXT);
    }

    private GraphParameter createInternalParameter(EtlParameter startupParam) {
        return createParameter(startupParam, PARAM_TYPE_INTERNAL);
    }

    private GraphParameter createStage(EtlStage stage) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(stage.getName());

        List<Attr> attrList = new ArrayList<>();
        attrList.add(createAttr(ATTRIBUTE_PARAM_TYPE, PARAM_TYPE_STAGE));
        attrList.add(createAttr(ATTRIBUTE_PHASE_NUM, String.valueOf(stage.getPhaseNum())));
        if (stage.getDisplayName() != null) {
            attrList.add(createAttr(ATTRIBUTE_DISPLAY_NAME, stage.getDisplayName()));
        }
        if (stage.getDescription() != null) {
            attrList.add(createAttr(ATTRIBUTE_DESCRIPTION, stage.getDescription()));
        }
        attrList.add(createAttr(ATTRIBUTE_VALUE, String.valueOf(stage.isEnabled())));

        parameter.setAttrList(attrList);
        return parameter;
    }

    private GraphParameter createStageParameter(EtlParameter stageParameter, EtlStage stage) {
        GraphParameter graphParameter = createParameter(stageParameter, PARAM_TYPE_STAGE_STARTUP);
        graphParameter.getAttrList().add(createAttr(ATTRIBUTE_PHASE_NUM, String.valueOf(stage.getPhaseNum())));
        return graphParameter;
    }

    private GraphParameter createParameter(EtlParameter etlParameter, String paramType) {
        GraphParameter parameter = new GraphParameter();
        parameter.setName(etlParameter.getName());
        parameter.setRequired(etlParameter.isRequired());
        parameter.setSecure(etlParameter.isSecure());

        List<Attr> attrList = new ArrayList<>();
        attrList.add(createAttr(ATTRIBUTE_PARAM_TYPE, paramType));
        if (etlParameter.getOrder() != null) {
            attrList.add(createAttr(ATTRIBUTE_ORDER, String.valueOf(etlParameter.getOrder())));
        }
        if (etlParameter.getDisplayName() != null) {
            attrList.add(createAttr(ATTRIBUTE_DISPLAY_NAME, etlParameter.getDisplayName()));
        }
        if (etlParameter.getDescription() != null) {
            attrList.add(createAttr(ATTRIBUTE_DESCRIPTION, etlParameter.getDescription()));
        }
        if (etlParameter.getType() != null) {
            attrList.add(createAttr(ATTRIBUTE_DATA_TYPE, etlParameter.getType().name()));
        }
        if (etlParameter.getFormat() != null) {
            attrList.add(createAttr(ATTRIBUTE_FORMAT, etlParameter.getFormat()));
        }
        if (etlParameter.getEnumValues() != null) {
            attrList.add(createAttr(ATTRIBUTE_ENUM_VALUES, etlParameter.getEnumValues()));
        }
        if (etlParameter.getEnumSeparator() != null) {
            attrList.add(createAttr(ATTRIBUTE_ENUM_SEPARATOR, etlParameter.getEnumSeparator()));
        }
        if (etlParameter.getEnumMultiple() != null) {
            attrList.add(createAttr(ATTRIBUTE_ENUM_MULTIPLE, String.valueOf(etlParameter.getEnumMultiple())));
        }
        if (etlParameter.getValue() != null) {
            attrList.add(createAttr(ATTRIBUTE_VALUE, etlParameter.getValue()));
        }

        parameter.setAttrList(attrList);
        return parameter;
    }

    private Attr createAttr(String name, String value) {
        Attr attr = new Attr();
        attr.setName(name);
        attr.setValue(value);
        return attr;
    }

}
