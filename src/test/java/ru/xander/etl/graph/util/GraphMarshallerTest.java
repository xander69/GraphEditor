package ru.xander.etl.graph.util;

import org.junit.Assert;
import org.junit.Test;
import ru.xander.etl.graph.graph.xml.Attr;
import ru.xander.etl.graph.graph.xml.Dictionary;
import ru.xander.etl.graph.graph.xml.Field;
import ru.xander.etl.graph.graph.xml.Global;
import ru.xander.etl.graph.graph.xml.Graph;
import ru.xander.etl.graph.graph.xml.GraphParameter;
import ru.xander.etl.graph.graph.xml.GraphParameters;
import ru.xander.etl.graph.graph.xml.Metadata;
import ru.xander.etl.graph.graph.xml.MetadataGroup;
import ru.xander.etl.graph.graph.xml.Record;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
public class GraphMarshallerTest {

    private static final String GRAPH_XML = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
            "<Graph id=\"1591262961630\" name=\"DEMO_GRAPH\" author=\"shahov\" created=\"2020-01-01 12:00:00\" guiVersion=\"5.1.2\" licenseCode=\"Licensed\">\n" +
            "    <Global>\n" +
            "        <MetadataGroup id=\"MetadataGroup0\" name=\"Staging\">\n" +
            "            <Metadata id=\"Metadata0\">\n" +
            "                <Record name=\"SAMPLE_TABLE\" type=\"delimited\" fieldDelimiter=\";\" recordDelimiter=\"\\n\" previewAttachmentCharset=\"UTF-8\">\n" +
            "                    <Field name=\"Field1\" type=\"string\" size=\"255\" scale=\"0\">\n" +
            "                        <attr name=\"description\"><![CDATA[Description of Field1]]></attr>\n" +
            "                    </Field>\n" +
            "                    <Field name=\"Field2\" type=\"date\" format=\"yyyy-MM-dd\"/>\n" +
            "                </Record>\n" +
            "            </Metadata>\n" +
            "            <Metadata id=\"signal\">\n" +
            "                <Record name=\"signal\" type=\"delimited\" fieldDelimiter=\";\" recordDelimiter=\"\\n\" previewAttachmentCharset=\"UTF-8\">\n" +
            "                    <Field name=\"dummy\" type=\"boolean\" default=\"true\"/>\n" +
            "                </Record>\n" +
            "            </Metadata>\n" +
            "        </MetadataGroup>\n" +
            "        <Metadata id=\"signal\">\n" +
            "            <Record name=\"signal\" type=\"delimited\" fieldDelimiter=\";\" recordDelimiter=\"\\n\" previewAttachmentCharset=\"UTF-8\">\n" +
            "                <Field name=\"dummy\" type=\"boolean\" default=\"true\"/>\n" +
            "            </Record>\n" +
            "        </Metadata>\n" +
            "        <GraphParameters>\n" +
            "            <GraphParameter name=\"PARAMETER1\" value=\"VALUE\" required=\"true\" secure=\"false\">\n" +
            "                <attr name=\"displayName\"><![CDATA[Parameter name]]></attr>\n" +
            "            </GraphParameter>\n" +
            "            <GraphParameter name=\"PARAMETER2\" type=\"date\" format=\"yyyy-MM-dd\" value=\"2020-01-01\" required=\"false\" secure=\"true\"/>\n" +
            "        </GraphParameters>\n" +
            "        <Dictionary/>\n" +
            "    </Global>\n" +
            "</Graph>\n";

    @Test
    public void marshal() {
        Graph graph = new Graph();
        graph.setId(1591262961630L);
        graph.setName("DEMO_GRAPH");
        graph.setAuthor("shahov");
        graph.setCreated("2020-01-01 12:00:00");
        graph.setLicenseCode("Licensed");
        graph.setGuiVersion("5.1.2");
        graph.setGlobal(createGlobal());

        String xml = GraphMarshaller.marshal(graph);
        Assert.assertEquals(GRAPH_XML, xml);
    }

