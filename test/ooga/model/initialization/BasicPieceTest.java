package ooga.model.initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.controller.DummyViewController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.initialization.pieces.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

/**
 * Testing class for piece creation, reflection for various piece components, and basic
 * testing of getAllPossibleMoves
 */
public class BasicPieceTest {

  private Map<String, List<Node>> rootNodeMap;
  private Map<String, List<Node>> subNodeMap;
  private Map<String, String> attributeMap;

  Set<Coordinate> allLegalMoves;
  DummyViewController dummyViewController = new DummyViewController();
  private GameBoard gameBoard= new GameBoard(8,8);
  private PieceCreator pieceCreator = new PieceCreator("chess", dummyViewController, gameBoard);

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
      makeRootNodeMap("normalChecker");
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "InvalidFileType");
    }
  }

  /**
   * Testing the hashcode and equals methods for coordinates to see if comparison in set
   * objects works properly
   */
  @Test
  void testCoordinateHashCodeAndEquals(){
    Set<Coordinate> testSet = new HashSet<>();
    testSet.add(makeCoordinates(3, 4));
    testSet.add(makeCoordinates(1,2));
    assertTrue(testActualExpectedCoordinates("1:2 3:4", testSet));
  }

  /**
   * Testing the getLegalMoves for a night in the middle of the board
   */
  @Test
  void testBasicKnightGetMoves(){
    GamePiece knight = makePiece("knight", 4, 4);
    knight.setPieceTeam("Casey");
    knight.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "5:6 5:2: 6:5 6:3 3:6 3:2 2:5 2:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the getLegalMoves for a knight on the edge of the board
   */
  @Test
  void testKnightEdgeGetMoves(){
    GamePiece knight = makePiece("knight", 7, 4);
    knight.setPieceTeam("Casey");
    knight.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "5:5 5:3 6:6 6:2";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the getLegalMoves for a knight in the corner of the board
   */
  @Test
  void testKnightCornerGetMoves(){
    GamePiece knight = makePiece("knight", 7, 7);
    knight.setPieceTeam("Casey");
    knight.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
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
    GamePiece knight = makePiece("knight", 7, 7);
    knight.setPieceTeam("Casey");
    makeDummyGamePiece("notCasey", 6, 5);
    makeDummyGamePiece("Casey", 5, 6);
    knight.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "6:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a bishop on an empty board
   */
  @Test
  void TestEmptyBoardBishopMovement(){
    GamePiece bishop = makePiece("bishop", 6, 5);
    bishop.setPieceTeam("Casey");
    bishop.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "7:6 5:4 4:3 3:2 2:1 1:0 5:6 4:7 7:4";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a bishop on a board with friendly/enemy pieces
   */
  @Test
  void TestBishopWithFriendlyAndOpponentPieces(){
    GamePiece bishop = makePiece("bishop", 4, 4);
    bishop.setPieceTeam("Casey");
    makeDummyGamePiece("notCasey", 6, 6);
    makeDummyGamePiece("notCasey", 2, 2);
    makeDummyGamePiece("notCasey", 1, 1);
    makeDummyGamePiece("Casey",2 ,6);
    makeDummyGamePiece("Casey", 6, 2);
    bishop.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "2:2 3:3 5:5 6:6 5:3 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a rook on an empty board
   */
  @Test
  void TestEmptyBoardRookMovement(){
    GamePiece rook = makePiece("rook", 6, 5);
    rook.setPieceTeam("Casey");
    rook.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "6:6 6:7 6:4 6:3 6:2 6:1 6:0 0:5 1:5 2:5 3:5 4:5 5:5 7:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a rook on a board with friendly/enemy pieces
   */
  @Test
  void TestRookWithFriendlyAndOpponentPieces(){
    GamePiece rook = makePiece("rook", 4, 4);
    rook.setPieceTeam("Casey");
    makeDummyGamePiece("notCasey", 6, 4);
    makeDummyGamePiece("notCasey", 2, 4);
    makeDummyGamePiece("Casey", 4, 6);
    makeDummyGamePiece("Casey", 4, 2);
    rook.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "2:4 3:4 5:4 6:4 4:5 4:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a queen on an empty board
   */
  @Test
  void TestEmptyBoardQueenMovement(){
    GamePiece queen = makePiece("queen", 6, 5);
    queen.setPieceTeam("Casey");
    queen.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "6:6 6:7 6:4 6:3 6:2 6:1 6:0 0:5 1:5 2:5 3:5 4:5 5:5 7:5 7:6 5:4 4:3 3:2 2:1 1:0 5:6 4:7 7:4";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a bishop on a board with friendly/enemy pieces
   */
  @Test
  void TestQueenWithFriendlyAndOpponentPieces(){
    GamePiece queen = makePiece("queen", 4, 4);
    queen.setPieceTeam("Casey");
    makeDummyGamePiece("notCasey", 6, 4);
    makeDummyGamePiece("notCasey", 2, 4);
    makeDummyGamePiece("Casey", 4, 6);
    makeDummyGamePiece("Casey", 4, 2);
    makeDummyGamePiece("notCasey", 6, 6);
    makeDummyGamePiece("notCasey", 2, 2);
    makeDummyGamePiece("Casey", 2, 6);
    makeDummyGamePiece("Casey", 6, 2);
    queen.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "2:4 3:4 5:4 6:4 4:5 4:3 2:2 3:3 5:5 6:6 5:3 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a king on an empty board
   */
  @Test
  void TestEmptyBoardKingMovement(){
    GamePiece king = makePiece("king", 4, 4);
    king.setPieceTeam("Casey");
    king.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "4:5 4:3 5:3 5:4 5:5 3:3 3:4 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a king on an empty board
   */
  @Test
  void TestKingWithFriendlyAndOpponentPieces(){
    GamePiece king = makePiece("king", 4, 4);
    king.setPieceTeam("Casey");
    makeDummyGamePiece("Casey", 5, 4);
    makeDummyGamePiece("notCasey", 4, 5);
    king.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "4:5 4:3 5:3 5:5 3:3 3:4 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Test edge board king movement
   */
  @Test
  void TestEdgeBoardKingMovement(){
    GamePiece king = makePiece("king", 7, 4);
    king.setPieceTeam("Casey");
    king.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "7:5 7:3 6:3 6:4 6:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a pawn on an empty board. Note that moving
   * forward 2 is always allowed, where as it should really have special conditions.
   */
  @Test
  void TestEmptyBoardPawnMovement(){
    GamePiece pawn = makePiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    pawn.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "4:5 4:6";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Tests that a pawn can take enemy pieces on the diagonal, and also can't slide through
   * opponent/friendly pieces
   */
  @Test
  void TestPawnTakeAndSlideMovement(){
    GamePiece pawn = makePiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    makeDummyGamePiece("Casey", 5, 5);
    makeDummyGamePiece("notCasey", 3, 5);
    makeDummyGamePiece("Casey", 4, 5);
    pawn.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Tests a pawn moving in the opposite direction
   */
  @Test
  void TestPawnReverseMovement(){
    GamePiece pawn = makeEnemyPiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    makeDummyGamePiece("Casey", 5, 3);
    makeDummyGamePiece("notCasey", 3, 3);
    makeDummyGamePiece("Casey", 4, 3);
    pawn.determineAllLegalMoves();
    allLegalMoves = dummyViewController.getAllPossibleMoves();
    String expected = "3:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }



  // piece creator methods
  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1, dummyViewController, "Casey");
  }

  private GamePiece makeEnemyPiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), -1, dummyViewController, "NotCasey");
  }


  private GamePiece makeDummyGamePiece(String teamName, int x, int y){
    GamePiece piece = new GamePiece(makeCoordinates(x,y), "dummy", dummyViewController, gameBoard, " dummy");
    piece.setPieceTeam(teamName);
    gameBoard.addPiece(piece);
    return piece;
  }

  // Methods used for testing coordinates and piece movement

  // Compares a string of expected coordinates to a list of actual coordinates
  private boolean testActualExpectedCoordinates(String expected, Set<Coordinate> actual){
    return testExpectedCoordinatesList(makeManyCoordinateList(expected), actual);
  }


  // Checks to see if the map of expected coordinates and the list of actual coordinates match
  private boolean testExpectedCoordinatesList(Set<Coordinate> expected, Set<Coordinate> actual){
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
