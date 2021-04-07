package ooga.model.game_engine;

import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.piece_initialization.PieceCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionTesting {

  private GameBoard board;
  private PieceCreator pieceCreator = new PieceCreator("Chess");

  @BeforeEach
  void setup(){
    board = new GameBoard(8, 8);
  }

  @Test
  void addPiece(){
    board.printBoard();
    System.out.println("");
    GamePiece knight = makePiece("Knight", 4, 4);
    board.addPiece(knight);
    board.printBoard();
  }


  private GamePiece makePiece(String pieceName, int xCoord, int yCoord){
    return pieceCreator.makePiece(pieceName, makeCoordinates(xCoord, yCoord), 1);
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }

}
