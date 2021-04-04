package ooga.model.game_components.move_types;

import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.move_types.move_restrictions.GeneralRestriction;

/**
 * This class is used to represent a singular move that a piece can do. It needs the board,
 * as well as sets of opponent/friendly pieces in order to determine whether moves are valid
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
   * The constructor takes the parameters of the move. This includes the change in position of
   * the move, as well as the infromation about whether this move can take a piece
   * @param parameters The map of parameters
   */
  public PieceMovement(Map<String, String> parameters){
    changeX = Integer.parseInt(parameters.get("changeX"));
    changeY = Integer.parseInt(parameters.get("changeY"));
    mustTake = Boolean.parseBoolean(parameters.get("mustTake"));
    if(mustTake){
      takeX = Integer.parseInt(parameters.get("takeX"));
      takeY = Integer.parseInt(parameters.get("takeY"));
    }
  }

  /**
   * Can be called on subclasses to determine the possible moves using the methods in
   * this class. This must be implemented differently for each subclass so therefore
   * it is abstract
   * @param coordinates The coordinates of  the piece that this move is acting on
   * @param board The board that this piece is on
   * @return A list of all the coordinates of the possible move locations
   */
  public abstract List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board);

  public void setDummyBoard(GamePiece[][] board){
    dummyBoard = board;
  }

  /**
   * Checks if a move is in bounds. The >= is due to the fact that the coordinate system
   * starts at 0. So if we have an 8x8 board, the possible coordinates range from 0-7, making
   * 8 out of bounds
   * @param coordinates The coordinates of the piece
   * @return A boolean representing if the move is in bounds or not
   */
  protected boolean checkIfMoveInBounds(Coordinate coordinates){
    if(coordinates.getX() + changeX >= dummyBoard[0].length ||
        coordinates.getX() + changeX < 0  ||
        coordinates.getY() + changeY >= dummyBoard.length ||
        coordinates.getY() + changeY < 0){
      return false;
    }
    return true;
  }

  /**
   * Checks if a piece is in the take position, if this move type takes pieces. If not,
   * automatically returns true
   * @param coordinates The coordinates of the piece that this movement belongs to
   * @return The boolean representing whether it has pieces to take
   */
  protected boolean checkIfPieceInTakePosition(Coordinate coordinates){
    if(mustTake){
      if(!checkIfPieceOnBoard(coordinates.getX(), coordinates.getY())){
        return false;
      }
    }
    return true;
  }

  private boolean checkIfPieceOnBoard(int xPos, int yPos){
    if(dummyBoard[yPos][xPos] != null){
      return true;
    }
    return false;
  }


  //getters

  protected int getChangeX() {
    return changeX;
  }

  protected int getChangeY() {
    return changeY;
  }

  protected boolean isMustTake() {
    return mustTake;
  }

  protected Integer getTakeX() {
    return takeX;
  }

  protected Integer getTakeY() {
    return takeY;
  }


}
