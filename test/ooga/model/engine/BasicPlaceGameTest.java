package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.BackEndExternalAPI;
import ooga.controller.DummyViewController;
import ooga.controller.ModelController;
import ooga.model.components.GameBoard;
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
    assertFalse(gameBoard.isPieceAtCoordinate(0,0));
    gameEngine.runTurn(0,0);
    assertTrue(gameBoard.isPieceAtCoordinate(0,0));
    printBoard();
  }


  private void printBoard(){
    gameBoard.printBoard();
  }

}
