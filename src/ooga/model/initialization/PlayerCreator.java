package ooga.model.initialization;

import java.io.File;
import ooga.model.components.player.Player;
import ooga.model.storage.PlayerFileSaver;

public class PlayerCreator extends Creator{

  private PlayerFileSaver playerFileReader;
  private final String PLAYER_FILE_PATH = "data/player/";
  private final String EXTENSION = ".xml";
  private final String DEFAULT_PLAYER_FILE_NAME = "DefaultPlayer";
  private final String DEFAULT_LAST_NAME = "Player";

  public PlayerCreator(){
     playerFileReader = new PlayerFileSaver();
  }

  public Player makePlayer(String playerName){
    if(!playerName.contains(" ")){
      playerName = playerName + " " + DEFAULT_LAST_NAME;
    }
    String playerFilePath = playerName.replaceAll("\\s+","");
    File playerFile = new File(PLAYER_FILE_PATH + playerFilePath + EXTENSION);
    if(!playerFile.exists()){
      Player defaultPlayer = playerFileReader.makePlayerObject(DEFAULT_PLAYER_FILE_NAME);
      defaultPlayer.setName(playerName.split(" "));
      return defaultPlayer;
    }
    return playerFileReader.makePlayerObject(playerFilePath);
  }
}
