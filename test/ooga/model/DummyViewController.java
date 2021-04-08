package ooga.model;

import java.util.Map;
import javafx.util.Pair;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ViewController;

public class DummyViewController implements FrontEndExternalAPI {

  @Override
  public void setModelController(BackEndExternalAPI modelController) {

  }

  @Override
  public void setBoardDimensions(int width, int height) {

  }

  @Override
  public void setBoardSpace(int row, int column, String identifier) {

  }

  @Override
  public void clearBoardSpace(int row, int column) {

  }

  @Override
  public void setInitialPieces(Map<String, String> friendlyPieces,
      Map<String, String> opponentPieces) {

  }

  @Override
  public Iterable<Pair<Integer, Integer>> giveAllPossibleMoves() {
    return null;
  }

  @Override
  public void gameWin(String playerName) {

  }

  @Override
  public void gameLose(String playerName) {

  }

  @Override
  public void setPlayer(int playerNumber, String playerName) {

  }

  @Override
  public Iterable<String> givePieceChangeOptions() {
    return null;
  }
}
