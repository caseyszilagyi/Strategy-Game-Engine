package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.DummyViewController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.engine.running.Engine;
import ooga.model.initialization.pieces.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Methods to test the engine and it's interactions with the viewController
 */
public class RestrictionAndConditionTest {

  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;
  PieceCreator pieceCreator;

  @BeforeEach
  private void SetUp() {
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("chess");
    gameEngine= modelController.getEngine();
    gameBoard = gameEngine.getBoard();
    gameEngine.setIfTurnRules(true);
    pieceCreator = new PieceCreator("chess", viewController, gameBoard);
    printBoard();
  }

  @Test
  void testPawnFirstMoveRestriction(){
    print("testPawnFirstMoveRestriction");
    actOnCoordinates(0,1);
    testActualExpectedCoordinates("0:2 0:3");
    actOnCoordinates(0,2);
    printBoard();
    actOnCoordinates(0,2);
    testActualExpectedCoordinates("0:3");
    actOnCoordinates(0,3);
    actOnCoordinates(1,1);
    testActualExpectedCoordinates("1:2 1:3");
    actOnCoordinates(4,4);
    actOnCoordinates(0,6);
    testActualExpectedCoordinates("0:5 0:4");
  }

  @Test
  void testPawnPromotionCondition(){
    print("TestPawnPromotionCondition");
    printBoard();
    actOnCoordinates(2,6);
    actOnCoordinates(2,5);
    actOnCoordinates(2,5);
    actOnCoordinates(2,4);
    actOnCoordinates(2,4);
    actOnCoordinates(2,3);
    actOnCoordinates(2,3);
    actOnCoordinates(2,2);
    printBoard();
    actOnCoordinates(2,2);
    actOnCoordinates(3,1);
    printBoard();
    assertNull(viewController.getPieceChangeOptions());
    actOnCoordinates(3,1);
    actOnCoordinates(4,0);
    printBoard();
    Iterator<String> pieceIter = viewController.getPieceChangeOptions();
    assertEquals("queen", pieceIter.next());
  }

  @Test
  void testKingSideCastle(){
    //move bishop out of way
    actOnCoordinates(6, 6);
    actOnCoordinates(6,5);
    actOnCoordinates(5,7);
    actOnCoordinates(7,5);
    //test king click
    actOnCoordinates(4, 7);
    testActualExpectedCoordinates("5:7");
    actOnCoordinates(4,7);
    //move knight
    actOnCoordinates(6, 7);
    actOnCoordinates(5, 5);
    printBoard();
    //test king click
    actOnCoordinates(4, 7);
    testActualExpectedCoordinates("5:7 6:7");
    printBoard();
    //actual castle
    actOnCoordinates(6,7);
    printBoard();
    assertEquals(gameBoard.getPieceAtCoordinate(makeCoordinates(5,7)).getPieceName(), "rook");
  }

  @Test
  void testUnblockCheck(){
    modelController.setBoardState("unblockCheck");
    gameBoard = gameEngine.getBoard();
    printBoard();
    actOnCoordinates(4, 6);
    testActualExpectedCoordinates("");
  }

  @Test
  void testTakePieceCausingCheck(){
    modelController.setBoardState("takePieceCheck");
    gameBoard = gameEngine.getBoard();
    printBoard();
    actOnCoordinates(4, 6);
    testActualExpectedCoordinates("5:5");
    actOnCoordinates(0,0);
    actOnCoordinates(6, 6);
    testActualExpectedCoordinates("5:5");
    actOnCoordinates(0,0);
    actOnCoordinates(7,6);
    testActualExpectedCoordinates("");
    actOnCoordinates(0,0);
    actOnCoordinates(4,7);
    testActualExpectedCoordinates("3:7 5:7 5:6");
  }

  @Test
  void testBlockCheck(){
    modelController.setBoardState("blockCheck");
    gameBoard = gameEngine.getBoard();
    printBoard();
    actOnCoordinates(2,3);
    testActualExpectedCoordinates("4:5");
    actOnCoordinates(0,0);
    actOnCoordinates(0,5);
    testActualExpectedCoordinates("4:5");
  }

  @Test
  void fixBrokenTest(){
    modelController.setBoardState("testing");
    gameBoard = gameEngine.getBoard();
    printBoard();
    actOnCoordinates(4,2);
    testActualExpectedCoordinates("3:3");
  }

  @Test
  void testOppositeSideCastle(){
    //moving pieces
    actOnCoordinates(6,0);
    actOnCoordinates(5,2);
    actOnCoordinates(6,1);
    actOnCoordinates(6,3);
    actOnCoordinates(5,0);
    actOnCoordinates(7,2);
    printBoard();
    actOnCoordinates(4,0);
    testActualExpectedCoordinates("5:0 6:0");
  }

  @Test
  void setUpQueenSideCastle(){
    actOnCoordinates(1, 6);
    actOnCoordinates(1,4);
    actOnCoordinates(2,6);
    actOnCoordinates(2,4);
    actOnCoordinates(1,7);
    actOnCoordinates(0,5);
    actOnCoordinates(2,7);
    actOnCoordinates(1,6);
    actOnCoordinates(3,7);
    actOnCoordinates(2,6);
    printBoard();
  }

