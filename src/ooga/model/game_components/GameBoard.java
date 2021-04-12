package ooga.model.game_components;

import java.util.HashMap;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;

/**
 * This is the representation of the board. It holds all of the GamePiece objects, and has the
 * coordinates corresponding to each of these objects
 */
public class GameBoard implements Board {

  private FrontEndExternalAPI viewController;
  private int width, height;
  private Map<Coordinate, GamePiece> pieceCoordMap = new HashMap<>();

  private GamePiece activePiece;

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
   * Determines all legal moves for a piece at a given coordinate. Automatically sets this piece as
   * the active piece when this is done.
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public void determineAllLegalMoves(int x, int y) {
    Coordinate current = makeCoordinates(x, y);
    pieceCoordMap.get(current).determineAllLegalMoves();
    activePiece = pieceCoordMap.get(current);
  }


  /**
   * Checks tho see if a piece is at a certain coordinate
   *
   * @param coordinate The coordinate object that may or may not correspond to a certain piece
   * @return True if there is a piece at the coordinate, false otherwise
   */
  public boolean isPieceAtCoordinate(Coordinate coordinate) {
    return (isCoordinateOnBoard(coordinate) && pieceCoordMap.keySet().contains(coordinate));
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
    if (coordinates.getX() >= width || coordinates.getY() >= height || coordinates
        .getX() < 0 || coordinates.getY() < 0) {
      return false;
    }
    return true;
  }


  /**
   * Checks if a friendly piece is on the board in this location
   *
   * @param coordinates The coordinate object corresponding to the location
   * @param teamName    The name of the team
   * @return True if there is a friendly piece there, false otherwise
   */
  public boolean checkIfFriendlyPieceInLocation(Coordinate coordinates, String teamName) {
    if (isPieceAtCoordinate(coordinates) && getPieceAtCoordinate(coordinates).getPieceTeam()
        .equals(teamName)) {
      return true;
    }
    return false;
  }

  /**
   * Checks if an enemy piece is on the board in this location
   *
   * @param coordinates The coordinate object corresponding to the location
   * @param teamName    The name of the team
   * @return True if there is a enemy piece there, false otherwise
   */
  public boolean checkIfOpponentPieceInLocation(Coordinate coordinates, String teamName) {
    if (isPieceAtCoordinate(coordinates) && !getPieceAtCoordinate(coordinates).getPieceTeam()
        .equals(teamName)) {
      return true;
    }
    return false;
  }


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


  // makes a set of coordinates, useful so that coordinate implementation is only present
  // in our gameboard
  private Coordinate makeCoordinates(int x, int y) {
    return new Coordinate(x, y);
  }


  // for testing
  public void printBoard() {
    System.out.println("");
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < width; y++) {
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
