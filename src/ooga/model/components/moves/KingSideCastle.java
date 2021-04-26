package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import org.jetbrains.annotations.NotNull;

/**
 * The piece movement that corresponds to the king side castle. Has to check that the king and rook
 * haven't moved, and that there are no pieces between them
 *
 * This class should be used via reflection, and should be defined in the GamePiece's .xml file in the
 * nested parameters:
 * <pre> {@code
 * <moves>
 *  <KingSideCastle>
 *  </KingSideCastle>
 * </moves>
 * }
 * </pre>
 *
 * @author Casey Szilagyi
 */
public class KingSideCastle extends FiniteSlide {

  private GamePiece king;
  private GamePiece rook;
  private GameBoard board;
  private String teamName;
  private List<Coordinate> possibleMoves;

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters         The map of parameters
   * @param direction          The multiplier used to change the direction that the piece uses
   * @param gameBoard          The board that the piece moves on
   * @param correspondingPiece The piece that this move corresponds to
   */
  public KingSideCastle(Map<String, String> parameters, int direction, GameBoard gameBoard,
      GamePiece correspondingPiece) {
    super(parameters, direction, gameBoard, correspondingPiece);
    king = correspondingPiece;
    teamName = king.getPieceTeam();
    board = gameBoard;
  }

  /**
   * Gets all the possible moves of the king side castle, which is a single move if the castle is
   * valid or 0 moves otherwise
   *
   * @param coordinates The coordinates of  the piece that this move is acting on (the king)
   * @param pieceTeam   The team that the piece is on
   * @return All the possible ending coordinates of the move, which is either a single value or
   * nothing for the castle
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, String pieceTeam) {
    int kingX = coordinates.getX();
    int kingY = coordinates.getY();
    rook = board.getPieceAtCoordinate(kingX + 3, kingY);
    return getLegalCastleMoves(kingX, kingY);
  }

  // Returns empty list if castle is illegal, a 1 element list if it is legal
  @NotNull
  private List<Coordinate> getLegalCastleMoves(int kingX, int kingY) {
    List<Coordinate> moves = new ArrayList<>();
    if (checkCastlingConditions(kingX, kingY)) {
      moves.add(new Coordinate(kingX + 2, kingY));
    }
    return moves;
  }

  // Checks that we have the right pieces, that they haven't moved, and that there are the appropriate empty spaces
  private boolean checkCastlingConditions(int kingX, int kingY) {
    return checkPieceLocations(kingX, kingY) && checkMoveThroughCheck(kingX, kingY);
  }

  // Checks that the king doesn't move through check
  private boolean checkMoveThroughCheck(int kingX, int kingY){
    return checkRestrictions(new Coordinate(kingX, kingY)) &&
        checkRestrictions(new Coordinate(kingX +1, kingY)) &&
        checkRestrictions(new Coordinate(kingX+2, kingY));
  }

  // Checks that pieces are in the correct locations, and that there are empty spaces
  private boolean checkPieceLocations(int kingX, int kingY) {
    return !king.hasMoved() && rook != null && !rook.hasMoved() &&
        !board.isPieceAtCoordinate(kingX + 1, kingY) && !board
        .isPieceAtCoordinate(kingX + 2, kingY);
  }

  /**
   * Executes the castle by moving the king and rook
   *
   * @param startingCoordinates The coordinates that the king starts at
   * @param endingCoordinates   The ending coordinates of the king
   */
  @Override
  public void executeMove(Coordinate startingCoordinates, Coordinate endingCoordinates) {
    board.movePiece(startingCoordinates, endingCoordinates);
    board.movePiece(new Coordinate(endingCoordinates, 1, 0),
        new Coordinate(endingCoordinates, -1, 0));
  }
}
