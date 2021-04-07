package ooga.model.game_engine;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;

public class GameEngine implements Engine {

  FrontEndExternalAPI viewController;
  List<Player> activePlayers;

  public GameEngine(FrontEndExternalAPI newViewController){
    viewController = newViewController;
    activePlayers = new ArrayList<Player>();
  }

  @Override
  public void checkForFinish() {

  }

  @Override
  public void saveCurrentState(String fileName) {

  }

  @Override
  public void addActiveUser(Player player) {
    activePlayers.add(player);
  }

  @Override
  public void checkForNextTurn() {

  }

  @Override
  public void startPlayerTimer(Player player) {

  }

  @Override
  public void stopPlayerTimer(Player player) {

  }

  @Override
  public void setRules(GameRules rules) {

  }

  @Override
  public void setBoard(GameBoard board) {

  }

}
