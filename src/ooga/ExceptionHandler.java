package ooga;

/**
 * Used to make errors that can be caught
 *
 * @author Casey Szilagyi
 */
public class ExceptionHandler extends RuntimeException {

  /**
   * Sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public ExceptionHandler(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   *
   * @param error The type of error
   */
  public ExceptionHandler(Exception error){ super(error.getMessage()); }
}
