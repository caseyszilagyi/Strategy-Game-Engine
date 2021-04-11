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
  public void setBoardSpace(int row, int column, String identifier, String teamName) {

  }

  @Override
  public void movePiece(int startX, int startY, int endX, int endY) {

  }

  @Override
  public void removePiece(int row, int column) {

  }

  @Override
  public void giveAllPossibleMoves(Iterable<Pair<Integer, Integer>> possibleMoves) {

  }

  @Override
  public void gameEnd(String playerName) {

  }

  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {

  }
}
