package ooga.model.components.player;

/**
 * A record corresponding to a certain game
 *
 * @author Casey Szilagyi
 */
public class Record {

  private String gameName;
  private int wins;
  private int losses;
  private int draws;

  /**
   * Empty constructor for JACKSON mapping
   */
  public Record(){};

  /**
   * Constructs a record for a specific game
   * @param gameName The name of the game
   * @param wins The number of wins
   * @param losses The number of losses
   * @param draws The number of draws
   */
  public Record(String gameName, int wins, int losses, int draws) {
    this.gameName = gameName;
    this.wins = wins;
    this.losses = losses;
    this.draws = draws;
  }

  /**
   * Gets the name of the game that this record corresponds to
   * @return The name of the game
   */
  public String getGameName() {
    return gameName;
  }

  /**
   * Sets the name of the game that this record corresponds to
   * @param gameName name of the game
   */
  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  /**
   * Gets the number of wins the player has in this game
   * @return The number of wins
   */
  public int getWins() {
    return wins;
  }

  /**
   * Sets the number of wins that the player has in this game
   * @param wins The number of wins
   */
  public void setWins(int wins) {
    this.wins = wins;
  }

  /**
   * Gets the number of losses that the player has in this game
   * @return The number of losses
   */
  public int getLosses() {
    return losses;
  }

  /**
   * Sets the number of losses that the player has in this game
   * @param losses The number of losses
   */
  public void setLosses(int losses) {
    this.losses = losses;
  }

  /**
   * Gets the number of draws that the player has in this game
   * @return The number of draws
   */
  public int getDraws() {
    return draws;
  }

  /**
   * Sets the number of draws that the player has in this game
   * @param draws The number of losses
   */
  public void setDraws(int draws) {
    this.draws = draws;
  }

  /**
   * Adds a win
   */
  public void addWin(){ wins++; }

  /**
   * Adds a loss
   */
  public void addLoss(){ losses++; }

  /**
   * Adds a draw
   */
  public void addDraw(){ draws++; }
}
