package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.DummyViewController;
import ooga.controller.ModelController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.player.Player;
import ooga.model.engine.running.Engine;
import ooga.model.initialization.pieces.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunningCheckerTest {

  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;
  Player player1 = new Player("user");
  Player player2 = new Player("opponent");
  PieceCreator pieceCreator;


  @BeforeEach
  private void SetUp() {
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("checkers");
    gameEngine = modelController.getEngine();
    gameBoard = gameEngine.getBoard();
    modelController.setPlayers("user", "opponent");
    pieceCreator = new PieceCreator("checkers", gameBoard);
    printBoard();
  }


  @Test
  void testBasicNonJumpTurnChanging(){
    actOnCoordinates(0,5);
    actOnCoordinates(1,4);
    printBoard();
    isCoordinateEmpty(0,5);
    isRegularCheckerAtCoordinates(1,4);
    actOnCoordinates(1,2);
    actOnCoordinates(2,3);
    printBoard();
    isCoordinateEmpty(1,2);
    isRegularCheckerAtCoordinates(2,3);
  }

  @Test
  void testDoubleJump(){
    modelController.setBoardState("checkerDoubleJumpAndPromotion");
    gameBoard = gameEngine.getBoard();
    printBoard();
    actOnCoordinates(3,4);
    actOnCoordinates(1,2);
    actOnCoordinates(3,0);
    isCoordinateEmpty(2,1);
    isCoordinateEmpty(2,3);
    printBoard();
    isKingCheckerAtCoordinates(3,0);
    printBoard();
  }

  @Test
  void testPromotionAndKingMovement(){
    testDoubleJump();
    actOnCoordinates(6,6);
    actOnCoordinates(5,7);
    isKingCheckerAtCoordinates(5,7);
    actOnCoordinates(3,0);
    testActualExpectedCoordinates("2:1 4:1");
    actOnCoordinates(2,1);
    isCoordinateEmpty(3,0);
    isKingCheckerAtCoordinates(2,1);
    actOnCoordinates(5,7);
    testActualExpectedCoordinates("6:6 4:6");
    actOnCoordinates(6,6);
    isKingCheckerAtCoordinates(6,6);
    isCoordinateEmpty(5,7);
    printBoard();
  }




  private void isCoordinateEmpty(int x, int y){
    assertFalse(gameBoard.isPieceAtCoordinate(x,y));
  }

  private void isRegularCheckerAtCoordinates(int x, int y){
    assertTrue(gameBoard.isPieceAtCoordinate(x,y));
    assertEquals("normalChecker", gameBoard.getPieceAtCoordinate(x, y).getPieceName());
  }

  private void isKingCheckerAtCoordinates(int x, int y){
    assertTrue(gameBoard.isPieceAtCoordinate(x,y));
    assertEquals("kingChecker", gameBoard.getPieceAtCoordinate(x, y).getPieceName());
  }

  private void print(String toPrint){
    System.out.println(toPrint);
  }

  // piece creator methods
  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1, "Casey");
  }

  private GamePiece makeEnemyPiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), -1, "NotCasey");
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

