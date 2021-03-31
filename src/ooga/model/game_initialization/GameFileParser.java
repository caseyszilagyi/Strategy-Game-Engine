package ooga.model.game_initialization;

import ooga.model.game_engine.Engine;

/**
 * I think this will need to be specific for each game type because the files will be parsed
 * differently. Also need to determine what data format the information will be passed
 * down in before loading it into classes
 */
public class GameFileParser implements FileParser{

  @Override
  public String initializeGame(String gameName) {
    return null;
  }

  @Override
  public void setBoardState(String boardFileName) {

  }

  @Override
  public void setGameRules(String rulesFileName) {

  }

  @Override
  public void setPlayer(int playerPosition, String playerFileName) {

  }
}
