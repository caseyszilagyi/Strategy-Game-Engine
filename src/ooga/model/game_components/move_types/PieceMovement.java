package ooga.model.game_components.move_types;

import java.util.List;
import java.util.Map;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.move_types.move_restrictions.GeneralRestriction;

/**
 * This class is used to represent a singular move that a piece can do. It needs the board, as well
 * as sets of opponent/friendly pieces in order to determine whether moves are valid
 *
 * @Casey Szilagyi
 */
public abstract class PieceMovement {

  // A list of restrictions that the subclass has to check for before declaring a move valid
  private List<GeneralRestriction> restrictions;
  private GamePiece[][] dummyBoard;

  private int changeX;
  private int changeY;
  private boolean mustTake;
  private int takeX;
  private int takeY;

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters The map of parameters
   */
  public PieceMovement(Map<String, String> parameters) {
    changeX = Integer.parseInt(parameters.get("changeX"));
    changeY = Integer.parseInt(parameters.get("changeY"));
    mustTake = Boolean.parseBoolean(parameters.get("mustTake"));
    if (mustTake) {
      takeX = Integer.parseInt(parameters.get("takeX"));
      takeY = Integer.parseInt(parameters.get("takeY"));
    }
  }

  /**
   * Can be called on subclasses to determine the possible moves using the methods in this class.
   * This must be implemented differently for each subclass so therefore it is abstract
   *
   * @param coordinates The coordinates of  the piece that this move is acting on
   * @param board       The board that this piece is on
   * @return A list of all the coordinates of the possible move locations
   */
  public abstract List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board,
      String pieceTeam);

  public void setDummyBoard(GamePiece[][] board) {
    dummyBoard = board;
  }

  /**
   * Checks if a move is in bounds. The >= is due to the fact that the coordinate system starts at
   * 0. So if we have an 8x8 board, the possible coordinates range from 0-7, making 8 out of bounds
   *
   * @param coordinates The coordinates of the piece
   * @return A boolean representing if the move is in bounds or not
   */
  protected boolean checkIfMoveInBounds(Coordinate coordinates) {
    if (coordinates.getX() + changeX >= dummyBoard[0].length ||
        coordinates.getX() + changeX < 0 ||
        coordinates.getY() + changeY >= dummyBoard.length ||
        coordinates.getY() + changeY < 0) {
      return false;
    }
    return true;
  }

  /**
   * Checks if a piece is in the take position, if this move type takes pieces. If there is, then we
   * also need to see if the piece taken is where the piece lands, and if it isn't then we need to
   * make sure that the piece landing spot is empty.
   *
   * @param coordinates The coordinates of the piece that this movement belongs to
   * @param teamName    the name of the team that this piece corresponds to
   * @return The boolean representing whether it has pieces to take
   */
  protected boolean checkEnemyPieceLocationConditions(Coordinate coordinates, String teamName) {
    if (mustTake) {
      //checks to see if piece is where it needs to be in order for it to be taken. If not, move invalid
      if (!checkIfOpponentPieceInLocation(coordinates.getX() + changeX + takeX,
          coordinates.getY() + changeY + takeY, teamName)) {
        return false;
      }
      // checks to make sure there is an empty space where the piece lands, if the take location
      // is different from the landing location of the piece. If no empty space, move invalid
      if (takeX != 0 || takeY != 0) {
        if (checkIfOpponentPieceInLocation(coordinates.getX() + changeX, coordinates.getY() + changeY,
            teamName)) {
          return false;
        }
      }
      // if piece in take location and not in landing location
      return true;
    }

    // if piece can't take, need to make sure landing spot doesn't have opponent's piece
    if (checkIfOpponentPieceInLocation(coordinates.getX() + changeX, coordinates.getY() + changeY,
        teamName)) {
      return false;
    }

    return true;
  }

  /**
   * Checks to make sure a friendly piece isn't in the location where the movement object is
   * supposed to be going
   *
   * @param coordinates The starting coordinates of the piece
   * @param teamName    The name of the team that the piece is on
   * @return True if there is no piece in the landing location, false otherwise
   */
  protected boolean checkThatNoFriendlyPieceInMoveDestination(Coordinate coordinates,
      String teamName) {
    if (checkIfFriendlyPieceInLocation(coordinates.getX() + changeX, coordinates.getY() + changeY,
        teamName)) {
      return false;
    }
    return true;
  }

  // Checks if an friendly piece is on the board in this location
  private boolean checkIfFriendlyPieceInLocation(int xPos, int yPos, String teamName) {
    if (checkIfPieceInSpace(xPos, yPos) && dummyBoard[yPos][xPos].getPieceTeam().equals(teamName)) {
      return true;
    }
    return false;
  }

  // Checks if an opponent piece is on the board in this location, used only if this is a take move
  private boolean checkIfOpponentPieceInLocation(int xPos, int yPos, String teamName) {
    if (checkIfPieceInSpace(xPos, yPos) && !dummyBoard[yPos][xPos].getPieceTeam().equals(teamName)) {
      return true;
    }
    return false;
  }

  private boolean checkIfPieceInSpace(int xPos, int yPos) {
    if (dummyBoard[yPos][xPos] == null) {
      return false;
    }
    return true;
  }

  /**
   * The change in x position of the piece for this move. Could be a finite change, or an infinite
   * change, depending on the subclass that implements
   *
   * @return The change in x position
   */
  protected int getChangeX() {
    return changeX;
  }

  /**
   * The change in y position of the piece for this move. Could be a finite change, or an infinite
   * change, depending on the subclass that implements
   *
   * @return The change in y position
   */
  protected int getChangeY() {
    return changeY;
  }

  /**
   * Tells whether this movement must take another piece when this movement object is executed
   *
   * @return True if this move must take another piece, false if not
   */
  protected boolean isMustTake() {
    return mustTake;
  }

  /**
   * If the piece corresponding to this object needs to be moved, it might need to take a piece.
   * This method gives the x position oof the piece that needs to be taken relative to the final
   * position of this piece
   *
   * @return The x position relative to the final position of the piece being moved
   */
  protected int getTakeX() {
    return takeX;
  }

  /**
   * If the piece corresponding to this object needs to be moved, it might need to take a piece.
   * This method gives the y position oof the piece that needs to be taken relative to the final
   * position of this piece
   *
   * @return The y position relative to the final position of the piece being moved
   */
  protected int getTakeY() {
    return takeY;
  }


}
