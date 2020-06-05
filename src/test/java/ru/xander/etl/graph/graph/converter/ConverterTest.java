package ru.xander.etl.graph.graph.converter;

import org.junit.Assert;
import org.junit.Test;
import ru.xander.etl.graph.graph.etl.EtlParameter;
import ru.xander.etl.graph.graph.etl.EtlParameterType;
import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.etl.EtlStage;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.util.GraphMarshaller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
public class ConverterTest {

    private static final String GRAPH_XML = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
            "<Graph id=\"1591262961630\" name=\"DEMO_SCENARIO\" author=\"Alexander\" created=\"2020-01-01 12:00:00.000\" guiVersion=\"5.1.1\" licenseCode=\"LICENSED\">\n" +
            "    <Global>\n" +
            "        <Metadata id=\"signal\">\n" +
            "            <Record name=\"signal\" type=\"delimited\" fieldDelimiter=\";\" recordDelimiter=\"\\n\" previewAttachmentCharset=\"UTF-8\">\n" +
            "                <Field name=\"dummy\" type=\"boolean\" default=\"true\"/>\n" +
            "            </Record>\n" +
            "        </Metadata>\n" +
            "        <GraphParameters>\n" +
            "            <GraphParameter name=\"SCENARIO_NAME\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[DEMO_SCENARIO]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Имя сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_VERSION\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[1]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Версия сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_DESCRIPTION\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[Demo scenario]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Описание сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_OUTER_SYSTEM\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[DEMO]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Код системы-источника]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_AUTHOR\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[Alexander]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Автор сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_CREATED\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[2020-01-01 12:00:00.000]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Дата создания]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_UPDATED\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[SYSTEM]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[2020-01-02 15:30:00.000]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Дата последней правки]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"INFO_BLOCK\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[STARTUP]]></attr>\n" +
            "                <attr name=\"order\"><![CDATA[1]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Блок информации]]></attr>\n" +
            "                <attr name=\"description\"><![CDATA[Закачиваемые блоки информации]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[ENUM]]></attr>\n" +
            "                <attr name=\"enumValues\"><![CDATA[INCOMES|OUTCOMES|SOURCES]]></attr>\n" +
            "                <attr name=\"enumSeparator\"><![CDATA[|]]></attr>\n" +
            "                <attr name=\"enumMultiple\"><![CDATA[true]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"LOAD_DATE\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[STARTUP]]></attr>\n" +
            "                <attr name=\"order\"><![CDATA[2]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Дата загрузки]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[DATETIME]]></attr>\n" +
            "                <attr name=\"format\"><![CDATA[yyyy-MM-dd]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[2020-01-01]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"JDBC_URL\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[CONTEXT]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[JDBC-url]]></attr>\n" +
            "                <attr name=\"description\"><![CDATA[Строка подключения к БД]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[STRING]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"JDBC_USER\" required=\"true\" secure=\"true\">\n" +
            "                <attr name=\"paramType\"><![CDATA[CONTEXT]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Пользователь БД]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[STRING]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"HTTP_TIMEOUT\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[CONTEXT]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[HTTP таймаут]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[INTEGER]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SUBFOLDER_IN\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[INTERNAL]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[STRING]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[DEMO_FOLDER]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"LOADING_STAGE\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[STAGE]]></attr>\n" +
            "                <attr name=\"phaseNum\"><![CDATA[0]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Этап загрузки]]></attr>\n" +
            "                <attr name=\"description\"><![CDATA[Выполняет извлечение данных и их загрузку в стейджинг]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[true]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"VALIDATE\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[STAGE_STARTUP]]></attr>\n" +
            "                <attr name=\"order\"><![CDATA[1]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Валидация]]></attr>\n" +
            "                <attr name=\"description\"><![CDATA[Выполнять валидацию файлов по XSD]]></attr>\n" +
            "                <attr name=\"dataType\"><![CDATA[BOOLEAN]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[true]]></attr>\n" +
            "                <attr name=\"phaseNum\"><![CDATA[0]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"PUBLISH_STAGE\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"paramType\"><![CDATA[STAGE]]></attr>\n" +
            "                <attr name=\"phaseNum\"><![CDATA[1]]></attr>\n" +
            "                <attr name=\"displayName\"><![CDATA[Этап публикации]]></attr>\n" +
            "                <attr name=\"value\"><![CDATA[false]]></attr>\n" +
            "            </GraphParameter>\n" +
            "        </GraphParameters>\n" +
            "    </Global>\n" +
            "</Graph>\n";

    @Test
    public void scenarioToGraph() {
        EtlScenario scenario = EtlScenario.builder()
                .id(1591262961630L)
                .name("DEMO_SCENARIO")
                .version(1)
                .description("Demo scenario")
                .outerSystem("DEMO")
                .author("Alexander")
                .created(LocalDateTime.of(2020, 1, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2020, 1, 2, 15, 30, 0))
                //region startupParams
                .startupParams(Arrays.asList(
                        EtlParameter.builder()
                                .order(1)
                                .name("INFO_BLOCK")
                                .displayName("Блок информации")
                                .description("Закачиваемые блоки информации")
                                .type(EtlParameterType.ENUM)
                                .enumValues("INCOMES|OUTCOMES|SOURCES")
                                .enumSeparator("|")
                                .enumMultiple(true)
                                .required(true)
                                .build(),
                        EtlParameter.builder()
                                .order(2)
                                .name("LOAD_DATE")
                                .displayName("Дата загрузки")
                                .type(EtlParameterType.DATETIME)
                                .format("yyyy-MM-dd")
                                .required(false)
                                .value("2020-01-01")
                                .build()
                ))
                //endregion
                //region contextParams
                .contextParams(Arrays.asList(
                        EtlParameter.builder()
                                .name("JDBC_URL")
                                .displayName("JDBC-url")
                                .description("Строка подключения к БД")
                                .type(EtlParameterType.STRING)
                                .required(true)
                                .secure(false)
                                .build(),
                        EtlParameter.builder()
                                .name("JDBC_USER")
                                .displayName("Пользователь БД")
                                .type(EtlParameterType.STRING)
                                .required(true)
                                .secure(true)
                                .build(),
                        EtlParameter.builder()
                                .name("HTTP_TIMEOUT")
                                .displayName("HTTP таймаут")
                                .type(EtlParameterType.INTEGER)
                                .required(false)
                                .secure(false)
                                .build()
                ))
                //endregion
                //region internalParams
                .internalParams(Collections.singletonList(
                        EtlParameter.builder()
                                .name("SUBFOLDER_IN")
                                .type(EtlParameterType.STRING)
                                .value("DEMO_FOLDER")
                                .build()
                ))
                //endregion
                //region stages
                .stages(Arrays.asList(
                        EtlStage.builder()
                                .phaseNum(0)
                                .name("LOADING_STAGE")
                                .displayName("Этап загрузки")
                                .description("Выполняет извлечение данных и их загрузку в стейджинг")
                                .enabled(true)
                                .parameters(Collections.singletonList(
                                        EtlParameter.builder()
                                                .order(1)
                                                .name("VALIDATE")
                                                .displayName("Валидация")
                                                .description("Выполнять валидацию файлов по XSD")
                                                .type(EtlParameterType.BOOLEAN)
                                                .required(true)
                                                .value("true")
                                                .build()
                                ))
                                .build(),
                        EtlStage.builder()
                                .phaseNum(1)
                                .name("PUBLISH_STAGE")
                                .displayName("Этап публикации")
                                .enabled(false)
                                .build()
                ))
                //endregion
                .build();

        Graph graph = Converter.scenarioToGraph(scenario);
        String xml = GraphMarshaller.marshal(graph);
        Assert.assertEquals(GRAPH_XML, xml);
    }

    @Test
    public void graphToScenario() {
        Graph graph = GraphMarshaller.unmarshal(GRAPH_XML);

        EtlScenario scenario = Converter.graphToScenario(graph);
        Assert.assertEquals(1591262961630L, scenario.getId());
        Assert.assertEquals("DEMO_SCENARIO", scenario.getName());
        Assert.assertEquals(1, scenario.getVersion().intValue());
        Assert.assertEquals("Demo scenario", scenario.getDescription());
        Assert.assertEquals("DEMO", scenario.getOuterSystem());
        Assert.assertEquals("Alexander", scenario.getAuthor());
        Assert.assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0, 0), scenario.getCreated());
        Assert.assertEquals(LocalDateTime.of(2020, 1, 2, 15, 30, 0), scenario.getUpdated());

        List<EtlParameter> startupParams = scenario.getStartupParams();
        Assert.assertNotNull(startupParams);
        Assert.assertEquals(2, startupParams.size());

        startupParams.sort(Comparator.comparingInt(EtlParameter::getOrder));

        assertEtlParameter(startupParams.get(0),
                1, "INFO_BLOCK",
                "Блок информации", "Закачиваемые блоки информации",
                EtlParameterType.ENUM, null, true, false);
        Assert.assertEquals("INCOMES|OUTCOMES|SOURCES", startupParams.get(0).getEnumValues());
        Assert.assertEquals("|", startupParams.get(0).getEnumSeparator());
        Assert.assertTrue(startupParams.get(0).getEnumMultiple());

        assertEtlParameter(startupParams.get(1),
                2, "LOAD_DATE",
                "Дата загрузки", null,
                EtlParameterType.DATETIME, "2020-01-01", false, false);
        Assert.assertEquals("yyyy-MM-dd", startupParams.get(1).getFormat());

        List<EtlParameter> contextParams = scenario.getContextParams();
        Assert.assertNotNull(contextParams);
        Assert.assertEquals(3, contextParams.size());

        contextParams.sort(Comparator.comparing(EtlParameter::getName));

        assertEtlParameter(contextParams.get(0),
                null, "HTTP_TIMEOUT",
                "HTTP таймаут", null,
                EtlParameterType.INTEGER, null, false, false);
        assertEtlParameter(contextParams.get(1),
                null, "JDBC_URL",
                "JDBC-url", "Строка подключения к БД",
                EtlParameterType.STRING, null, true, false);
        assertEtlParameter(contextParams.get(2),
                null, "JDBC_USER",
                "Пользователь БД", null,
                EtlParameterType.STRING, null, true, true);

        List<EtlParameter> internalParams = scenario.getInternalParams();
        Assert.assertNotNull(internalParams);
        Assert.assertEquals(1, internalParams.size());

        assertEtlParameter(internalParams.get(0),
                null, "SUBFOLDER_IN", null, null, EtlParameterType.STRING, "DEMO_FOLDER", false, false);

        List<EtlStage> stages = scenario.getStages();
        Assert.assertNotNull(stages);
        Assert.assertEquals(2, stages.size());

        stages.sort(Comparator.comparingInt(EtlStage::getPhaseNum));

        assertEtlStage(stages.get(0),
                0, "LOADING_STAGE",
                "Этап загрузки",
                "Выполняет извлечение данных и их загрузку в стейджинг",
                true);

        assertEtlStage(stages.get(1),
                1, "PUBLISH_STAGE",
                "Этап публикации", null, false);

        List<EtlParameter> stageParameters = stages.get(0).getParameters();
        Assert.assertNotNull(stageParameters);
        Assert.assertEquals(1, stageParameters.size());

        assertEtlParameter(stageParameters.get(0),
                1, "VALIDATE",
                "Валидация", "Выполнять валидацию файлов по XSD",
                EtlParameterType.BOOLEAN, "true", true, false);
    }

    private static void assertEtlParameter(EtlParameter expected,
                                           Integer order,
                                           String name,
                                           String displayName,
                                           String description,
                                           EtlParameterType type,
                                           String value,
                                           Boolean required,
                                           Boolean secure) {
        Assert.assertNotNull(expected);
        Assert.assertEquals(order, expected.getOrder());
        Assert.assertEquals(name, expected.getName());
        Assert.assertEquals(displayName, expected.getDisplayName());
        Assert.assertEquals(description, expected.getDescription());
        Assert.assertEquals(type, expected.getType());
        Assert.assertEquals(value, expected.getValue());
        Assert.assertEquals(required, expected.isRequired());
        Assert.assertEquals(secure, expected.isSecure());
    }

    private static void assertEtlStage(EtlStage expected,
                                       int phaseNum,
                                       String name,
                                       String displayName,
                                       String description,
                                       boolean enabled) {
        Assert.assertNotNull(expected);
        Assert.assertEquals(phaseNum, expected.getPhaseNum());
        Assert.assertEquals(name, expected.getName());
        Assert.assertEquals(displayName, expected.getDisplayName());
        Assert.assertEquals(description, expected.getDescription());
        Assert.assertEquals(enabled, expected.isEnabled());
    }
}