package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.BackEndExternalAPI;
import ooga.controller.DummyViewController;
import ooga.controller.ModelController;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.engine.running.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicPlaceGameTest {

  BackEndExternalAPI modelController;
  DummyViewController viewController;
  Engine gameEngine;
  GameBoard gameBoard;

  @BeforeEach
  private void SetUp(){
    modelController = new ModelController();
    viewController = new DummyViewController();
    modelController.setBoardController(viewController);
    modelController.setGameType("connectfour");
    gameBoard = modelController.getEngine().getBoard();
    gameEngine = modelController.getEngine();
    gameEngine.setIfTurnRules(true);
    printBoard();
  }

  @Test
  void testBasicPlace(){
    System.out.println("testBasicPlace");
    assertFalse(gameBoard.isPieceAtCoordinate(0,5));
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameBoard.isPieceAtCoordinate(0,5));
  }

  @Test
  void testMultiplePlace(){
    System.out.println("testMultiplePlace");
    assertFalse(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameBoard.isPieceAtCoordinate(0,4));

  }

  @Test
  void testInvalidPlace(){
    System.out.println("testInvalidPlace");
    assertFalse(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));
    GamePiece validPiece = gameBoard.getPieceAtCoordinate(0,5);
    gameEngine.runTurn(0,5);
    printBoard();
    assertTrue(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));
    assertEquals(validPiece, gameBoard.getPieceAtCoordinate(0,5));
  }

  @Test
  void testBasicDownWinCondition(){
    System.out.println("testBasicDownWinCondition");
    gameEngine.runTurn(0,0);
    //printBoard();
    gameEngine.runTurn(1,0);
    //printBoard();
    gameEngine.runTurn(0,0);
    //printBoard();
    gameEngine.runTurn(1,0);
    //printBoard();
    gameEngine.runTurn(0,0);
    //printBoard();
    gameEngine.runTurn(1,0);
    //printBoard();
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameEngine.isGameOver());
  }




  private void printBoard(){
    gameBoard.printBoard();
  }

}
