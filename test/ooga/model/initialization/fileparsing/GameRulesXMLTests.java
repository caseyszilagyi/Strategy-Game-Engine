package ooga.model.initialization.fileparsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.components.GameRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesXMLTests {
  GameRules gameRules;

  @BeforeEach
  void testBasicGameRulesCreation(){
    gameRules = new GameRules("Chess");
    assertNotNull(gameRules);
  }

  @Test
  void testTurnConditionString(){
    List<String> turnConditionList = gameRules.getTurnConditionsAsStringList();
    assertEquals(1, turnConditionList.size());
    assertEquals("force", turnConditionList.get(0));
  }
}
