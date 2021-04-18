package ooga.model.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;

/**
 * This is the representation of the board. It holds all of the GamePiece objects, and has the
 * coordinates corresponding to each of these objects. It also has some information about the state
 * of the game, such as which piece is currently considered active and the coordinates of that
 * piece.
 *
 * @author Casey Szilagyi
 */
public class GameBoard implements Board {

  private FrontEndExternalAPI viewController;
  private int width, height;
  private Map<Coordinate, GamePiece> pieceCoordMap = new HashMap<>();
  private Set<Coordinate> currentLegalMoveCoordinates;

  private Coordinate activeCoordinates;
  private GamePiece activePiece;
  private GamePiece tempStoragePiece;

  private boolean isHeldPiece = false;

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
   * Sets the view controller that the board will use to make method calls to the front end
   *
   * @param viewController The view controller
   */
  public void setViewController(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }

  /**
   * Sets if there is a piece being actively manipulated by the user
   *
   * @param isHeldPiece is whether or not there is a piece being manipulated
   */
  public void setIsHeldPiece(Boolean isHeldPiece) {
    this.isHeldPiece = isHeldPiece;
  }

  /**
   * Returns if there is a piece being actively manipulated by the user
   *
   * @return whether or not there is a piece being manipulated
   */
  public boolean getIsHeldPiece() {
    return isHeldPiece;
  }

  /**
   * Gets the height of the board
   *
   * @return The board height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the width of the board
   *
   * @return The board width
   */
  public int getWidth() {
    return width;
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
   * Determines the take moves of an opposing team, without restrictions
   *
   * @param teamName The name of the friendly team
   * @return All of the coordinates of the possible take moves
   */
  public Set<Coordinate> determineOppositeTeamTakeMovesWithoutRestrictions(String teamName) {
    Set<Coordinate> opponentTeamLegalMoves = new HashSet<>();
    for (GamePiece piece : pieceCoordMap.values()) {
      if (!piece.getPieceTeam().equals(teamName)) {
        opponentTeamLegalMoves.addAll(piece.determineAllPossibleRestrictionlessTakeMoves());
      }
    }
    return opponentTeamLegalMoves;
  }

  /**
   * Determines if the coordinates given are listed as a legal move for the active piece
   *
   * @param x The x coordinate
   * @param y The y coordinate
   * @return True if the move is legal, false otherwise
   */
  public boolean isLegalMoveLocation(int x, int y) {
    return currentLegalMoveCoordinates.contains(makeCoordinates(x, y));
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
   * Checks too see if a piece is at a certain coordinate
   *
   * @param x The x coordinate value
   * @param y The y coordinate value
   * @return True if there is a piece at the coordinate, false otherwise
   */
  public boolean isPieceAtCoordinate(int x, int y) {
    return isPieceAtCoordinate(makeCoordinates(x, y));
  }

  /**
   * Checks to see if a piece is at a certain coordinate
   *
   * @param coordinate The coordinate object that may or may not correspond to a certain piece
   * @return True if there is a piece at the coordinate, false otherwise
   */
  public boolean isPieceAtCoordinate(Coordinate coordinate) {
    return (isCoordinateOnBoard(coordinate) && pieceCoordMap.containsKey(coordinate));
  }

  /**
   * Returns the piece at a given set of coordinates
   *
   * @param x The x coordinate
   * @param y the y coordinate
   * @return
   */
  public GamePiece getPieceAtCoordinate(int x, int y) {
    return getPieceAtCoordinate(makeCoordinates(x, y));
  }

  /**
   * Returns the piece at a certain set of coordinates
   *
   * @param coordinate The coordinate object that corresponds to the piece
   * @return The piece object
   */
  public GamePiece getPieceAtCoordinate(Coordinate coordinate) {
    return pieceCoordMap.get(coordinate);
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
   * Moves a piece by giving it the ending coordinates. Will move the activePiece based on the
   * activeCoordinates
   *
   * @param endingX The ending x position
   * @param endingY The ending y position
   */
  public void movePiece(int endingX, int endingY) {
    Coordinate newCoordinates = makeCoordinates(endingX, endingY);
    activePiece.executeMove(newCoordinates);
    removePiece(activeCoordinates);
    pieceCoordMap.put(newCoordinates, activePiece);
  }


  /**
   * Moves a piece from one set of coordinates to another
   *
   * @param start The starting coordinates of the piece
   * @param end   The ending coordinates of the piece
   * @return True if it was able to be moved, false otherwise
   */
  @Override
  public boolean movePiece(Coordinate start, Coordinate end) {
    if (pieceCoordMap.get(start) == null || !isCoordinateOnBoard(end)) {
      return false;
    }
    movePiece(start.getX(), start.getY(), end.getX(), end.getY());
    return true;
  }

  /**
   * Moves a piece on the board, but it does not have to be the active piece. Movement is not done
   * through a piece movement object in this case, and also doesn't refer to the active piece at
   * all
   *
   * @param startingX The starting x position of the piece
   * @param startingY The starting y position of the piece
   * @param endingX   The ending x position of the piece
   * @param endingY   The ending y position of the piece
   */
  public void movePiece(int startingX, int startingY, int endingX, int endingY) {
    moveBackendPiece(startingX, startingY, endingX, endingY);
    viewController.movePiece(startingX, startingY, endingX, endingY);
  }

  /**
   * Moves a piece only in the back end, but does not send the info to the front end. Useful for
   * scenarios where a piece needs to be moved in order to check something
   *
   * @param startingX The starting x position of the piece
   * @param startingY The starting y position of the piece
   * @param endingX   The ending x position of the piece
   * @param endingY   The ending y position of the piece
   */
  public void moveBackendPiece(int startingX, int startingY, int endingX, int endingY) {
    moveBackendPiece(makeCoordinates(startingX, startingY), makeCoordinates(endingX, endingY));
  }


  /**
   * Moves a piece only in the back end, but does not send the info to the front end. Useful for
   * scenarios where a piece needs to be moved in order to check something
   *
   * @param start The starting coordinates of the piece
   * @param end   The ending coordinates of the piece
   */
  public void moveBackendPiece(Coordinate start, Coordinate end) {
    GamePiece currentPiece = pieceCoordMap.get(start);
    currentPiece.setPieceCoordinates(end);
    pieceCoordMap.remove(start);
    pieceCoordMap.put(end, currentPiece);
  }


  /**
   * Takes a piece off the board
   *
   * @param coordinate The coordinate object corresponding to the piece to remove
   */
  @Override
  public void removePiece(Coordinate coordinate) {
    pieceCoordMap.remove(coordinate);
    viewController.removePiece(coordinate.getX(), coordinate.getY());
  }


  /**
   * Adds a piece to the board
   *
   * @param newPieceType The new piece to add
   * @return The location on the board that it is placed at
   */
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


  public Coordinate findPieceCoordinates(String teamName, String pieceName) {
    for (GamePiece piece : pieceCoordMap.values()) {
      if (piece.getPieceName().equals(pieceName) && piece.getPieceTeam().equals(teamName)) {
        return piece.getPieceCoordinates();
      }
    }
    return null;
  }

  public Map<Coordinate, GamePiece> getPieceCoordMap() {
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
