package ooga.model.engine;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.DummyViewController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.Player;
import ooga.model.engine.running.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunningChessTest {
  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;
  Player player1 = new Player("user");
  Player player2 = new Player("opponent");

  @BeforeEach
  private void SetUp(){
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("chess");
    gameEngine = modelController.getEngine();
    gameBoard = gameEngine.getBoard();
    gameEngine.setIfNoTurnRules(true);
    modelController.setPlayers("user", "opponent");
    printBoard();
  }

  @Test
  void testBasicTurn(){
    assertTrue(gameBoard.isPieceAtCoordinate(new Coordinate(4, 1)));
    assertFalse(gameBoard.isPieceAtCoordinate(new Coordinate(4, 2)));
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
    gameEngine.runTurn(4,1);
    assertTrue(gameBoard.isPieceAtCoordinate(new Coordinate(4, 1)));
    assertFalse(gameBoard.isPieceAtCoordinate(new Coordinate(4, 2)));
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
    gameEngine.runTurn(4,2);
    assertFalse(gameBoard.isPieceAtCoordinate(new Coordinate(4, 1)));
    assertTrue(gameBoard.isPieceAtCoordinate(new Coordinate(4, 2)));
    assertEquals(player2.getFullName(), gameEngine.getCurrentPlayerTurn());
    printBoard();
  }

  @Test
  void testEmptyPieceClick(){
    gameEngine.runTurn(4,4);
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
  }

  @Test
  void testIndecisivePlayer(){
    gameEngine.runTurn(3,1);
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
    gameEngine.runTurn(4,4);
    //printBoard();
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
    gameEngine.runTurn(4,1);
    assertEquals(player1.getFullName(), gameEngine.getCurrentPlayerTurn());
    gameEngine.runTurn(4,2);
    //printBoard();
    assertEquals(player2.getFullName(), gameEngine.getCurrentPlayerTurn());

  }









  private void printBoard(){
    gameBoard.printBoard();
  }


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
