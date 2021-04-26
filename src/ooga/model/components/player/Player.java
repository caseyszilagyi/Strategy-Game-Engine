package ooga.model.components.player;

import ooga.exceptions.PlayerFileException;

/**
 * Loaded in from an XML file by the {@link ooga.model.storage.PlayerFileSaver}. Can also load in
 * the default player and assign a new name and save that player
 *
 * @author Casey Szilagyi
 */
public class Player {

  private String fullName;
  private String firstName;
  private String lastName;
  private Record[] records;

  /**
   * Empty constructor for JACKSON parsing
   */
  public Player() {
  }

  ;

  /**
   * Single name constructor
   *
   * @param name The name of the player
   */
  public Player(String name) {
    this.fullName = name;
  }

  /**
   * Gives the player a first and last name, as well as games and records for those games
   *
   * @param firstName The first name
   * @param lastName  The last name
   * @param records   The records for each game
   */
  public Player(String firstName, String lastName, Record[] records) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.records = records;
    this.fullName = firstName + " " + lastName;
  }

  /**
   * Gets a record for a certain game
   *
   * @param gameName The name of the game
   * @return The record
   */
  public Record getRecord(String gameName) {
    for (Record record : records) {
      if (record.getGameName().equals(gameName)) {
        return record;
      }
    }
    throw new PlayerFileException("noSuchGame");
  }

  /**
   * Gets all of the game records
   *
   * @return The records
   */
  public Record[] getRecords() {
    return records;
  }

  /**
   * Setts all of the game records
   *
   * @param records the records
   */
  public void setRecords(Record[] records) {
    this.records = records;
  }

  /**
   * Gets the first name of the player
   *
   * @return The first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the player
   *
   * @param firstName The first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the player's last name
   *
   * @return The last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the player's last name
   *
   * @param lastName The player's last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the player's full name
   *
   * @return The full name of the player
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Sets the player's name
   *
   * @param name Can be a single element, or 2 strings representing the first and last name
   */
  public void setName(String... name) {
    firstName = name[0];
    fullName = firstName;
    if (name.length > 1) {
      lastName = name[1];
      fullName = fullName + " " + lastName;
    }
  }


  /**
   * Checks if the players have the same name
   *
   * @param obj The other player object
   * @return True if they are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    return obj.toString().equals(fullName);
  }

  /**
   * Gets the players name in string form
   *
   * @return The player's full name
   */
  @Override
  public String toString() {
    return getFullName();
  }

  /**
   * Overridden to compare players
   *
   * @return The hash code
   */
  @Override
  public int hashCode() {
    int result = 7;
    for (char c : fullName.toCharArray()) {
      result = result * 43 + c;
    }
    return result;
  }

}
