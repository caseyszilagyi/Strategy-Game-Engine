package ooga.model.game_components;

import java.util.ArrayList;
import java.util.List;

public class GameBoard implements Board {

  private List<GamePiece> activePieces;
  private Player currentTurn;
  private GamePiece[][] grid;

  private int width, height;


  public GameBoard(int width, int height){
    this.width = width;
    this.height = height;
    grid = new GamePiece[width][height];
    activePieces = new ArrayList<>();
  }


  @Override
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {
    if(!isPieceAtCoordinate(startingCoordinate) || !isCoordinateOnBoard(startingCoordinate)){
      System.err.println("Tried to move non-existing piece");
      return false;
    }
    if(!isCoordinateOnBoard(endingCoordinate)){
      System.err.println("Tried to move off the board");
      return false;
    }
    GamePiece pieceToMove = getPieceAtCoordinate(startingCoordinate);
    if(isPieceAtCoordinate(endingCoordinate)){
      GamePiece conflict = getPieceAtCoordinate(endingCoordinate);
      activePieces.remove(conflict);
    }
    grid[startingCoordinate.getX()][startingCoordinate.getY()] = null;
    pieceToMove.setPieceCoordinates(endingCoordinate);
    grid[endingCoordinate.getX()][endingCoordinate.getY()] = pieceToMove;
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
    if (isAnyCoordinateConflicts(newPieceCoordinates)) {
      return false;
    }
    activePieces.add(newPieceType);
    grid[newPieceCoordinates.getX()][newPieceCoordinates.getY()] = newPieceType;
    return true;
  }

  private boolean isAnyCoordinateConflicts(Coordinate coordinates) {
    if (!isCoordinateOnBoard(coordinates)) {
      System.err.println("Coordinate outside of the board");
      return true;
    }
    if (isPieceAtCoordinate(coordinates)) {
      System.err.println("Coordinate is occupied");
      return true;
    }
    return false;
  }

  public GamePiece[][] getBoardArray(){
    return grid;
  }

  public List<Coordinate> getAllActivePieceCoordinates() {
    List<Coordinate> allCoordinates = new ArrayList<>();
    for(GamePiece piece : activePieces){
      allCoordinates.add(piece.getPieceCoordinates());
    }
    return allCoordinates;
  }

  public GamePiece getPieceAtCoordinate(Coordinate coordinate){
    return grid[coordinate.getX()][coordinate.getY()];
  }

  private boolean isCoordinateOnBoard(Coordinate coordinates) {
    if(coordinates.getX() >= width || coordinates.getY() >= height || coordinates
        .getX() < 0 || coordinates.getY() < 0){
      return false;
    }
    return true;
  }

  public boolean isPieceAtCoordinate(Coordinate coordinate){
    return (isCoordinateOnBoard(coordinate) && grid[coordinate.getX()][coordinate.getY()] != null);
  }

  public void printBoard(){
    System.out.println("");
    for(GamePiece[] row : grid){
      for (GamePiece pieceOrNot : row) {
        if(pieceOrNot == null){
          System.out.print("_");
        } else {
          System.out.print("p");
        }
      }
      System.out.println("");
    }
  }

}
