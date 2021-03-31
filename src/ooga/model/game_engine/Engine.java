package ooga.model.game_engine;

/**
 * Implemented by any type of game engine that is designed to run a specific type of program. The
 * game_engine is the core class that contains all of the references to other classes
 * that are meant to be used in order to give the game_engine its functionality
 */
public interface Engine {

  public void checkForFinish();

  public void updatePlayerInformation();

  public void saveCurrentState(String fileName);
}
