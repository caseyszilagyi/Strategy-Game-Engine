package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import ooga.controller.DummyViewController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.GameRules;
import ooga.model.initialization.pieces.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesTest {

  private GameBoard board;
  private PieceCreator pieceCreator;
  private DummyViewController viewController = new DummyViewController();
  private GamePiece basicTestPiece;

  @BeforeEach
  void setup(){
    board = new GameBoard(8, 8);
    pieceCreator = new PieceCreator("chess", viewController, board);
    Coordinate basicTestCoordinate = makeCoordinates(0, 0);
    basicTestPiece = makePiece("pawn", basicTestCoordinate);
  }


  @Test
  void testBasicGameRulesCreation(){
    GameRules gameRules = new GameRules("chess");
    assertNotNull(gameRules);
  }

  @Test
  void testTurnConditionString(){
    GameRules gameRules = new GameRules("chess");
    List<String> turnConditionList = gameRules.getTurnConditionsAsStringList();
    assertEquals(1, turnConditionList.size());
    assertEquals("NoHeldPiece", turnConditionList.get(0));
  }

  @Test
  void testIsTurnOverWithForce(){
    GameRules gameRules = new GameRules("chess");
    assertTrue(gameRules.checkForNextTurn(board, basicTestPiece));
  }

  @Test
  void testIsTurnOverWithConstant(){
    GameRules gameRules = new GameRules("testConstant");
    assertFalse(gameRules.checkForNextTurn(board, basicTestPiece));
  }

  @Test
  void testMultipleForcesTurnCondtions(){
    GameRules gameRules = new GameRules("testMultipleForce");
    assertTrue(gameRules.checkForNextTurn(board, basicTestPiece));
  }

  @Test
  void testConstantAndForceTurnCondtions(){
    GameRules gameRules = new GameRules("testConstantAndForce");
    assertFalse(gameRules.checkForNextTurn(board, basicTestPiece));
  }


  private GamePiece makePiece(String pieceName, Coordinate coord){
    return pieceCreator.makePiece(pieceName, coord, 1, "noTeam");
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }
}
