package ooga.model.storage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import ooga.model.components.Player;

public class PlayerFileSaver {

  private String playerFilePath;
  private String EXTENSION = ".xml";
  ObjectMapper mapper = new XmlMapper();

  public PlayerFileSaver(String playerFilePath){
    this.playerFilePath = playerFilePath;
  }

  public Player makePlayerObject(String playerName){
    Player player = null;
    try{
      InputStream inputStream = new FileInputStream(new File(playerFilePath + playerName + EXTENSION));
      TypeReference<Player> typeReference= new TypeReference<Player>() {};
      player = mapper.readValue(inputStream, typeReference);
      inputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return player;
  }

  public void storePlayerFile(Player player){
    try {
      mapper.writeValue(new File(playerFilePath + player.getFirstName() + player.getLastName() + EXTENSION),
          player);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
