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


  @Override
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {
    if(isPieceAtCoordinate(endingCoordinate)){
      System.err.println("Tried to move piece to occupied space");
      return false;
    }
    if(!isPieceAtCoordinate(startingCoordinate)){
      System.err.println("Tried to move non-existing piece");
      return false;
    }
    GamePiece pieceToMove = getPieceAtCoordinate(startingCoordinate);
    pieceToMove.setPieceCoordinates(endingCoordinate);
    return true;
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
    if(newPieceCoordinates.getX() >= width || newPieceCoordinates.getY() >= height || newPieceCoordinates.getX() < 0 || newPieceCoordinates.getY() < 0){
      System.err.println("Tried to add a piece outside of the board");
      return false;
    }
    if(isPieceAtCoordinate(newPieceCoordinates)){
      System.err.println("Tried to place a piece on top of another piece");
      return false;
    }
    activePieces.add(newPieceType);
    return true;
  }

  public List<Coordinate> getAllActivePieceCoordinates() {
    List<Coordinate> allCoordinates = new ArrayList<>();
    for(GamePiece piece : activePieces){
      allCoordinates.add(piece.getPieceCoordinates());
    }
    return allCoordinates;
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

    System.out.println("");
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
