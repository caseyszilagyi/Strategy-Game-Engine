package ooga.view.board;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Parent {
  private Point myPosition;
  private boolean hasPiece;
  private ImageView myPieceImage;
  private EventHandler myEventHandler;
  private Rectangle myRectangle;
  private Color myColor;

  public Tile(int size, Point position, EventHandler<MouseEvent> e) {
    super();
    myRectangle = new Rectangle(size, size);
    setColor(Color.WHITE);
    myEventHandler = e;
    myRectangle.setOnMouseClicked(myEventHandler);
    this.getChildren().add(myRectangle);
    myPosition = position;
    hasPiece = false;
  }

  public ImageView getPiece(){
    return myPieceImage;
  }

  public void addPiece(ImageView piece) {
    piece.setOnMouseClicked(myEventHandler);
    this.getChildren().add(piece);
    hasPiece = true;
    myPieceImage = piece;
  }

  public void removePiece() {
    this.getChildren().remove(myPieceImage);//does this work
    hasPiece = false;
    myPieceImage = null;
  }

  public Point getPosition() {
    return new Point(myPosition);
  }

  public void changeColor(Color color) {
    myRectangle.setFill(color);
  }

  public void unHighlight() {
    myRectangle.setFill(myColor);
  }

  public void setColor(Color color) {
    myColor = color;
    changeColor(myColor);
  }

}
