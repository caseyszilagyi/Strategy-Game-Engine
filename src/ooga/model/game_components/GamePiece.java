package ooga.model.game_components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import ooga.controller.FrontEndExternalAPI;
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
  private String pieceName;
  private FrontEndExternalAPI viewController;

  //These two need to be added to constructor in boardCreator class when making the board,
  // right now just being assigned. dummyBoard needs to be integrated with actual Board classs
  private String pieceTeam;

  // used so that when coordinates are given for a move, we know which movement object they correspond
  // to
  private Map<Coordinate, PieceMovement> legalMovementMap = new HashMap<>();

  /**
   * Constructor that takes the coordinates, because every piece needs it's coordinates to know how
   * it can move
   *
   */
  public GamePiece(Coordinate pieceCoordinates, String pieceName, FrontEndExternalAPI viewController, GameBoard gameBoard) {
    this.pieceCoordinates = pieceCoordinates;
    this.pieceName = pieceName;
    this.viewController = viewController;
    this.gameBoard = gameBoard;
  }

  public String getPieceName() {
    return pieceName;
  }

  /**
   * Returns the piece's Coordinate
   * @return a Coordinate which can be used to map this GamePiece on the GameBoard
   */
  public Coordinate getPieceCoordinates(){
    return pieceCoordinates;
  }

  /**
   * Sets this piece's Coordinates to the new, given Coordinates
   * @param newCoordinates the new Coordinates for this piece to be moved to
   */
  public void setPieceCoordinates(Coordinate newCoordinates){
    pieceCoordinates = newCoordinates;
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
   * Gets a list of all the coordinates that represent legal moves. Also creates the
   * legalMovementMap used to retrieve the correct movement object when a set of coordinates is used
   * to move a piece
   *
   * @return A list of the coordinates of the legal moves
   */
  public void determineAllLegalMoves() {
    Set<Coordinate> possibleMoveLocations = new HashSet<>();
    for (PieceMovement move : allPossibleMoves) {
      List<Coordinate> currentPossibilities = move
          .getAllPossibleMoves(pieceCoordinates, gameBoard, pieceTeam);
      possibleMoveLocations
          .addAll(currentPossibilities);
      for (Coordinate coord : currentPossibilities) {
        legalMovementMap.put(coord, move);
      }
    }
    passLegalMoves(possibleMoveLocations);
  }

  private void passLegalMoves(Set<Coordinate> possibleMoveLocations){
    Set<Pair<Integer, Integer>> coordPairs = new HashSet<>();
    for(Coordinate coords: possibleMoveLocations){
      coordPairs.add(new Pair(coords.getX(), coords.getY()));
    }
    viewController.giveAllPossibleMoves(coordPairs.iterator());
  }

  /**
   * When given the final coordinates of a move, this move is executed
   *
   * @param coordinates The final coordinates of the move
   */
  public void executeMove(Coordinate coordinates) {
    PieceMovement correspondingMove = legalMovementMap.get(coordinates);
    correspondingMove.executeMove(coordinates);
  }

  /**
   * Sets the team that a piece is a part of
   *
   * @param team The name of the team (or name of the player)
   */
  public void setPieceTeam(String team) {
    pieceTeam = team;
  }

  /**
   * Gets the team that a piece is a part of
   *
   * @return the name of the team (or name of the player)
   */
  public String getPieceTeam() {
    return pieceTeam;
  }
}

