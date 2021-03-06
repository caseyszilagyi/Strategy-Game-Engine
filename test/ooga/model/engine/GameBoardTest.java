package ooga.model.engine;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.DummyViewController;
import ooga.exceptions.GameRunningException;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.initialization.pieces.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

  private GameBoard board;
  private PieceCreator pieceCreator;
  private DummyViewController viewController = new DummyViewController();

  @BeforeEach
  void setup(){
    board = new GameBoard(8, 8);
    board.setViewController(new DummyViewController());
    pieceCreator = new PieceCreator("chess", board);
  }

  @Test
  void testBasicAddPiece(){
    board.printBoard();
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece knight = makePiece("knight", testCoordinate);
    board.addPiece(knight);
    board.printBoard();
    assertTrue(board.isPieceAtCoordinate(testCoordinate));
  }

  @Test
  void testUnableToAddToOccupiedSpace(){
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece queen = makePiece("queen", testCoordinate);
    assertTrue(board.addPiece(queen));
    assertTrue(board.isPieceAtCoordinate(testCoordinate));

    GamePiece knight = makePiece("knight", testCoordinate);
    assertThrows(GameRunningException.class, () -> board.addPiece(knight), "PieceAddConflict");
  }

  @Test
  void testGetPieceAtCoordinate(){
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertNull(board.getPieceAtCoordinate(testCoordinate));
    GamePiece queen = makePiece("queen", testCoordinate);
    assertTrue(board.addPiece(queen));
    assertNotNull(board.getPieceAtCoordinate(testCoordinate));
    assertEquals(board.getPieceAtCoordinate(testCoordinate), queen);

    GamePiece knight = makePiece("knight", testCoordinate);
    assertNotEquals(board.getPieceAtCoordinate(testCoordinate), knight);

  }

  @Test
  void testPlaceOutOfBounds(){
    Coordinate testCoordinate = makeCoordinates(8, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece knight = makePiece("knight", testCoordinate);
    assertThrows(GameRunningException.class, () -> board.addPiece(knight), "PieceAddConflict");

    Coordinate testCoordinate2 = makeCoordinates(4, 8);
    assertFalse(board.isPieceAtCoordinate(testCoordinate2));
    GamePiece knight2 = makePiece("knight", testCoordinate2);
    assertThrows(GameRunningException.class, () -> board.addPiece(knight2), "PieceAddConflict");

    Coordinate testCoordinate3 = makeCoordinates(-1, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate3));
    GamePiece knight3 = makePiece("knight", testCoordinate3);
    assertThrows(GameRunningException.class, () -> board.addPiece(knight3), "PieceAddConflict");

    Coordinate testCoordinate4 = makeCoordinates(4, -1);
    assertFalse(board.isPieceAtCoordinate(testCoordinate4));
    GamePiece knight4 = makePiece("knight", testCoordinate4);
    assertThrows(GameRunningException.class, () -> board.addPiece(knight4), "PieceAddConflict");
  }

  @Test
  void testBasicMovePiece(){
    board.printBoard();
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    GamePiece knight = makePiece("knight", startingCoordinate);
    board.addPiece(knight);
    assertTrue(board.isPieceAtCoordinate(startingCoordinate));
    board.printBoard();

    Coordinate endingCoordinate = makeCoordinates(0,0);
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));
    assertTrue(board.movePiece(startingCoordinate, endingCoordinate));
    board.printBoard();

    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    assertTrue(board.isPieceAtCoordinate(endingCoordinate));
  }

  @Test
  void testMoveNonExistingPiece(){
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    Coordinate endingCoordinate = makeCoordinates(0,0);
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));
    assertThrows(GameRunningException.class, () -> board.movePiece(startingCoordinate, endingCoordinate), "NoPieceToMove");
  }

  @Test
  void testMoveToOccupiedSpace(){
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    GamePiece knight = makePiece("knight", startingCoordinate);
    assertTrue(board.addPiece(knight));
    assertTrue(board.isPieceAtCoordinate(startingCoordinate));

    Coordinate endingCoordinate = makeCoordinates(0,0);
    GamePiece queen = makePiece("queen", endingCoordinate);
    assertTrue(board.addPiece(queen));
    assertTrue(board.isPieceAtCoordinate(endingCoordinate));
    assertThrows(GameRunningException.class, () -> board.movePiece(startingCoordinate, endingCoordinate), "PieceMoveConflict");

    assertTrue(board.isPieceAtCoordinate(startingCoordinate));
    assertTrue(board.isPieceAtCoordinate(endingCoordinate));
    assertEquals(queen, board.getPieceAtCoordinate(endingCoordinate));
  }

  @Test
  void testMoveOutOfBounds(){
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    GamePiece knight = makePiece("knight", startingCoordinate);
    board.addPiece(knight);
    assertTrue(board.isPieceAtCoordinate(startingCoordinate));

    Coordinate endingCoordinate = makeCoordinates(-1,0);
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));

    assertThrows(GameRunningException.class, () -> board.movePiece(startingCoordinate, endingCoordinate), "PieceMoveConflict");

    assertTrue(board.isPieceAtCoordinate(startingCoordinate));
    assertFalse(board.isPieceAtCoordinate(endingCoordinate));
  }


  private GamePiece makePiece(String pieceName, Coordinate coord){
    return pieceCreator.makePiece(pieceName, coord, 1, "noTeam");
  }

  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1, "noTeam");
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }


}
