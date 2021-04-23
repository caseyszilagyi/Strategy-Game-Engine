package ooga.controller;

import java.util.Iterator;
import javafx.util.Pair;
import ooga.view.board.Board;

/**
 * This is a concrete implementation of what is passed around in the back end to
 * make method calls to the front end.
 *
 * @author Casey Szilagyi
 */
public class BoardController implements FrontEndExternalAPI {

  private Board board;
  private BackEndExternalAPI modelController;

  /**
   * Obtains a {@link Board} object for this class to control.
   * @param board a {@code Board} object
   */
  public void giveBoard(Board board) {
    this.board = board;
  }

  @Override
  public void setModelController(BackEndExternalAPI newModelController) {
    modelController = newModelController;
  }

  /**
   * Sets the dimensions of the board
   *
   * @param width The width of the board
   * @param height The height of the board
   */
  @Override
  public void setBoardDimensions(int width, int height) {

  }

  /**
   * Sets a certain space on the board to a certain piece. This removes any piece that
   * is already on that space.
   *
   * @param x The row of the space
   * @param y The column of the space
   * @param identifier The identifier of the piece
   * @param teamName The name of the team that the piece is playing on
   */
  // TODO: Rename files so that this method works
  @Override
  public void setBoardSpace(int x, int y, String identifier, String teamName) {
    if (!board.spaceIsEmpty(x, y)) {
      board.removePiece(x, y);
    }
    board.addPiece(x, y, teamName, identifier);
  }

  /**
   * Moves a piece's visual representation on the board. 0,0 is the upper left
   * @param startX The starting x coordinate of the piece
   * @param startY The starting y coordinate of the piece
   * @param endX The ending x coordinate of the piece
   * @param endY The ending y coordinate of the piece
   */
  @Override
  public void movePiece(int startX, int startY, int endX, int endY) {
    board.unhighlightAll();
    board.movePiece(startX, startY, endX, endY);
  }

  /**
   * Takes a piece off the board
   * @param x The x position of the piece to remove
   * @param y The y position of the piece to remove
   */
  @Override
  public void removePiece(int x, int y) {
    board.removePiece(x, y);
  }

  /**
   * Gives all of the coordinates of the possible moves when a piece is clicked on
   *
   * @param possibleMoves An iterator of pair objects that contain the coordinates
   */
  @Override
  public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {
    board.showPossibleMoves(possibleMoves);
  }

  /**
   * Gives the possible options that a piece can change to
   *
   * @param pieceChangeOptions An iterable of strings that correspond to piece names
   */
  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {
    Iterator iterator = pieceChangeOptions.iterator();
    while(iterator.hasNext()){
      System.out.println(iterator.next());
    }
  }


  // Not implemented yet, everything above this has been implemented in back end

  @Override
  public void gameEnd(String playerName) {
    System.out.println(playerName + "won!");
  }



}
