package ooga.exceptions;

/**
 * Used to make exceptions relating to loading in the player files
 *
 * @author Casey Szilagyi
 */
public class PlayerFileException extends RuntimeException {

  /**
   * Sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public PlayerFileException(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Pass a custom message and the exception itself
   *
   * @param errorMessage The message corresponding to the error
   * @param exception The actual exception
   */
  public PlayerFileException(String errorMessage, Exception exception) {
    super(errorMessage, exception);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   *
   * @param error The type of error
   */
  public PlayerFileException(Exception error){ super(error.getMessage()); }

}
