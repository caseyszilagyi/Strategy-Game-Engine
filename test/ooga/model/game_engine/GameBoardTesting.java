package ooga.model.game_engine;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTesting {

  private GameBoard board;
  private PieceCreator pieceCreator = new PieceCreator("Chess");

  @BeforeEach
  void setup(){
    board = new GameBoard(8, 8);
  }

  @Test
  void testBasicAddPiece(){
    board.printBoard();
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece knight = makePiece("Knight", testCoordinate);
    board.addPiece(knight);
    board.printBoard();
    assertTrue(board.isPieceAtCoordinate(testCoordinate));
  }

  @Test
  void testUnableToAddToOccupiedSpace(){
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece queen = makePiece("Queen", testCoordinate);
    assertTrue(board.addPiece(queen));
    assertTrue(board.isPieceAtCoordinate(testCoordinate));

    GamePiece knight = makePiece("Knight", testCoordinate);
    assertFalse(board.addPiece(knight));

  }

  @Test
  void testGetPieceAtCoordinate(){
    Coordinate testCoordinate = makeCoordinates(4, 4);
    assertNull(board.getPieceAtCoordinate(testCoordinate));
    GamePiece queen = makePiece("Queen", testCoordinate);
    assertTrue(board.addPiece(queen));
    assertNotNull(board.getPieceAtCoordinate(testCoordinate));
    assertEquals(board.getPieceAtCoordinate(testCoordinate), queen);

    GamePiece knight = makePiece("Knight", testCoordinate);
    assertNotEquals(board.getPieceAtCoordinate(testCoordinate), knight);

  }

  private GamePiece makePiece(String pieceName, Coordinate coord){
    return pieceCreator.makePiece(pieceName, coord, 1);
  }

  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1);
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }

}
