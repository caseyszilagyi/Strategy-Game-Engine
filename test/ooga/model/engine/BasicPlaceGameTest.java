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
    gameEngine.setIfNoTurnRules(true);
    modelController.setPlayers("user", "opponent");
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
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(1,0);
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(0,0);
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(1,0);
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(0,0);
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(1,0);
    assertFalse(gameEngine.isGameOver());
    //printBoard();
    gameEngine.runTurn(0,0);
    printBoard();
    assertTrue(gameEngine.isGameOver());
  }

@Test
void testBasicRightWinCondition(){
  System.out.println("testBasicRightWinCondition");
  gameEngine.runTurn(0,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(0,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(1,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(1,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(2,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(2,0);
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(3,0);
  printBoard();
  assertTrue(gameEngine.isGameOver());

}

@Test
void testBasicDiagonalLeftWinCondition(){
  System.out.println("testBasicDiagonalLeftWinCondition");
  gameEngine.runTurn(0,0); // 0,0 U X
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(1,0); // 1,0 O
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(1,0); // 1,1 U X
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(2,0); // 2,0 O
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(2,0); // 2,1 U
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(3,0); // 3,0 O
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(2,0); // 2,2 U X
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(3,0); // 3,1 O
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(3,0); // 3,2 U
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(5,0); // 5,0 O
  assertFalse(gameEngine.isGameOver());
  gameEngine.runTurn(3,0); // 3,3 U X
  printBoard();
  assertTrue(gameEngine.isGameOver());
}

  @Test
  void testBasicDiagonalRightWinCondition(){
    System.out.println("testBasicDiagonalRightWinCondition");
    gameEngine.runTurn(0,0); // 0,0 U
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(0,0); // 0,1 O
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(0,0); // 0,2 U
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(1,0); // 1,0 O
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(0,0); // 0,3 U X
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(1,0); // 1,1 O
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(1,0); // 1,2 U X
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(2,0); // 2,0 O
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(2,0); // 2,1 U X
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(5,0); // 5,0 O
    assertFalse(gameEngine.isGameOver());
    gameEngine.runTurn(3,0); // 3,0 U X
    printBoard();
    assertTrue(gameEngine.isGameOver());
  }

  @Test
  void fixBottomRowClickBug(){
    System.out.println("Fixing bottom row click");
    gameEngine.runTurn(0,5);
    assertTrue(gameBoard.isPieceAtCoordinate(0,5));
    assertFalse(gameBoard.isPieceAtCoordinate(0,4));

  }


  private void printBoard(){
    int width = gameBoard.getWidth();
    int height = gameBoard.getHeight();
    System.out.println("");

    for (int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        if(!gameBoard.isPieceAtCoordinate(x,y)){
          System.out.print("_");
        } else {
          if(gameBoard.getPieceAtCoordinate(x,y).getPieceTeam().equals("user")){
            System.out.print("U");
          } else {
            System.out.print("O");
          }
        }
      }
      System.out.println("");
    }

  }

}
