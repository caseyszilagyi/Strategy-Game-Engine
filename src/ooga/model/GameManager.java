package ooga.model;

import ooga.model.game_engine.Engine;
import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.GameCreator;

public class GameManager implements Manager{

  private Creator gameCreator = new GameCreator();
  private Engine gameEngine;

  @Override
  public void initializeGame(String gameName) {
    gameCreator = new GameCreator();
    gameCreator.initializeGame(gameName);
    gameEngine = gameCreator.getEngine();
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