    @Test
    public void unmarshal() {
        Graph graph = GraphMarshaller.unmarshal(GRAPH_XML);

        Assert.assertNotNull(graph);
        Assert.assertEquals(1591262961630L, graph.getId());
        Assert.assertEquals("DEMO_GRAPH", graph.getName());
        Assert.assertEquals("shahov", graph.getAuthor());
        Assert.assertEquals("2020-01-01 12:00:00", graph.getCreated());
        Assert.assertEquals("Licensed", graph.getLicenseCode());
        Assert.assertEquals("5.1.2", graph.getGuiVersion());

        Global global = graph.getGlobal();
        Assert.assertNotNull(global);

        List<MetadataGroup> metadataGroupList = global.getMetadataGroupList();
        Assert.assertNotNull(metadataGroupList);
        Assert.assertEquals(1, metadataGroupList.size());

        MetadataGroup metadataGroup = metadataGroupList.get(0);
        Assert.assertNotNull(metadataGroup);
        Assert.assertEquals("MetadataGroup0", metadataGroup.getId());
        Assert.assertEquals("Staging", metadataGroup.getName());

        List<Metadata> metadataList = metadataGroup.getMetadataList();
        Assert.assertNotNull(metadataList);
        Assert.assertEquals(2, metadataList.size());

        Metadata metadata1 = metadataList.get(0);
        Assert.assertNotNull(metadata1);
        Assert.assertEquals("Metadata0", metadata1.getId());

        Record record1 = metadata1.getRecord();
        Assert.assertNotNull(record1);
        Assert.assertEquals("SAMPLE_TABLE", record1.getName());
        Assert.assertEquals("delimited", record1.getType());
        Assert.assertEquals(";", record1.getFieldDelimiter());
        Assert.assertEquals("\\n", record1.getRecordDelimiter());
        Assert.assertEquals("UTF-8", record1.getPreviewAttachmentCharset());
        List<Field> fieldList1 = record1.getFieldList();
        Assert.assertNotNull(fieldList1);
        Assert.assertEquals(2, fieldList1.size());
        assertField(fieldList1.get(0), "Field1", "string", null, 255, 0, null, attr("description", "Description of Field1"));
        assertField(fieldList1.get(1), "Field2", "date", "yyyy-MM-dd", null, null, null);

        Metadata metadata2 = metadataList.get(1);
        Assert.assertNotNull(metadata2);
        Assert.assertEquals("signal", metadata2.getId());

        Record record2 = metadata2.getRecord();
        Assert.assertNotNull(record2);
        Assert.assertEquals("signal", record2.getName());
        List<Field> fieldList2 = record2.getFieldList();
        Assert.assertNotNull(fieldList2);
        Assert.assertEquals(1, fieldList2.size());
        assertField(fieldList2.get(0), "dummy", "boolean", null, null, null, "true");

        List<Metadata> globalMetadataList = global.getMetadataList();
        Assert.assertNotNull(globalMetadataList);
        Assert.assertEquals(1, globalMetadataList.size());

        Metadata metadata = globalMetadataList.get(0);
        Assert.assertNotNull(metadata);
        Assert.assertEquals("signal", metadata.getId());

        Record record = metadata.getRecord();
        Assert.assertNotNull(record);
        Assert.assertEquals("signal", record.getName());
        List<Field> fieldList = record.getFieldList();
        Assert.assertNotNull(fieldList);
        Assert.assertEquals(1, fieldList.size());
        assertField(fieldList.get(0), "dummy", "boolean", null, null, null, "true");

        GraphParameters graphParameters = global.getGraphParameters();
        Assert.assertNotNull(graphParameters);
        List<GraphParameter> graphParameterList = graphParameters.getGraphParameterList();
        Assert.assertNotNull(graphParameterList);
        Assert.assertEquals(2, graphParameterList.size());
        assertGraphParameter(graphParameterList.get(0), "PARAMETER1", "VALUE", null, null, true, false, attr("displayName", "Parameter name"));
        assertGraphParameter(graphParameterList.get(1), "PARAMETER2", "2020-01-01", "date", "yyyy-MM-dd", false, true);

        Assert.assertNotNull(global.getDictionary());
    }

    private Global createGlobal() {
        Global global = new Global();
        global.setMetadataGroupList(Collections.singletonList(createMetadataGroup()));
        global.setMetadataList(Collections.singletonList(createMetadataSignal()));
        global.setGraphParameters(createGraphParameters());
        global.setDictionary(new Dictionary());
        return global;
    }

    private MetadataGroup createMetadataGroup() {
        MetadataGroup metadataGroup = new MetadataGroup();
        metadataGroup.setId("MetadataGroup0");
        metadataGroup.setName("Staging");
        metadataGroup.setMetadataList(Arrays.asList(createMetadata(), createMetadataSignal()));
        return metadataGroup;
    }

