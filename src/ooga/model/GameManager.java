package ooga.model;

import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.GameCreator;

public class GameManager implements Manager{

  private Creator gameCreator = new GameCreator();

  @Override
  public void initializeGame(String gameName) {
    gameCreator.initializeGame(gameName);
  }

  @Override
  public void setBoardState(String boardFileName) {
    gameCreator.setBoardState(boardFileName);
  }

  @Override
  public void setGameRules(String rulesFileName) {
    gameCreator.setGameRules(rulesFileName);
  }

  @Override
  public void setPlayer(int playerPosition, String playerFileName) {
    gameCreator.setPlayer(playerPosition, playerFileName);
  }

}
