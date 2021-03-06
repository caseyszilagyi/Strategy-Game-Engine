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
import ooga.exceptions.PlayerFileException;
import ooga.model.components.player.Player;

/**
 * Used to read in and save player files
 *
 * @author Casey Szilagyi
 */
public class PlayerFileSaver {

  private final String EXTENSION = ".xml";
  private final String PLAYER_FILE_PATH = "data/player/";
  private final String DEFAULT_PLAYER_FILE_NAME = "DefaultPlayer";
  private ObjectMapper mapper = new XmlMapper();

  /**
   * Makes a player object
   *
   * @param playerName The name of the player
   * @return The player object to make
   */
  public Player makePlayerObject(String playerName) {
    Player player = null;
    try {
      InputStream inputStream = new FileInputStream(
          new File(PLAYER_FILE_PATH + playerName + EXTENSION));
      TypeReference<Player> typeReference = new TypeReference<Player>() {
      };
      player = mapper.readValue(inputStream, typeReference);
      inputStream.close();
    } catch (FileNotFoundException e) {
      throw new PlayerFileException("NonExistentPlayerFile");
    } catch (JsonParseException e) {
      throw new PlayerFileException("InvalidPlayerFile");
    } catch (JsonMappingException e) {
      throw new PlayerFileException("InvalidPlayerFile");
    } catch (IOException e) {
      throw new PlayerFileException("BadIOPlayer");
    }
    return player;
  }

  /**
   * Stores the player object in a file, to be read in at a later time
   *
   * @param player The player object to store
   */
  public void storePlayerFile(Player player) {
    try {
      mapper.writeValue(
          new File(PLAYER_FILE_PATH + player.getFirstName() + player.getLastName() + EXTENSION),
          player);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
