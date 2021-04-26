package ooga.exceptions;

/**
 * Used to throw exceptions related to the game board
 *
 * @author Casey Szilagyi
 */
public class GameBoardException extends RuntimeException {

  /**
   * Sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public GameBoardException(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Pass a custom message and the exception itself
   *
   * @param errorMessage The message corresponding to the error
   * @param exception    The actual exception
   */
  public GameBoardException(String errorMessage, Exception exception) {
    super(errorMessage, exception);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   *
   * @param error The type of error
   */
  public GameBoardException(Exception error) {
    super(error.getMessage());
  }

}
