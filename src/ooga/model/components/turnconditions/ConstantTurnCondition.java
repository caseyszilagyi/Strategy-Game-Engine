package ooga.model.components.turnconditions;

public class ConstantTurnCondition implements TurnCondition{

  @Override
  public boolean isTurnOver() {
    return false;
  }
}