  @Test
  void testValidQueenSideCastle(){
    setUpQueenSideCastle();
    actOnCoordinates(4,7);
    testActualExpectedCoordinates("3:7 2:7");
    actOnCoordinates(2, 7);
    isPieceAtCoordinates("rook", 3,7);
    isPieceAtCoordinates("king", 2, 7);
    printBoard();
  }

  @Test
  void testInvalidQueenSideCastle(){
    setUpQueenSideCastle();
    actOnCoordinates(4,7);
    actOnCoordinates(3,7);
    actOnCoordinates(3,7);
    actOnCoordinates(4,7);
    actOnCoordinates(4,7);
    testActualExpectedCoordinates("3:7");
    actOnCoordinates(3,7);
    isPieceAtCoordinates("rook", 0,7);
    isPieceAtCoordinates("king", 3, 7);
    printBoard();
  }

  @Test
  void testCastleThroughCheck(){
    modelController.setBoardState("castleThroughCheck");
    gameBoard=gameEngine.getBoard();
    printBoard();
    actOnCoordinates(4,7);
    testActualExpectedCoordinates("4:6");
  }

  @Test
  void testMoveHistoryAndUndoBasicMove(){
    actOnCoordinates(1,0);
    actOnCoordinates(2,2);
    printBoard();
    gameBoard.undoTurn();
    printBoard();
    assertEquals("knight", gameBoard.getPieceAtCoordinate(1,0).getPieceName());
    assertNull(gameBoard.getPieceAtCoordinate(2,2));
  }

  @Test
  void testUndoTakeMove(){
    actOnCoordinates(1,1);
    actOnCoordinates(1,2);
    actOnCoordinates(2,0);
    actOnCoordinates(0,2);
    actOnCoordinates(0,2);
    actOnCoordinates(4,6);
    printBoard();
    modelController.undoTurn();
    printBoard();
    assertEquals("bishop", gameBoard.getPieceAtCoordinate(0,2).getPieceName());
    assertEquals("pawn", gameBoard.getPieceAtCoordinate(4,6).getPieceName());
  }


  private void isPieceAtCoordinates(String pieceName, int x, int y){
    assertEquals(gameBoard.getPieceAtCoordinate(x, y).getPieceName(), pieceName);
  }

  private void print(String toPrint){
    System.out.println(toPrint);
  }

  // piece creator methods
  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1, viewController, "Casey");
  }

  private GamePiece makeEnemyPiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), -1, viewController, "NotCasey");
  }

  private void checkSetBoardSpaceCall(int x, int y, String identifier, String team) {
    assertEquals(identifier, viewController.getIdentifier(x, y));
    assertEquals(team, viewController.getTeamName(x, y));
  }

  private void checkMoveCall(int startX, int startY, int endX, int endY) {
    assertEquals(startX, viewController.getStartX());
    assertEquals(startY, viewController.getStartY());
    assertEquals(endX, viewController.getEndX());
    assertEquals(endY, viewController.getEndY());
  }

  private void checkRemoveCall(int x, int y) {
    assertEquals(x, viewController.getRemY());
    assertEquals(y, viewController.getRemX());
  }

  private Set<Coordinate> getMoves() {
    return viewController.getAllPossibleMoves();
  }

  private void printBoard() {
    gameBoard.printBoard();
  }

  private void actOnCoordinates(int x, int y) {
    modelController.actOnCoordinates(x, y);
  }

  // Compares a string of expected coordinates to a list of actual coordinates
  private void testActualExpectedCoordinates(String expected) {
    assertTrue(testExpectedCoordinatesList(makeManyCoordinateList(expected), viewController.getAllPossibleMoves()));
  }


  // Checks to see if the map of expected coordinates and the list of actual coordinates match
  private boolean testExpectedCoordinatesList(Set<Coordinate> expected, Set<Coordinate> actual) {
    for (Coordinate currentCoordinate : actual) {
      if (expected.contains(currentCoordinate)) {
        expected.remove(currentCoordinate);
      } else {
        return false;
      }
    }
    if (expected.isEmpty()) {
      return true;
    }
    return false;
  }

  // Used to make coordinate lists. Format is a string of "1:2 3:4", where x=1, y=2, and x=3 y=4
  private Set<Coordinate> makeManyCoordinateList(String coordinates) {
    Set<Coordinate> allCoords = new HashSet<>();
    if (coordinates.equals("")) {
      return allCoords;
    }
    String[] splitCoords = coordinates.split(" ");
    for (String s : splitCoords) {
      String[] coordPair = s.split(":");
      Coordinate newCoord = makeCoordinates(Integer.parseInt(coordPair[0]),
          Integer.parseInt(coordPair[1]));
      allCoords.add(newCoord);
    }
    return allCoords;
  }

  // makes a coordinate object
  private Coordinate makeCoordinates(int x, int y) {
    return new Coordinate(x, y);
  }

}