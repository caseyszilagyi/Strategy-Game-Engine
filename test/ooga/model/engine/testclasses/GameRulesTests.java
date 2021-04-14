package ooga.model.engine.testclasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.components.GameRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesTests {


  @Test
  void testBasicGameRulesCreation(){
    GameRules gameRules = new GameRules("Chess");
    assertNotNull(gameRules);
  }

  @Test
  void testTurnConditionString(){
    GameRules gameRules = new GameRules("Chess");
    List<String> turnConditionList = gameRules.getTurnConditionsAsStringList();
    assertEquals(1, turnConditionList.size());
    assertEquals("Force", turnConditionList.get(0));
  }

  @Test
  void testIsTurnOverWithForce(){
    GameRules gameRules = new GameRules("Chess");
    assertTrue(gameRules.checkForNextTurn());
  }

  @Test
  void testIsTurnOverWithConstant(){
    GameRules gameRules = new GameRules("testConstant");
    assertFalse(gameRules.checkForNextTurn());
  }

  @Test
  void testMultipleForcesTurnCondtions(){
    GameRules gameRules = new GameRules("testMultipleForce");
    assertTrue(gameRules.checkForNextTurn());
  }

  @Test
  void testConstantAndForceTurnCondtions(){
    GameRules gameRules = new GameRules("testConstantAndForce");
    assertFalse(gameRules.checkForNextTurn());
  }
}
