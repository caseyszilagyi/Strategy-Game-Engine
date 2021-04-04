package ooga.model.game_components;

import java.util.ArrayList;
import java.util.List;
import ooga.model.game_components.move_types.PieceMovement;

/**
 * Represents a generic game piece. Has a location and an indicator representing whether
 * it exists on the board or not
 *
 *
 * Random notes:
 * Need to be able to give the piece a location and ask whether it can attack that location or not.
 * This is how check/checkmate will be determined
 */
public class GamePiece{

  private Coordinate pieceCoordinates;
  private GameBoard gameBoard;
  private List<PieceMovement> allPossibleMoves;

  public GamePiece(Coordinate coordinates){
    pieceCoordinates = coordinates;
  }

  public void setPossibleMoves(List<PieceMovement> allPossibleMoves){
    this.allPossibleMoves = allPossibleMoves;
  }

  public List<Coordinate> getAllPossibleMoves() {
    List<Coordinate> possibleMoveLocations = new ArrayList<Coordinate>();
    for(PieceMovement move: allPossibleMoves){
      possibleMoveLocations.addAll(move.getAllPossibleMoves(pieceCoordinates, gameBoard));
    }
    return possibleMoveLocations;
  }


  public double getXPosition(){
    return pieceCoordinates.getX();
  }

  public double getYPosition(){
    return pieceCoordinates.getY();
  }
}

