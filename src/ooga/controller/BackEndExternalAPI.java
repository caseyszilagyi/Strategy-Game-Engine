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
}
