package ooga.model.components;

/**
 * The board used to house all of the GamePieces
 */
public interface Board{


  /**
   * This method will move the GamePiece from the starting Coordinate to the ending Coordinate
   * @param startingCoordinate the Coordinate where the GamePiece to move resides
   * @param endingCoordinate the Coordinate to move the GamePiece to
   * @return whether or not the move was successful
   */
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate);

  /**
   * This method will remove the GamePiece from the board at the Coordinate given
   *
   * This method does not return a value, for no matter what, after this method is called there will not
   * be a GamePiece at the Coordinate called
   *
   * @param coordinate the Coordinate of the GamePiece to remove
   */
  public void removePiece(Coordinate coordinate);

  /**
   * This method will add a GamePiece with a previously defined Coordinate to the Board
   *
   * This method assumes that the GamePiece has a previously defined Coordinate
   *
   * @param newPieceType this is the GamePiece to add to the Board
   * @return whether or not the GamePiece was successfully added to the Board
   */
  public boolean addPiece(GamePiece newPieceType);

}
