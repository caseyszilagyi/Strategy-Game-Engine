package ooga.model.game_initialization.file_parsing;

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
    public String parseBoard(String boardFileName);

    /**
     * Sets the rules that the user will play with
     * @param rulesFileName The file name that contains the rules of the game
     */
    public String parseGameRules(String rulesFileName);

    /**
     * Parses a player's file. Returns default values if the player doesn't exist
     * @param playerFileName The string associated with the player name
     */
    public String parsePlayerInfo(String playerFileName);


}
