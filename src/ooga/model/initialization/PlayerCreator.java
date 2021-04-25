package ooga.model.initialization;

import java.io.File;
import ooga.model.components.Player;
import ooga.model.storage.PlayerFileSaver;

public class PlayerCreator extends Creator{

  private PlayerFileSaver playerFileReader;
  private final String PLAYER_FILE_PATH = "data/player/";
  private final String EXTENSION = ".xml";
  private final String DEFAULT_PLAYER_FILE_NAME = "DefaultPlayer";

  public PlayerCreator(){
     playerFileReader = new PlayerFileSaver(PLAYER_FILE_PATH);
  }

  public Player makePlayer(String playerName){
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
