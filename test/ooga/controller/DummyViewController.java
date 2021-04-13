package ooga.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ViewController;
import ooga.model.game_components.Coordinate;

public class DummyViewController implements FrontEndExternalAPI {

  private Set<Coordinate> allPossibleMoves;


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

  // turning this into coordinates for testing
  @Override
  public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {
    Set<Coordinate> moves = new HashSet<>();
    for (Iterator<Pair<Integer, Integer>> it = possibleMoves; it.hasNext(); ) {
      Pair p = it.next();
      moves.add(makeCoordinates((int) p.getKey(), (int) p.getValue()));
    }
    allPossibleMoves = moves;
  }

  // for getting for testing
  public Set<Coordinate> getAllPossibleMoves(){
    return allPossibleMoves;
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }

  @Override
  public void gameEnd(String playerName) {

  }

  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {

  }
}
