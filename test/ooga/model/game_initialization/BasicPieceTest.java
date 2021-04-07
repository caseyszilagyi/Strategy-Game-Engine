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
public class BasicPieceTest {

  private PieceCreator pieceCreator = new PieceCreator("Chess");
  private Map<String, List<Node>> rootNodeMap;
  private Map<String, List<Node>> subNodeMap;
  private Map<String, String> attributeMap;

  GamePiece[][] dummyBoard = new GamePiece[8][8];
  Set<Coordinate> allLegalMoves;

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
   * Testing the legal move coordinates for a bishop on an empty board
   */
  @Test
  void TestEmptyBoardBishopMovement(){
    makeEmptyBoard();
    GamePiece bishop = makePiece("bishop", 6, 5);
    bishop.setPieceTeam("Casey");
    bishop.setDummyBoard(dummyBoard);
    allLegalMoves = bishop.getAllLegalMoves();
    String expected = "7:6 5:4 4:3 3:2 2:1 1:0 5:6 4:7 7:4";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a bishop on a board with friendly/enemy pieces
   */
  @Test
  void TestBishopWithFriendlyAndOpponentPieces(){
    makeEmptyBoard();
    GamePiece bishop = makePiece("bishop", 4, 4);
    bishop.setPieceTeam("Casey");
    dummyBoard[6][6] = makeDummyGamePiece("notCasey");
    dummyBoard[2][2] = makeDummyGamePiece("notCasey");
    dummyBoard[1][1] = makeDummyGamePiece("notCasey");
    dummyBoard[6][2] = makeDummyGamePiece("Casey");
    dummyBoard[2][6] = makeDummyGamePiece("Casey");
    bishop.setDummyBoard(dummyBoard);
    allLegalMoves = bishop.getAllLegalMoves();
    String expected = "2:2 3:3 5:5 6:6 5:3 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a rook on an empty board
   */
  @Test
  void TestEmptyBoardRookMovement(){
    makeEmptyBoard();
    GamePiece rook = makePiece("rook", 6, 5);
    rook.setPieceTeam("Casey");
    rook.setDummyBoard(dummyBoard);
    allLegalMoves = rook.getAllLegalMoves();
    String expected = "6:6 6:7 6:4 6:3 6:2 6:1 6:0 0:5 1:5 2:5 3:5 4:5 5:5 7:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a rook on a board with friendly/enemy pieces
   */
  @Test
  void TestRookWithFriendlyAndOpponentPieces(){
    makeEmptyBoard();
    GamePiece rook = makePiece("rook", 4, 4);
    rook.setPieceTeam("Casey");
    dummyBoard[4][6] = makeDummyGamePiece("notCasey");
    dummyBoard[4][2] = makeDummyGamePiece("notCasey");
    dummyBoard[6][4] = makeDummyGamePiece("Casey");
    dummyBoard[2][4] = makeDummyGamePiece("Casey");
    rook.setDummyBoard(dummyBoard);
    allLegalMoves = rook.getAllLegalMoves();
    String expected = "2:4 3:4 5:4 6:4 4:5 4:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a queen on an empty board
   */
  @Test
  void TestEmptyBoardQueenMovement(){
    makeEmptyBoard();
    GamePiece queen = makePiece("queen", 6, 5);
    queen.setPieceTeam("Casey");
    queen.setDummyBoard(dummyBoard);
    allLegalMoves = queen.getAllLegalMoves();
    String expected = "6:6 6:7 6:4 6:3 6:2 6:1 6:0 0:5 1:5 2:5 3:5 4:5 5:5 7:5 7:6 5:4 4:3 3:2 2:1 1:0 5:6 4:7 7:4";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a bishop on a board with friendly/enemy pieces
   */
  @Test
  void TestQueenWithFriendlyAndOpponentPieces(){
    makeEmptyBoard();
    GamePiece queen = makePiece("queen", 4, 4);
    queen.setPieceTeam("Casey");
    dummyBoard[4][6] = makeDummyGamePiece("notCasey");
    dummyBoard[4][2] = makeDummyGamePiece("notCasey");
    dummyBoard[6][4] = makeDummyGamePiece("Casey");
    dummyBoard[2][4] = makeDummyGamePiece("Casey");
    dummyBoard[6][6] = makeDummyGamePiece("notCasey");
    dummyBoard[2][2] = makeDummyGamePiece("notCasey");
    dummyBoard[6][2] = makeDummyGamePiece("Casey");
    dummyBoard[2][6] = makeDummyGamePiece("Casey");
    queen.setDummyBoard(dummyBoard);
    allLegalMoves = queen.getAllLegalMoves();
    String expected = "2:4 3:4 5:4 6:4 4:5 4:3 2:2 3:3 5:5 6:6 5:3 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a king on an empty board
   */
  @Test
  void TestEmptyBoardKingMovement(){
    makeEmptyBoard();
    GamePiece king = makePiece("king", 4, 4);
    king.setPieceTeam("Casey");
    king.setDummyBoard(dummyBoard);
    allLegalMoves = king.getAllLegalMoves();
    String expected = "4:5 4:3 5:3 5:4 5:5 3:3 3:4 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a king on an empty board
   */
  @Test
  void TestKingWithFriendlyAndOpponentPieces(){
    makeEmptyBoard();
    GamePiece king = makePiece("king", 4, 4);
    king.setPieceTeam("Casey");
    king.setDummyBoard(dummyBoard);
    dummyBoard[4][5] = makeDummyGamePiece("Casey");
    dummyBoard[5][4] = makeDummyGamePiece("notCasey");
    allLegalMoves = king.getAllLegalMoves();
    String expected = "4:5 4:3 5:3 5:5 3:3 3:4 3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Test edge board king movement
   */
  @Test
  void TestEdgeBoardKingMovement(){
    makeEmptyBoard();
    GamePiece king = makePiece("king", 7, 4);
    king.setPieceTeam("Casey");
    king.setDummyBoard(dummyBoard);
    allLegalMoves = king.getAllLegalMoves();
    String expected = "7:5 7:3 6:3 6:4 6:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a pawn on an empty board. Note that moving
   * forward 2 is always allowed, where as it should really have special conditions.
   */
  @Test
  void TestEmptyBoardPawnMovement(){
    makeEmptyBoard();
    GamePiece pawn = makePiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    pawn.setDummyBoard(dummyBoard);
    allLegalMoves = pawn.getAllLegalMoves();
    String expected = "4:5 4:6";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Tests that a pawn can take enemy pieces on the diagonal, and also can't slide through
   * opponent/friendly pieces
   */
  @Test
  void TestPawnTakeAndSlideMovement(){
    makeEmptyBoard();
    GamePiece pawn = makePiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    pawn.setDummyBoard(dummyBoard);
    dummyBoard[5][5] = makeDummyGamePiece("Casey");
    dummyBoard[5][3] = makeDummyGamePiece("notCasey");
    dummyBoard[5][4] = makeDummyGamePiece("Casey");
    allLegalMoves = pawn.getAllLegalMoves();
    String expected = "3:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Tests a pawn moving in the opposite direction
   */
  @Test
  void TestPawnReverseMovement(){
    makeEmptyBoard();
    GamePiece pawn = makeEnemyPiece("pawn", 4, 4);
    pawn.setPieceTeam("Casey");
    pawn.setDummyBoard(dummyBoard);
    dummyBoard[3][5] = makeDummyGamePiece("Casey");
    dummyBoard[3][3] = makeDummyGamePiece("notCasey");
    dummyBoard[3][4] = makeDummyGamePiece("Casey");
    allLegalMoves = pawn.getAllLegalMoves();
    String expected = "3:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }



  // piece creator methods
  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1);
  }

  private GamePiece makeEnemyPiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), -1);
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
          dummyBoard[row][col] = null;
        }
        else{
          dummyBoard[row][col] = makeDummyGamePiece("Enemy");
        }
      }
    }
  }

  private GamePiece makeDummyGamePiece(String teamName){
    GamePiece piece = new GamePiece(makeCoordinates(0,0), "dummy");
    piece.setPieceTeam(teamName);
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
