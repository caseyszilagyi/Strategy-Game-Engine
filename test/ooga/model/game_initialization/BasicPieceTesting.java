package ooga.model.game_initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

/**
 * Testing class for piece creation, reflection for various piece components, and basic
 * testing of getAllPossibleMoves
 */
public class BasicPieceTesting {

  private PieceCreator pieceCreator = new PieceCreator("Chess");
  private Map<String, List<Node>> rootNodeMap;
  private Map<String, List<Node>> subNodeMap;
  private Map<String, String> attributeMap;

  GamePiece[][] dummyBoard = new GamePiece[8][8];
  List<Coordinate> allLegalMoves;

  @BeforeEach
  private void SetUp(){
  }


  /**
   * Tests the creator superclass methods, makes sure that they are functioning properly
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

  /**
   * Testing the getLegalMoves for a night in the middle of the board
   */
  @Test
  void testBasicKnightGetMoves(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 4, 4);
    knight.setPieceTeam("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "5:6 5:2: 6:5 6:3 3:6 3:2 2:5 2:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the getLegalMoves for a knight on the edge of the board
   */
  @Test
  void testKnightEdgeGetMoves(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 7, 4);
    knight.setPieceTeam("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "5:5 5:3 6:6 6:2";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the getLegalMoves for a knight in the corner of the board
   */
  @Test
  void testKnightCornerGetMoves(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 7, 7);
    knight.setPieceTeam("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "6:5 5:6";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing whether the knight can recognize that it can land on other pieces, and can't
   * land on friendly pieces
   *
   * Should be able to take piece at 6:5 but not 5:6
   */
  @Test
  void testKnightTakeMovement(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 7, 7);
    knight.setPieceTeam("Casey");
    dummyBoard[5][6] = makeDummyGamePiece("notCasey");
    dummyBoard[6][5] = makeDummyGamePiece("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "6:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }


  /**
   * Testing the hashcode and equals methods for coordinates to see if comparison in set
   * objects works properly
   */
  @Test
  void testCoordinateHashCodeAndEquals(){
    List<Coordinate> testSet = new ArrayList<>();
    testSet.add(makeCoordinates(3, 4));
    testSet.add(makeCoordinates(1,2));
    assertTrue(testActualExpectedCoordinates("1:2 3:4", testSet));
  }


  // piece creator methods
  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1);
  }


  // dummy board methods

  private void makeEmptyBoard(){
    dummyBoardCreator("................................................................");
  }

  // Makes a dummy board to use for testing
  private void dummyBoardCreator(String boardConfig){
    char[] board = boardConfig.toCharArray();
    for(int row = 0; row<8; row++){
      for(int col = 0; col<8; col++){
        if(board[8*row+col] - '.' == 0){
          dummyBoard[col][row] = null;
        }
        else{
          dummyBoard[col][row] = makeDummyGamePiece("Enemy");
        }
      }
    }
  }

  private GamePiece makeDummyGamePiece(String teamName){
    GamePiece piece = new GamePiece(makeCoordinates(0,0));
    piece.setPieceTeam(teamName);
    return piece;
  }

  // Methods used for testing coordinates and piece movement

  // Compares a string of expected coordinates to a list of actual coordinates
  private boolean testActualExpectedCoordinates(String expected, List<Coordinate> actual){
    return testExpectedCoordinatesList(makeManyCoordinateList(expected), actual);
  }


  // Checks to see if the map of expected coordinates and the list of actual coordinates match
  private boolean testExpectedCoordinatesList(Set<Coordinate> expected, List<Coordinate> actual){
    for(Coordinate currentCoordinate: actual){
      if(expected.contains(currentCoordinate)){
        expected.remove(currentCoordinate);
      }
      else{
        return false;
      }
    }
    if(expected.isEmpty()){
      return true;
    }
    return false;
  }

  // Used to make coordinate lists. Format is a string of "1:2 3:4", where x=1, y=2, and x=3 y=4
  private Set<Coordinate> makeManyCoordinateList(String coordinates){
    Set<Coordinate> allCoords = new HashSet<>();
    String[] splitCoords = coordinates.split(" ");
    for(String s: splitCoords){
      String[] coordPair = s.split(":");
      Coordinate newCoord = makeCoordinates(Integer.parseInt(coordPair[0]), Integer.parseInt(coordPair[1]));
      allCoords.add(newCoord);
    }
    return allCoords;
  }

  // makes a coordinate object
  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }


  // XML testing/useful methods

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
