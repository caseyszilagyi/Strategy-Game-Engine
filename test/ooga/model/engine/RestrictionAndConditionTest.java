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
import ooga.model.engine.Engine;
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
    modelController.setViewController(viewController);
    modelController.setGameType("chess");
    gameBoard = modelController.getEngine().getBoard();
    pieceCreator = new PieceCreator("chess", viewController, gameBoard);
    printBoard();
  }

  @Test
  void testPawnFirstMoveRestriction(){
    print("testPawnFirstMoveRestriction");
    actOnCoordinates(0,1);
    testActualExpectedCoordinates("0:2 0:3", viewController.getAllPossibleMoves());
    actOnCoordinates(0,2);
    printBoard();
    actOnCoordinates(0,2);
    testActualExpectedCoordinates("0:3 0:4", viewController.getAllPossibleMoves());
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
  private boolean testActualExpectedCoordinates(String expected, Set<Coordinate> actual) {
    return testExpectedCoordinatesList(makeManyCoordinateList(expected), actual);
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