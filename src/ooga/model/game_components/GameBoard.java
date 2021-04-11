package ooga.model.game_components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;

public class GameBoard implements Board {

  private FrontEndExternalAPI viewController;
  private List<GamePiece> activePieces;
  private Player currentTurn;
  private GamePiece[][] grid;

  private Map<Coordinate, GamePiece> pieceCoordMap = new HashMap<>();

  private int width, height;

  public GameBoard(int width, int height){
    this.width = width;
    this.height = height;
    grid = new GamePiece[width][height];
    activePieces = new ArrayList<>();
    makePieceCoordMap();
  }

  /**
   * Sets the view controller that the board will use to make method calls to the front end
   * @param viewController The view controller
   */
  public void setViewController(FrontEndExternalAPI viewController){
    this.viewController = viewController;
  }


  /**
   * Is given coordinates and makes the appropriate method calls to the front end
   * @param x The x coordinate
   * @param y The y coordinate
  */
  public void actOnCoordinates(int x, int y, boolean isMovement){
    Coordinate currentCoordinates = makeCoordinates(x, y);
    // TODO: this method needs to be generalized for place games. should be done in game engine method call
    if(pieceCoordMap.containsKey(currentCoordinates) && isMovement){
      pieceCoordMap.get(currentCoordinates);
    }
  }
  

  public void setGrid(GamePiece[][] grid){
    this.grid = grid;
    for(int x = 0; x<grid[0].length; x++){
      for(int y = 0; y<grid.length; y++){
        if(grid[y][x] != null){
          activePieces.add(grid[y][x]);
        }
      }
    }
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
    grid[startingCoordinate.getY()][startingCoordinate.getX()] = null;
    pieceToMove.setPieceCoordinates(endingCoordinate);
    grid[endingCoordinate.getY()][endingCoordinate.getX()] = pieceToMove;
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
    grid[newPieceCoordinates.getY()][newPieceCoordinates.getX()] = newPieceType;
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



  public GamePiece getPieceAtCoordinate(Coordinate coordinate){
    return grid[coordinate.getY()][coordinate.getX()];
  }

  private boolean isCoordinateOnBoard(Coordinate coordinates) {
    if(coordinates.getX() >= width || coordinates.getY() >= height || coordinates
        .getX() < 0 || coordinates.getY() < 0){
      return false;
    }
    return true;
  }

  public boolean isPieceAtCoordinate(Coordinate coordinate){
    return (isCoordinateOnBoard(coordinate) && grid[coordinate.getY()][coordinate.getX()] != null);
  }




  //makes the coordinate:piece map
  private void makePieceCoordMap(){
    for(GamePiece piece: activePieces){
      pieceCoordMap.put(piece.getPieceCoordinates(), piece);
    }
  }

  private Set<Coordinate> getAllActivePieceCoordinates() {
    Set<Coordinate> allCoordinates = new HashSet<>();
    for(GamePiece piece : activePieces){
      allCoordinates.add(piece.getPieceCoordinates());
    }
    return allCoordinates;
  }

  // makes a set of coordinates, useful so that coordinate implementation is only present
  // in our gameboard
  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x,y);
  }


  // for testing
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

  // For testing
  public void printAllPossibleMoves(int xPos, int yPos){
    grid[yPos][xPos].setDummyBoard(grid);
    Set<Coordinate> moves= grid[yPos][xPos].getAllLegalMoves();
    for(Coordinate coords: moves){
      System.out.println(coords.toString());
    }
  }

}
