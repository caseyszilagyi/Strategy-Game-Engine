package ooga.model.game_initialization.file_parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

public class XMLNestParsingTest {

  private String filePath = "test/ooga/model/game_initialization/file_parsing/XMLTestFiles/";

  XMLParser xmlParser = new XMLParser();
  Map<String, List<Node>> rootNodeMap;
  Map<String, List<Node>> subNodeMap;
  Map<String, List<String>> attributeMap;

  @BeforeEach
  private void SetUp(){
  }

  @Test
  void TripleNestTest(){
    rootNodeMap = makeRootNodeMap("NestedElements.xml", "piece", "Chess");
    subNodeMap = makeSubNodeMap(rootNodeMap.get("test").get(0));
    attributeMap = makeAttributeMap(subNodeMap.get("nest1").get(0));
    checkAttributeMapping("test1", "Hello");
  }



  // asserts that the attribute map has the correct mapping
  private void checkAttributeMapping(String key, String value){
    assertEquals(value, attributeMap.get(key).get(0));
  }

  // gets the attribute map when given a node
  private Map<String, List<String>> makeAttributeMap(Node node){
    return xmlParser.makeAttributeMap(node);
  }

  // gets a node map when given a node
  private Map<String, List<Node>> makeSubNodeMap(Node node){
    return xmlParser.makeNodeMap(node);
  }

  //sets up the root node map
  private Map<String, List<Node>> makeRootNodeMap(String fileName, String fileType, String gameName){
    File testFile = makeFile(fileName);
    return xmlParser.makeRootNodeMap(testFile, fileType, gameName);
  }

  //makes a file from the XMLTestFiles folder
  private File makeFile(String name){
    return new File(filePath+name);
  }


}
