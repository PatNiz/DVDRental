package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

public interface XmlConvertible {
    Element toXml(Document doc, long i) throws ParserConfigurationException;
}
