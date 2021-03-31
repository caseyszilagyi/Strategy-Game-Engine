package ooga.controller;

/**
 * Classes that implement this interface are meant to be passed around the front end in
 * order to call the appropriate methods on the back end
 *
 * @author Casey Szilagyi
 */
public interface BackEndExternalAPI {

  /**
   * Links this half of the controller to the other half that is passed around the back end
   * @param viewController The other half of the controller
   */
  public void setViewController(FrontEndExternalAPI viewController);


  /**
   * Sets the type of game that will be played
   * @param gameName The name of the game
   */
  public void setGameType(String gameName);


  /**
   * Sets the rules that the game will follow
   * @param rulesFileName The name of the file with all the rules
   */
  public void setGameRules(String rulesFileName);

  /**
   * Sets the language
   * @param language The language
   */
  public void setLanguage(String language);

  /**
   * Sets a player that is playing the game
   * @param playerPosition A number associated with the player
   * @param playerFileName The string associated with the player name
   */
  public void setPlayer(int playerPosition, String playerFileName);

  /**
   * Sets the initial state of the board
   * @param boardFileName The file name that contains the board
   */
  public void setBoardState(String boardFileName);

  /**
   * Saves the current state of the game in a file
   * @param fileName The name of the file to store the game information
   */
  public void saveCurrentBoardState(String fileName);

  /**
   * Saves the current rules of the game in a file
   * @param fileName The name of the file to save the rules in
   */
  public void saveCurrentRules(String fileName);
}
