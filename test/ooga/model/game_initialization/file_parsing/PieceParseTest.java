package ooga.model.game_initialization.file_parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

public class PieceParseTest {

  private String filePath = "test/ooga/model/game_initialization/file_parsing/XMLTestFiles/";

  XMLParser xmlParser = new XMLParser("piece", "Chess");


  @BeforeEach
  private void SetUp(){

  }

  @Test
  void BasicTest(){
    File testFile = makeFile("NestedElements.xml");
    Map<String, Node> map = xmlParser.getElementMap(testFile);
    for(String s: map.keySet()){
      System.out.println(map.get(s).getTextContent());
    }
  }

  //makes a file
  private File makeFile(String name){
    return new File(filePath+name);
  }


}
