package ooga.model.game_components;

import java.util.ArrayList;
import java.util.List;
import ooga.model.game_components.move_types.PieceMovement;

/**
 * Represents a generic game piece. Has a location, the board it is on, and a list of possible
 * moves
 *
 * @author Casey Szilagyi
 */
public class GamePiece {

  private Coordinate pieceCoordinates;
  private GameBoard gameBoard;
  private List<PieceMovement> allPossibleMoves;
  private String pieceType;

  //These two need to be added to constructor in boardCreator class when making the board,
  // right now just being assigned. dummyBoard needs to be integrated with actual Board classs
  private String pieceTeam;
  private GamePiece[][] dummyBoard;

  /**
   * Constructor that takes the coordinates, because every piece needs it's coordinates to know how
   * it can move
   *
   * @param coordinates The coordinates object representing the piece's coordinates
   */
  public GamePiece(Coordinate coordinates) {
    pieceCoordinates = coordinates;
  }

  /**
   * Sets the piece's possible moves
   *
   * @param allPossibleMoves A list of PieceMovement objects that represent the possible moves
   */
  public void setPossibleMoves(List<PieceMovement> allPossibleMoves) {
    this.allPossibleMoves = allPossibleMoves;
  }

  /**
   * Gets a list of all the coordinates that represent legal moves
   *
   * @return A list of the coordinates of the legal moves
   */
  public List<Coordinate> getAllLegalMoves() {
    List<Coordinate> possibleMoveLocations = new ArrayList<>();
    for (PieceMovement move : allPossibleMoves) {
      possibleMoveLocations.addAll(move.getAllPossibleMoves(pieceCoordinates, gameBoard, pieceTeam));
    }
    return possibleMoveLocations;
  }

  /**
   * Used for testing, sets a dummy board
   *
   * @param dummyBoard The dummy board
   */
  public void setDummyBoard(GamePiece[][] dummyBoard) {
    this.dummyBoard = dummyBoard;
    for (PieceMovement move : allPossibleMoves) {
      move.setDummyBoard(dummyBoard);
    }
  }

  /**
   * Sets the team that a piece is a part of
   * @param team The name of the team (or name of the player)
   */
  public void setPieceTeam(String team){
    pieceTeam = team;
  }

  /**
   * Gets the team that a piece is a part of
   * @return the name of the team (or name of the player)
   */
  public String getPieceTeam(){
    return pieceTeam;
  }
}

