package ru.xander.etl.graph.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Alexander Shakhov
 */
public class AdapterCData extends XmlAdapter<String, String> {
    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
}
