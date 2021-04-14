package ooga.model.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;

/**
 * This is the representation of the board. It holds all of the GamePiece objects, and has the
 * coordinates corresponding to each of these objects
 */
public class GameBoard implements Board {

  private FrontEndExternalAPI viewController;
  private int width, height;
  private Map<Coordinate, GamePiece> pieceCoordMap = new HashMap<>();
  private Set<Coordinate> currentLegalMoveCoordinates;

  private Coordinate activeCoordinates;
  private GamePiece activePiece;

  private boolean isHeldPiece = false;
  private GamePiece heldPiece;

  /**
   * Initializes this board
   *
   * @param width  The width of the board
   * @param height The height of the board
   */
  public GameBoard(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Sets if there is a piece being actively manipulated by the user
   * @param isHeldPiece is whether or not there is a piece being manipulated
   */
  private void setIsHeldPiece(Boolean isHeldPiece){
    this.isHeldPiece = isHeldPiece;
  }

  /**
   * Returns if there is a piece being actively manipulated by the user
   * @return whether or not there is a piece being manipulated
   */
  private boolean getIsHeldPiece(){
    return isHeldPiece;
  }

  /**
   * Sets the piece being manipulated;
   * @param heldPiece sets piece being manipulated
   */
  private void setHeldPiece(GamePiece heldPiece){
    this.heldPiece = heldPiece;
  }



  /**
   * Sets the view controller that the board will use to make method calls to the front end
   *
   * @param viewController The view controller
   */
  public void setViewController(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }


  /**
   * Determines all legal moves for a piece at a given coordinate. Automatically sets this piece as
   * the active piece when this is done.
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public void determineAllLegalMoves(int x, int y) {
    activeCoordinates = makeCoordinates(x, y);
    currentLegalMoveCoordinates = pieceCoordMap.get(activeCoordinates).determineAllLegalMoves();
    activePiece = pieceCoordMap.get(activeCoordinates);
  }

  /**
   * Determines if the coordinates given are listed as a legal move for the active piece
   * @param x The x coordinate
   * @param y The y coordinate
   * @return True if the move is legal, false otherwise
   */
  public boolean isLegalMoveLocation(int x, int y){
    return currentLegalMoveCoordinates.contains(makeCoordinates(x,y));
  }

  /**
   * Checks tho see if a piece is at a certain coordinate
   *
   * @param coordinate The coordinate object that may or may not correspond to a certain piece
   * @return True if there is a piece at the coordinate, false otherwise
   */
  public boolean isPieceAtCoordinate(Coordinate coordinate) {
    return (isCoordinateOnBoard(coordinate) && pieceCoordMap.containsKey(coordinate));
  }

  /**
   * Checks tho see if a piece is at a certain coordinate
   *
   * @param x The x coordinate value
   * @param y The y coordinate value
   * @return True if there is a piece at the coordinate, false otherwise
   */
  public boolean isPieceAtCoordinate(int x, int y){
    return isPieceAtCoordinate(makeCoordinates(x, y));
  }


  /**
   * Gets a piece at a certain coordinate
   *
   * @param coordinate The coordinate object that corresponds to the piece
   * @return The piece object
   */
  public GamePiece getPieceAtCoordinate(Coordinate coordinate) {
    return pieceCoordMap.get(coordinate);
  }

  /**
   * Checks if a coordinate is on the board
   *
   * @param coordinates The coordinate object
   * @return True if it is on the board, false otherwise
   */
  public boolean isCoordinateOnBoard(Coordinate coordinates) {
    return coordinates.getX() < width && coordinates.getY() < height && coordinates
            .getX() >= 0 && coordinates.getY() >= 0;
  }


  /**
   * Checks if a friendly piece is on the board in this location
   *
   * @param coordinates The coordinate object corresponding to the location
   * @param teamName    The name of the team
   * @return True if there is a friendly piece there, false otherwise
   */
  public boolean checkIfFriendlyPieceInLocation(Coordinate coordinates, String teamName) {
    return isPieceAtCoordinate(coordinates) && getPieceAtCoordinate(coordinates).getPieceTeam()
            .equals(teamName);
  }

  /**
   * Checks if an enemy piece is on the board in this location
   *
   * @param coordinates The coordinate object corresponding to the location
   * @param teamName    The name of the team
   * @return True if there is a enemy piece there, false otherwise
   */
  public boolean checkIfOpponentPieceInLocation(Coordinate coordinates, String teamName) {
    return isPieceAtCoordinate(coordinates) && !getPieceAtCoordinate(coordinates).getPieceTeam()
            .equals(teamName);
  }


  /**
   * Moves a piece by giving it the ending coordinates. Will move the activePiece based
   * on the activeCoordinates
   * @param x The ending x position
   * @param y The ending y position
   */
  public void movePiece(int x, int y){
    Coordinate newCoordinates = makeCoordinates(x, y);
    activePiece.executeMove(newCoordinates);
    pieceCoordMap.remove(activeCoordinates);
    pieceCoordMap.put(newCoordinates, activePiece);
  }


  /**
   * Gets the height of the board
   * @return The board height
   */
  public int getHeight(){
    return height;
  }

  /**
   * Gets the width of the board
   * @return The board width
   */
  public int getWidth(){
    return width;
  }


  // Movements through actions

  @Override
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {
    if (!isPieceAtCoordinate(startingCoordinate) || !isCoordinateOnBoard(startingCoordinate)) {
      System.err.println("Tried to move non-existing piece");
      return false;
    }
    if (!isCoordinateOnBoard(endingCoordinate)) {
      System.err.println("Tried to move off the board");
      return false;
    }
    GamePiece pieceToMove = getPieceAtCoordinate(startingCoordinate);
    if (isPieceAtCoordinate(endingCoordinate)) {
      GamePiece conflict = getPieceAtCoordinate(endingCoordinate);
    }

    pieceToMove.setPieceCoordinates(endingCoordinate);
    pieceCoordMap.put(endingCoordinate, pieceToMove);
    pieceCoordMap.remove(startingCoordinate);
    return true;
  }

  @Override
  public void removePiece(Coordinate coordinate) {
    pieceCoordMap.remove(coordinate);
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
    pieceCoordMap.put(newPieceCoordinates, newPieceType);
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

  public Coordinate findKing(String team){
    for(GamePiece piece : pieceCoordMap.values()){
      if(piece.getPieceName().equals("king") && piece.getPieceTeam().equals(team)) return piece.getPieceCoordinates();
    }
    return null;
  }

  public Map<Coordinate, GamePiece> getPieceCoordMap(){
    return pieceCoordMap;
  }

  // helper methods


  // makes a set of coordinates
  private Coordinate makeCoordinates(int x, int y) {
    return new Coordinate(x, y);
  }

  // for testing
  public void printBoard() {
    System.out.println();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Coordinate curr = makeCoordinates(x, y);
        if (isPieceAtCoordinate(curr)) {
          System.out.print(getPieceAtCoordinate(curr).getPieceName().charAt(0));
        } else {
          System.out.print("_");
        }
      }
      System.out.println();
    }
  }

}
