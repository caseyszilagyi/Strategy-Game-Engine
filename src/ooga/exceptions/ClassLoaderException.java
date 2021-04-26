package ooga.exceptions;

/**
 * Used to make exceptions relating to reflection and class loading
 *
 * This class assumes that the user provides a String error message or an Error upon declaration
 *
 * Code Example:
 * <pre>{@code
 * String fileLocation = ~/Documents/foo.java
 * try {
 *     Class<?> clazz = Class.forName(fileLocation);
 * } catch (Exception e) {
 *     throw new ClassLoaderException("unable to load class);
 * }
 * }
 * </pre>
 *
 * @author Casey Szilagyi
 */
public class ClassLoaderException extends RuntimeException {

  /**
   * Sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public ClassLoaderException(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Pass a custom message and the exception itself
   *
   * @param errorMessage The message corresponding to the error
   * @param exception The actual exception
   */
  public ClassLoaderException(String errorMessage, Exception exception) {
    super(errorMessage, exception);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   *
   * @param error The type of error
   */
  public ClassLoaderException(Exception error){ super(error.getMessage()); }

}
