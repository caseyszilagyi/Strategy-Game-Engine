package ooga.controller;

import java.util.Map;
import javafx.util.Pair;
import ooga.view.Display;
import ooga.view.GameDisplay;

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
