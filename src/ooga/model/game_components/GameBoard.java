package ooga.model.game_components;

import java.util.ArrayList;
import java.util.List;

public class GameBoard implements Board {

  private List<GamePiece> activePieces;
  private Player currentTurn;

  private int width, height;

  public GameBoard(int width, int height){
    this.width = width;
    this.height = height;
    activePieces = new ArrayList<>();
  }

  public List<Coordinate> getAllActivePieceCoordinates() {
    return null;
  }

  @Override
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {
    return false;
  }

  @Override
  public boolean removePiece(Coordinate coordinate) {
    return false;
  }

  @Override
  public boolean changePiece(Coordinate coordinate, GamePiece newPieceType) {
    return false;
  }

  @Override
  public boolean addPiece(GamePiece newPieceType) {
    Coordinate newPieceCoordinates = newPieceType.getPieceCoordinates();
    if(newPieceCoordinates.getX() >= width || newPieceCoordinates.getY() >= height){
      System.err.println("Tried to add a piece outside of the board");
      return false;
    }
    for(GamePiece currentPiece : activePieces){
      if(currentPiece.getPieceCoordinates().equals(newPieceCoordinates)){
        System.err.println("Tried to add a piece on another piece");
        return false;
      }
    }
    activePieces.add(newPieceType);
    return true;
  }

  public GamePiece getPieceAtCoordinate(Coordinate coordinate){
    for(GamePiece currentPiece : activePieces) {
      if (currentPiece.getPieceCoordinates().equals(coordinate)) {
        return currentPiece;
      }
    }
    return null;
  }

  public boolean isPieceAtCoordinate(Coordinate coordinate){
    for(GamePiece currentPiece : activePieces) {
      if (currentPiece.getPieceCoordinates().equals(coordinate)) {
        return true;
      }
    }
    return false;
  }

  public void printBoard(){
    String[][] printableBoard = new String[width][height];
    for(GamePiece piece : activePieces){
      printableBoard[piece.getPieceCoordinates().getX()][piece.getPieceCoordinates().getY()] = "p";
    }

    for(String[] row : printableBoard){
      for (String pieceOrNot : row) {
        if(pieceOrNot == null){
          System.out.print("x");
        } else {
          System.out.print(pieceOrNot);
        }
      }
      System.out.println("");
    }
  }

}
