package org.example.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XmlUtils {

    private static XStream newInstance() {
        XStream xstream = new XStream(new StaxDriver(new XmlFriendlyNameCoder("_-", "_")));
        XStream.setupDefaultSecurity(xstream);
        xstream.ignoreUnknownElements();
        xstream.autodetectAnnotations(true);
        xstream.aliasSystemAttribute(null, "class");
        xstream.allowTypesByWildcard(new String[] { "org.example.**" });
        return xstream;
    }

    public static <T> T parseObject(String xml, Class<T> clazz) {
        XStream xStream = newInstance();
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xml);
    }

    public static <T> T toObject(String text, Class<T> clazz) {
        return parseObject(text, clazz);
    }

    public static String toXml(Object object) {
        XStream xStream = newInstance();
        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }

}
