package ooga.model.game_initialization;

import ooga.model.game_engine.Engine;

/**
 * This interface will be used to parse JSON files for all types of games
 */
public interface FileParser {

    /**
     * Creates an instance of the specific game type
     * @param gameName The name of the game
     * @return The engine that runs that game type
     */
    public String initializeGame(String gameName);

    /**
     * Sets the initial state of the board
     * @param boardFileName The file name that contains the board
     */
    public void setBoardState(String boardFileName);

    /**
     * Sets the rules that the user will play with
     * @param rulesFileName The file name that contains the rules of the game
     */
    public void setGameRules(String rulesFileName);

    /**
     * Sets a player that is playing the game
     * @param playerPosition A number associated with the player
     * @param playerFileName The string associated with the player name
     */
    public void setPlayer(int playerPosition, String playerFileName);


}
