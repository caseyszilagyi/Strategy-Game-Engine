package ooga.model.game_engine;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;

public class GameEngine implements Engine {

  FrontEndExternalAPI viewController;

  public GameEngine(FrontEndExternalAPI newViewController){
    viewController = newViewController;
  }

  @Override
  public void checkForFinish() {

  }

  @Override
  public void saveCurrentState(String fileName) {

  }

  @Override
  public void addActiveUser(Player player) {

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
