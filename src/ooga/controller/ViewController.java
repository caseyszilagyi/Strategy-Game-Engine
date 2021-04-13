package ooga.controller;

import java.util.Iterator;
import javafx.util.Pair;
import ooga.view.Display;
import ooga.view.GameDisplay;

/**
 * This is a concrete implementation of what is passed around in the back end to
 * make method calls to the front end.
 *
 * @author Casey Szilagyi
 */
public class ViewController implements FrontEndExternalAPI{

  private Display display;
  private BackEndExternalAPI modelController;

  public ViewController(){

  }

  @Override
  public void setModelController(BackEndExternalAPI newModelController) {
    display = new GameDisplay();
    modelController = newModelController;
    display.setModelController(modelController);
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
   * Sets a certain space on the board to a certain piece
   *
   * @param x The row of the space
   * @param y The column of the space
   * @param identifier The identifier of the piece
   * @param teamName The name of the team that the piece is playing on
   */
  @Override
  public void setBoardSpace(int x, int y, String identifier, String teamName) {

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

  }

  /**
   * Takes a piece off the board
   * @param x The x position of the piece to remove
   * @param y The y position of the piece to remove
   */
  @Override
  public void removePiece(int x, int y) {

  }

  /**
   * Gives all of the coordinates of the possible moves when a piece is clicked on
   *
   * @param possibleMoves An iterator of pair objects that contain the coordinates
   */
  @Override
  public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {

  }


  // Not implemented yet, everything above this has been implemented in front end

  @Override
  public void gameEnd(String playerName) {

  }

  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {

  }

}
