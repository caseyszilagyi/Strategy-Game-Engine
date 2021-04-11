package ooga.model.game_engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.DummyViewController;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_engine.action_files.Action;
import ooga.model.game_engine.action_files.ActionCreator;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionTesting {

  private FrontEndExternalAPI viewController = new DummyViewController();
  private GameBoard board;
  private GameRules rules;
  private PieceCreator pieceCreator = new PieceCreator("Chess");
  private ActionCreator actionCreator = new ActionCreator(viewController);

  @BeforeEach
  void setup(){
    board = new GameBoard(8, 8);
    rules = new GameRules();
  }

  @Test
  void testBasicPlaceAction(){
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    String actionType = "Place";
    List<String> actionParameters = Arrays.asList("4:4", "Knight");
    Action action = actionCreator.createAction(actionType, actionParameters);
    action.executeAction(board, rules);
    assertTrue(board.isPieceAtCoordinate(testCoordinate));
  }

  @Test
  void testBasicMoveAction(){
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    Coordinate endingCoordinate = makeCoordinates(0, 0);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));
    testBasicPlaceAction();
    assertTrue(board.isPieceAtCoordinate(startingCoordinate));
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));
    String actionType = "Move";
    List<String> actionParameters = Arrays.asList("4:4", "0:0");
    Action action = actionCreator.createAction(actionType, actionParameters);
    action.executeAction(board, rules);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    assertTrue(board.isPieceAtCoordinate(endingCoordinate));
  }


  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }

}
