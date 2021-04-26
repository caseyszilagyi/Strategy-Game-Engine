package ooga.model.initialization;

import ooga.model.components.computer.AI;

/**
 * This class initializes the AI
 */
public class AICreator extends Creator {

  /**
   * Creates the AI
   *
   * @return Returns AI
   */
  public AI makeAI() {
    return new AI();
  }
}
