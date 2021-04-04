package ooga.model.game_initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

public class PieceCreatorTesting {

  private Creator pieceCreator = new PieceCreator("Chess");
  private Map<String, List<Node>> rootNodeMap;
  private Map<String, List<Node>> subNodeMap;
  private Map<String, String> attributeMap;

  @BeforeEach
  private void SetUp(){
  }


  /**
   * Tests the creator superclass methods
   */
  @Test
  void testGeneralCreator(){
    rootNodeMap = makeRootNodeMap("knight");
    subNodeMap = makeSubNodeMap(rootNodeMap.get("moves").get(0));
    attributeMap = makeAttributeMap(subNodeMap.get("FiniteJump").get(0));
    checkAttributeMapping("changeX", "1");
    checkAttributeMapping("changeY", "2");
  }

  /**
   * Tests faulty file information
   */
  @Test
  void testWrongGameType(){
    try{
      makeRootNodeMap("checkerPiece");
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "InvalidFileType");
    }
  }




  // asserts that the attribute map has the correct mapping
  private void checkAttributeMapping(String key, String value){
    assertEquals(value, attributeMap.get(key));
  }

  // Makes the attribute map
  private Map<String, String> makeAttributeMap(Node node){
    return pieceCreator.makeAttributeMap(node);
  }

  // Makes the root node map
  private Map<String, List<Node>> makeSubNodeMap(Node node){
    return pieceCreator.makeSubNodeMap(node);
  }

  // Makes the root node map
  private Map<String, List<Node>> makeRootNodeMap(String fileName){
    return pieceCreator.makeRootNodeMap(fileName);
  }

}
