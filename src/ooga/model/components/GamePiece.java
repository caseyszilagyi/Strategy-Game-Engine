package ooga.model.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.moves.PieceMovement;

/**
 * Represents a generic game piece. Has a location, the board it is on, and a list of possible
 * moves
 *
 * @author Casey Szilagyi
 */
public class GamePiece {

  private Coordinate pieceCoordinates;
  private String pieceName;

  private List<PieceMovement> allPossibleMoves;
  private Map<Coordinate, PieceMovement> legalMovementMap = new HashMap<>();
  private List<Coordinate> locationHistory = new ArrayList<>();

  // Needs to be added to constructor in boardCreator
  private String pieceTeam;

  /**
   * Makes the piece and passes all the information it needs to be able to move
   *
   * @param pieceCoordinates The coordinates of the piece, represented by a coordinate object
   * @param pieceName        The name of the piece
   * @param pieceTeam        The team that the piece is on
   */
  public GamePiece(Coordinate pieceCoordinates, String pieceName, String pieceTeam) {
    this.pieceCoordinates = pieceCoordinates;
    this.pieceName = pieceName;
    this.pieceTeam = pieceTeam;
    locationHistory.add(pieceCoordinates);
  }

  /**
   * Gets a list of all the coordinates that represent legal moves. Also creates the
   * legalMovementMap used to retrieve the correct movement object when a set of coordinates is used
   * to move a piece
   *
   * @return A list of the coordinates of the legal moves
   */
  public Set<Coordinate> determineAllLegalMoves() {
    Set<Coordinate> possibleMoveLocations = new HashSet<>();
    for (PieceMovement move : allPossibleMoves) {
      List<Coordinate> currentPossibilities = move
          .getAllPossibleMoves(pieceCoordinates, pieceTeam);
      possibleMoveLocations
          .addAll(currentPossibilities);
      for (Coordinate coord : currentPossibilities) {
        legalMovementMap.put(coord, move);
      }
    }
    return possibleMoveLocations;
  }

  public Set<Coordinate> determineAllPossibleRestrictionlessTakeMoves() {
    Set<Coordinate> possibleMoveLocations = new HashSet<>();
    for (PieceMovement move : allPossibleMoves) {
      List<Coordinate> currentPossibilities = move
          .getAllPossibleRestrictionlessTakeMoves(pieceCoordinates, pieceTeam);
      possibleMoveLocations
          .addAll(currentPossibilities);
    }
    return possibleMoveLocations;
  }

  /**
   * When given the final coordinates of a move, this move is executed
   *
   * @param finalCoordinates The final coordinates of the move
   */
  public void executeMove(Coordinate finalCoordinates) {
    locationHistory.add(finalCoordinates);
    PieceMovement correspondingMove = legalMovementMap.get(finalCoordinates);
    correspondingMove.executeMove(pieceCoordinates, finalCoordinates);
  }

  /**
   * Gets all the coordinates that the piece has visited, including the one the piece is currently
   * on
   *
   * @return A list of all the coordinates the piece has visited
   */
  public List<Coordinate> getLocationHistory() {
    return locationHistory;
  }

  /**
   * Returns whether the piece has moved or not
   *
   * @return True if it has, false if it hasn't
   */
  public boolean hasMoved() {
    return locationHistory.size() != 1;
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
   * Returns the piece's Coordinates
   *
   * @return A Coordinate which can be used to map this GamePiece on the GameBoard
   */
  public Coordinate getPieceCoordinates() {
    return pieceCoordinates;
  }

  /**
   * Sets this piece's Coordinates to the new, given Coordinates
   *
   * @param newCoordinates the new Coordinates for this piece to be moved to
   */
  public void setPieceCoordinates(Coordinate newCoordinates) {
    pieceCoordinates = newCoordinates;
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

  /**
   * Gets the name of the piece
   *
   * @return The name of the piece
   */
  public String getPieceName() {
    return pieceName;
  }


}

