package ooga.view;

import java.util.Iterator;
import javafx.util.Pair;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.board.BoardScene;

/**
 * The controller class for any {@link BoardScene}. This class implements
 * {@link FrontEndExternalAPI} to interact with the game board and its pieces.
 *
 * @author Yi Chen
 */
@Deprecated
public class GameBoardViewController implements FrontEndExternalAPI {



  @Override
  public void setModelController(BackEndExternalAPI modelController) {

  }

  @Override
  public void setBoardDimensions(int width, int height) {

  }

  @Override
  public void setBoardSpace(int row, int column, String identifier, String teamName) {

  }

  @Override
  public void movePiece(int startX, int startY, int endX, int endY) {

  }

  @Override
  public void removePiece(int row, int column) {

  }

  @Override
  public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {

  }

  @Override
  public void gameWin(String playerName) {

  }

  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {

  }
}
