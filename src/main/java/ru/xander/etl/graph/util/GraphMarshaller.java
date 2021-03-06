package ru.xander.etl.graph.util;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import ru.xander.etl.graph.graph.xml.Graph;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Alexander Shakhov
 */
public class GraphMarshaller {

    public static String marshal(Graph graph) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Graph.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CDataEscapeHandler());
            StringWriter writer = new StringWriter();
            marshaller.marshal(graph, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Cannot marshal graph: " + e.getMessage(), e);
        }
    }

    public static Graph unmarshal(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Graph.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            Source source = new StreamSource(reader);
            return unmarshaller.unmarshal(source, Graph.class).getValue();
        } catch (Exception e) {
            throw new RuntimeException("Cannot unmarshal graph from xml: " + e.getMessage(), e);
        }
    }

    private static class CDataEscapeHandler implements CharacterEscapeHandler {
        @Override
        public void escape(char[] chars, int start, int length, boolean isAttVal, Writer writer) throws IOException {
            writer.write(chars, start, length);
        }
    }
}
