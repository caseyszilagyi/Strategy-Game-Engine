package ooga.model.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.model.components.Player;
import ooga.model.components.Record;
import org.junit.jupiter.api.Test;

public class FileSaverTest {

  private PlayerFileSaver playerFileSaver = new PlayerFileSaver();

  @Test
  void testReadInPlayer(){
    Player player = playerFileSaver.makePlayerObject("Casey");
    assertEquals("Casey", player.getFirstName());
    assertEquals("Szilagyi", player.getLastName());
    Record chessRecord = player.getRecords()[0];
    assertEquals("chess", chessRecord.getGameName());
    assertEquals(0, chessRecord.getWins());
    assertEquals(1, chessRecord.getLosses());
    assertEquals(0, chessRecord.getDraws());
  }

  @Test
  void testSavePlayer(){
    Record[] records = new Record[2];
    records[0] = makeRecord("chess", 1, 3, 4);
    records[1] = makeRecord("checkers", 3, 4, 5);
    Player player = new Player("dummy", "person", records);
    playerFileSaver.storePlayerFile(player);
  }



  private Record makeRecord(String gameName, int wins, int losses, int draws){
    return new Record(gameName, wins, losses, draws);
  }

}
