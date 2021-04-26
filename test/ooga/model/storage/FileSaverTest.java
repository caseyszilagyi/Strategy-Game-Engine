package ooga.model.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import ooga.model.components.player.Player;
import ooga.model.components.player.Record;
import ooga.model.initialization.PlayerCreator;
import org.junit.jupiter.api.Test;

public class FileSaverTest {
  private final String PLAYER_FILE_PATH = "data/player/";
  private final String EXTENSION = ".xml";

  private PlayerFileSaver playerFileSaver = new PlayerFileSaver();
  private PlayerCreator creator = new PlayerCreator();

  @Test
  void testReadInPlayer(){
    Player player = playerFileSaver.makePlayerObject("dummyperson");
    assertEquals("dummy", player.getFirstName());
    assertEquals("person", player.getLastName());
    Record chessRecord = player.getRecords()[0];
    assertEquals("chess", chessRecord.getGameName());
    assertEquals(1, chessRecord.getWins());
    assertEquals(3, chessRecord.getLosses());
    assertEquals(4, chessRecord.getDraws());
  }

  @Test
  void testSavePlayer(){
    Record[] records = new Record[2];
    records[0] = makeRecord("chess", 1, 3, 4);
    records[1] = makeRecord("checkers", 3, 4, 5);
    Player player = new Player("Dummy", "Dummy", records);
    playerFileSaver.storePlayerFile(player);
  }

  @Test
  void testMakeAndStoreNewPlayerWithPlayerCreator(){
    File playerFile = new File(PLAYER_FILE_PATH + "DummyDummy" + EXTENSION);
    playerFile.delete();
    Player test = creator.makePlayer("Dummy Dummy");
    assertEquals("Dummy", test.getFirstName());
    assertEquals("Dummy", test.getLastName());
    assertEquals(3, test.getRecords().length);
    assertEquals(0, test.getRecords()[0].getDraws());
    assertEquals(0, test.getRecords()[0].getLosses());
    assertEquals(0, test.getRecords()[0].getWins());
    test.getRecords()[0].setWins(1);
    playerFileSaver.storePlayerFile(test);
    test = creator.makePlayer("Dummy Dummy");
    assertEquals(1, test.getRecords()[0].getWins());
    playerFile = new File(PLAYER_FILE_PATH + "DummyDummy" + EXTENSION);
    playerFile.delete();
  }

  @Test
  void testMakeAndStoreExistingPlayerWithPlayerCreator(){
    Player test = creator.makePlayer("dummy person");
    assertEquals("dummy", test.getFirstName());
    assertEquals("person", test.getLastName());
    assertEquals(3, test.getRecords().length);
    assertEquals(4, test.getRecords()[0].getDraws());
    assertEquals(3, test.getRecords()[0].getLosses());
    assertEquals(1, test.getRecords()[0].getWins());
  }



  private Record makeRecord(String gameName, int wins, int losses, int draws){
    return new Record(gameName, wins, losses, draws);
  }

}