    private Metadata createMetadata() {
        Metadata metadata = new Metadata();
        metadata.setId("Metadata0");

        Record record = new Record();
        record.setName("SAMPLE_TABLE");
        metadata.setRecord(record);

        Field field1 = new Field();
        field1.setName("Field1");
        field1.setType("string");
        field1.setSize(255);
        field1.setScale(0);

        Attr attr = new Attr();
        attr.setName("description");
        attr.setValue("Description of Field1");
        field1.setAttrList(Collections.singletonList(attr));

        Field field2 = new Field();
        field2.setName("Field2");
        field2.setType("date");
        field2.setFormat("yyyy-MM-dd");

        record.setFieldList(Arrays.asList(field1, field2));
        return metadata;
    }

    private Metadata createMetadataSignal() {
        Metadata metadataSignal = new Metadata();
        metadataSignal.setId("signal");

        Record recordSignal = new Record();
        recordSignal.setName("signal");

        Field dummy = new Field();
        dummy.setName("dummy");
        dummy.setType("boolean");
        dummy.setDefaultValue("true");
        recordSignal.setFieldList(Collections.singletonList(dummy));

        metadataSignal.setRecord(recordSignal);
        return metadataSignal;
    }

    private GraphParameters createGraphParameters() {
        GraphParameters graphParameters = new GraphParameters();

        GraphParameter parameter1 = new GraphParameter();
        parameter1.setName("PARAMETER1");
        parameter1.setValue("VALUE");
        parameter1.setRequired(true);
        parameter1.setSecure(false);

        Attr attr = new Attr();
        attr.setName("displayName");
        attr.setValue("Parameter name");
        parameter1.setAttrList(Collections.singletonList(attr));

        GraphParameter parameter2 = new GraphParameter();
        parameter2.setName("PARAMETER2");
        parameter2.setValue("2020-01-01");
        parameter2.setType("date");
        parameter2.setFormat("yyyy-MM-dd");
        parameter2.setSecure(true);

        graphParameters.setGraphParameterList(Arrays.asList(parameter1, parameter2));

        return graphParameters;
    }

    private static void assertField(Field expected,
                                    String name,
                                    String type,
                                    String format,
                                    Integer size,
                                    Integer scale,
                                    String defaultValue, AttrTest... attrs) {
        Assert.assertNotNull(expected);
        Assert.assertEquals(name, expected.getName());
        Assert.assertEquals(type, expected.getType());
        Assert.assertEquals(format, expected.getFormat());
        Assert.assertEquals(size, expected.getSize());
        Assert.assertEquals(scale, expected.getScale());
        Assert.assertEquals(defaultValue, expected.getDefaultValue());
        assertAttrs(expected.getAttrList(), attrs);
    }

    private static void assertGraphParameter(GraphParameter expected,
                                             String name,
                                             String value,
                                             String type,
                                             String format,
                                             boolean requiered,
                                             boolean secure, AttrTest... attrs) {
        Assert.assertNotNull(expected);
        Assert.assertEquals(name, expected.getName());
        Assert.assertEquals(value, expected.getValue());
        Assert.assertEquals(type, expected.getType());
        Assert.assertEquals(format, expected.getFormat());
        Assert.assertEquals(requiered, expected.isRequired());
        Assert.assertEquals(secure, expected.isSecure());
        assertAttrs(expected.getAttrList(), attrs);
    }

    private static void assertAttrs(List<Attr> attrList, AttrTest[] attrs) {
        if ((attrs != null) && (attrs.length > 0)) {
            Assert.assertNotNull(attrList);
            Assert.assertEquals(attrs.length, attrList.size());
            for (int attrIndex = 0; attrIndex < attrs.length; attrIndex++) {
                Attr attr = attrList.get(attrIndex);
                Assert.assertNotNull(attr);

                AttrTest attrTest = attrs[attrIndex];
                Assert.assertEquals(attrTest.name, attr.getName());
                Assert.assertEquals(attrTest.value, attr.getValue());
            }
        }
    }

    private static AttrTest attr(String name, String value) {
        return new AttrTest(name, value);
    }

    private static class AttrTest {
        private final String name;
        private final String value;

        private AttrTest(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}