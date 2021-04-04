package ooga.model.game_components.move_types;

import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.move_types.move_restrictions.GeneralRestriction;

public abstract class PieceMovement {

  // A list of restrictions that the subclass has to check for before declaring a move valid
  private List<GeneralRestriction> restrictions;
  private GamePiece[][] dummyBoard;

  private int changeX;
  private int changeY;
  private boolean mustTake;
  private Integer takeX;
  private Integer takeY;

  public PieceMovement(Map<String, String> parameters){
    changeX = Integer.parseInt(parameters.get("changeX"));
    changeY = Integer.parseInt(parameters.get("changeY"));
    mustTake = Boolean.parseBoolean(parameters.get("mustTake"));
    if(mustTake){
      takeX = Integer.parseInt(parameters.get("takeX"));
      takeY = Integer.parseInt(parameters.get("takeY"));
    }
  }

  public abstract List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board);

  public void setDummyBoard(GamePiece[][] board){
    dummyBoard = board;
  }

  /**
   * Checks if a move is in bounds
   * @param coordinates The coordinates of the piece
   * @return A boolean representing if the move is in bounds or not
   */
  protected boolean checkIfMoveInBounds(Coordinate coordinates){
    if(coordinates.getX() + changeX > dummyBoard[0].length ||
        coordinates.getX() + changeX < 0  ||
        coordinates.getY() + changeY > dummyBoard.length ||
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

  public Integer getTakeX() {
    return takeX;
  }

  public Integer getTakeY() {
    return takeY;
  }


}
