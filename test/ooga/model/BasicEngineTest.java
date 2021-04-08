package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_engine.Engine;
import ooga.model.game_engine.GameEngine;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

public class BasicEngineTest {

  ModelController modelController;
  Engine gameEngine;
  GameBoard gameBoard;

  @BeforeEach
  private void SetUp(){
    modelController = new ModelController();
    modelController.setViewController(new DummyViewController());
  }

  /**
   * Tests the engine getting moves
   */
  @Test
  void TestEngineMoveGet(){
    modelController.setGameType("Chess");
    gameEngine = modelController.getEngine();
    gameBoard = gameEngine.getBoard();
    gameBoard.printBoard();
    modelController.getAllPossibleMoves(0,1);
    System.out.println();
    modelController.getAllPossibleMoves(1,0);
    gameEngine.executeAction("Move 1:0 2:2");
    gameBoard.printBoard();

  }
}
