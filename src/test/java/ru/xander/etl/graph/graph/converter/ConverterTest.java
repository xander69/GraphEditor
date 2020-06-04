package ru.xander.etl.graph.graph.converter;

import org.junit.Assert;
import org.junit.Test;
import ru.xander.etl.graph.graph.etl.EtlScenario;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.util.GraphMarshaller;

import java.time.LocalDateTime;

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
            "            <GraphParameter name=\"SCENARIO_NAME\" value=\"DEMO_SCENARIO\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Имя сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_VERSION\" value=\"1\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Версия сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_DESCRIPTION\" value=\"Demo scenario\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Описание сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_OUTER_SYSTEM\" value=\"DEMO\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Код системы-источника]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_AUTHOR\" value=\"Alexander\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Автор сценария]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_CREATED\" value=\"2020-01-01 12:00:00.000\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Дата создания]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"SCENARIO_UPDATED\" value=\"2020-01-02 15:30:00.000\" required=\"false\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Дата последней правки]]></attr>\n" +
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
    }
}