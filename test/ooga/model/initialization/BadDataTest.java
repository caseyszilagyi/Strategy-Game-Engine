package ooga.model.initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.DummyViewController;
import ooga.exceptions.ClassLoaderException;
import ooga.exceptions.GameRunningException;
import ooga.exceptions.XMLParseException;
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
public class BadDataTest {

  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;
  PieceCreator pieceCreator;
  BoardCreator boardCreator;

  @BeforeEach
  private void SetUp() {
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("chess");
    gameEngine= modelController.getEngine();
    gameBoard = gameEngine.getBoard();
    gameEngine.setIfNoTurnRules(true);
    pieceCreator = new PieceCreator("chess", gameBoard);
    boardCreator = new BoardCreator("chess", viewController);
    printBoard();
  }

  @Test
  void BadPiece(){
    assertThrows(ClassLoaderException.class, () -> makePiece("badDataNonExistentPieceMovement"), "PieceMovementClassNotFound");
    assertThrows(ClassLoaderException.class, () -> makePiece("badDataNonExistentPieceMovement"), "PieceMovementInvocation");
    assertThrows(ClassLoaderException.class, () -> makePiece("badDataAbstract"), "PieceMovementInstantiation");
  }

  @Test
  void BadRestrictionAndCondition(){
    assertThrows(ClassLoaderException.class, () -> makePiece("badDataWrongCondition"), "ConditionClassNotFound");
    assertThrows(ClassLoaderException.class, () -> makePiece("badDataWrongRestriction"), "RestrictionClassNotFound");
  }

  @Test
  void BadBoard(){
    assertThrows(XMLParseException.class, () -> makeBoard("badDataNoRowTag"), "MissingRowColumnTag");
    assertThrows(XMLParseException.class, () -> makeBoard("badDataNoOpponent"), "InvalidBoardFile");
    assertThrows(XMLParseException.class, () -> makeBoard("badDataNonExistentPiece"), "NoSuchPieceFile");
  }


  private void makeBoard(String fileName){
    boardCreator.initializeMaps(fileName);
    boardCreator.makeBoard();
  }
  private void makePiece(String pieceFile){
    makePiece(pieceFile, 0, 0);
  }

  private void isCoordinateEmpty(int x, int y){
    assertFalse(gameBoard.isPieceAtCoordinate(x,y));
  }

  private void isPieceAtCoordinates(String pieceName, int x, int y){
    assertEquals(gameBoard.getPieceAtCoordinate(x, y).getPieceName(), pieceName);
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