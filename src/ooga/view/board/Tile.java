package ooga.view.board;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * purpose: it is part of a board, can have a piece and change colors.
 * dependencies: extends Parent.
 *
 * @author Kenneth Moore III
 */
public class Tile extends Parent {
  private ImageView myPieceImage;
  private EventHandler myEventHandler;
  private Rectangle myRectangle;
  private Color myColor;

  /**
   * purpose: constructor for Tile
   * assumptions: valid inputs.
   * @param size the width and height of tile in pixels.
   * @param e: the eventhandler of what happens when tile is pressed
   */
  public Tile(int size, EventHandler<MouseEvent> e) {
    super();
    myRectangle = new Rectangle(size, size);
    setColor(Color.WHITE);
    myEventHandler = e;
    myRectangle.setOnMouseClicked(myEventHandler);
    this.getChildren().add(myRectangle);
  }

  /**
   * purpose: to get the piece the tile has, which is really the image.
   * @return ImageView myPieceImage, representing the piece
   */
  public ImageView getPiece() {
    return myPieceImage;
  }

  /**
   * purpose: adds a piece (ImageView) to the tile
   * assumptions: there is no piece already there.
   * @param piece: the ImageView that is being added to the tile
   */
  public void addPiece(ImageView piece) {
    piece.setOnMouseClicked(myEventHandler);
    this.getChildren().add(piece);
    myPieceImage = piece;
  }

  /**
   * purpose: to remove a piece from the tile
   * assumptions: that the tile has a piece
   */
  public void removePiece() {
    this.getChildren().remove(myPieceImage);
    myPieceImage = null;
  }

  /**
   * purpose: changes the color temporarily of the tile
   * @param color: the color the piece is changed to
   */
  public void changeColor(Color color) {
    myRectangle.setFill(color);
  }

  /**
   * purpose: resets the color back to its regular color
   */
  public void unHighlight() {
    myRectangle.setFill(myColor);
  }

  /**
   * purpose: sets the perm color of the tile and updates it grpahically
   * @param color: the color that
   */
  public void setColor(Color color) {
    myColor = color;
    changeColor(myColor);
  }

}
