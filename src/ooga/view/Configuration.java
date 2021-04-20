package ooga.view;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Configuration {
  private final DocumentBuilder DOCUMENT_BUILDER;
  private Map<String, String> opponentPositionMap;
  private Map<String, String> userPositionMap;
  private Map<String, Integer> paramsMap;

  public Configuration(String fileString) throws Exception {
    File dataFile = new File(fileString);
    DOCUMENT_BUILDER = getDocumentBuilder();
    //Element root = getRootElement(dataFile);
    paramsMap = makeParamsMap(dataFile);
    opponentPositionMap = getPositions(dataFile, "opponent");
    userPositionMap = getPositions(dataFile, "user");
  }

  public Map<String, String> getOpponentPositionMap() {
    return new HashMap<String, String>(opponentPositionMap);
  }

  private Map<String, Integer> makeParamsMap(File dataFile) throws XMLException {
    try {
      Map<String, Integer> returned = new HashMap<>();
      NodeList nList = DOCUMENT_BUILDER.parse(dataFile).getElementsByTagName("params");
      Node node = nList.item(0);
      NodeList list = node.getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
        Node n = list.item(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          returned.put(n.getNodeName(), Integer.parseInt(n.getTextContent()));
        }
      }
      return returned;
    } catch (Exception e) {
      throw new XMLException("Missing Params Section or Non-String Values");
    }
  }

  private Map<String, String> getPositions(File dataFile, String player) throws XMLException {
    try {
      Map<String, String> returned = new HashMap<>();
      NodeList nList = DOCUMENT_BUILDER.parse(dataFile).getElementsByTagName(player);
      Node node = nList.item(0);
      NodeList list = node.getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
        Node n = list.item(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          returned.put(n.getNodeName(), n.getTextContent());
        }
      }
      return returned;
    } catch (Exception e) {
      throw new XMLException("Missing Parameters Section or Non-String Values");
    }
  }

  private DocumentBuilder getDocumentBuilder() throws XMLException {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (Exception e) {
      throw new XMLException("DocumentBuilderException");
    }
  }

  private Element getRootElement(File xmlFile) throws XMLException {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (Exception e) {
      throw new XMLException("Not XML File or Empty XML");
    }
  }

  public Map<String, String> getUserPositionMap() {
    return new HashMap<String, String>(userPositionMap);
  }
}
