package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.DummyViewController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.engine.running.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Methods to test the engine and it's interactions with the viewController
 */
public class BasicEngineTest {

  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;

  @BeforeEach
  private void SetUp(){
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("chess");
    gameBoard = modelController.getEngine().getBoard();
    gameEngine = modelController.getEngine();
    gameEngine.setIfNoTurnRules(true);
    modelController.setPlayers("user", "opponent");
    printBoard();
  }


  /**
   * Testing front end method calls for chess setup
   */
  @Test
  void testNormalChessMethodCalls(){
    System.out.println("testNormalChessMethodCalls");
    modelController.setGameType("chess");
    assertEquals(8, viewController.getWidth());
    assertEquals(8, viewController.getHeight());
    checkSetBoardSpaceCall(0,0,"rook","opponent");
    checkSetBoardSpaceCall(1,1,"pawn","opponent");
    checkSetBoardSpaceCall(2,7,"bishop","user");
  }

  /**
   * Tests the selection/unselection of pieces that shouldn't be moving
   */
  @Test
  void testSelectAndUnselect(){
    System.out.println("testSelectAndUnselect");
    //clicks rook with no moves
    actOnCoordinates(0,0);
    testActualExpectedCoordinates("", getMoves());
    //unselects piece
    actOnCoordinates(0,3);
    //clicks knight with moves
    actOnCoordinates(1, 0);
    testActualExpectedCoordinates("0:2 2:2", getMoves());
    //unselects piece
    actOnCoordinates(3,3);
  }

  /**
   * Tests movement of a piece
   */
  @Test
  void testMoving(){
    System.out.println("testMoving");
    // clicks on night with 2 moves
    actOnCoordinates(1,0);
    testActualExpectedCoordinates("0:2 2:2", getMoves());
    //moves knight around
    actOnCoordinates(2, 2);
    checkMoveCall(1,0,2,2);
    printBoard();
    actOnCoordinates(2,2);
    actOnCoordinates(3,4);
    checkMoveCall(2,2,3,4);
    printBoard();
    //moves a pawn
    actOnCoordinates(3, 1);
    actOnCoordinates(3,2);
    checkMoveCall(3,1,3,2);
    printBoard();
    //moves a bishop
    actOnCoordinates(2,0);
    actOnCoordinates(6,4);
    checkMoveCall(2,0,6,4);
    printBoard();
  }

  @Test
  void testTaking(){
    testMoving();
    System.out.println("testTaking");
    actOnCoordinates(6,4);
    actOnCoordinates(4,6);
    checkMoveCall(6,4,4,6);
    printBoard();
  }





  private void checkSetBoardSpaceCall(int x, int y, String identifier, String team){
    assertEquals(identifier, viewController.getIdentifier(x,y));
    assertEquals(team, viewController.getTeamName(x,y));
  }

  private void checkMoveCall(int startX, int startY, int endX, int endY){
    assertEquals(startX, viewController.getStartX());
    assertEquals(startY, viewController.getStartY());
    assertEquals(endX, viewController.getEndX());
    assertEquals(endY, viewController.getEndY());
  }

  private void checkRemoveCall(int x, int y){
    assertEquals(x, viewController.getRemX());
    assertEquals(y, viewController.getRemY());
  }

  private Set<Coordinate> getMoves(){
    return viewController.getAllPossibleMoves();
  }

  private void printBoard(){
    gameBoard.printBoard();
  }

  private void actOnCoordinates(int x, int y){
    modelController.actOnCoordinates(x, y);
  }

  // Compares a string of expected coordinates to a list of actual coordinates
  private void testActualExpectedCoordinates(String expected, Set<Coordinate> actual){
    assertTrue(testExpectedCoordinatesList(makeManyCoordinateList(expected), actual));
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
    if(coordinates.equals("")){
      return allCoords;
    }
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


}
