package ooga.exceptions;

/**
 * Make exceptions related to XML parsing
 *
 * @author Casey Szilagyi
 */
public class XMLParseException extends RuntimeException{

  /**
   * Sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public XMLParseException(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Pass a custom message and the exception itself
   *
   * @param errorMessage The message corresponding to the error
   * @param exception The actual exception
   */
  public XMLParseException(String errorMessage, Exception exception) {
    super(errorMessage, exception);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   *
   * @param error The type of error
   */
  public XMLParseException(Exception error){ super(error.getMessage()); }

}
