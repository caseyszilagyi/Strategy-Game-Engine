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

  @Test
  void testPlaceOutOfBounds(){
    Coordinate testCoordinate = makeCoordinates(8, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate));
    GamePiece knight = makePiece("Knight", testCoordinate);
    assertFalse(board.addPiece(knight));

    Coordinate testCoordinate2 = makeCoordinates(4, 8);
    assertFalse(board.isPieceAtCoordinate(testCoordinate2));
    GamePiece knight2 = makePiece("Knight", testCoordinate2);
    assertFalse(board.addPiece(knight2));

    Coordinate testCoordinate3 = makeCoordinates(-1, 4);
    assertFalse(board.isPieceAtCoordinate(testCoordinate3));
    GamePiece knight3 = makePiece("Knight", testCoordinate3);
    assertFalse(board.addPiece(knight3));

    Coordinate testCoordinate4 = makeCoordinates(4, -1);
    assertFalse(board.isPieceAtCoordinate(testCoordinate4));
    GamePiece knight4 = makePiece("Knight", testCoordinate4);
    assertFalse(board.addPiece(knight4));
  }

  @Test
  void testBasicMovePiece(){
    board.printBoard();
    Coordinate startingCoordinate = makeCoordinates(4, 4);
    assertFalse(board.isPieceAtCoordinate(startingCoordinate));
    GamePiece knight = makePiece("Knight", startingCoordinate);
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
    assertFalse(board.movePiece(startingCoordinate, endingCoordinate));
  }

  @Test
  void testMoveToOccupiedSpace(){

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
