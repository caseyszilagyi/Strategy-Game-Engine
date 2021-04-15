package ooga.view;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Parent {
  Point myPosition;
  boolean hasPiece;
  ImageView myPieceImage;
  EventHandler myEventHandler;

  public Tile(Color fill, int size, Point position, EventHandler<MouseEvent> e) {
    super();
    Rectangle rec  = new Rectangle(size, size, fill);
    myEventHandler = e;
    rec.setOnMouseClicked(myEventHandler);
    this.getChildren().add(rec);
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

  public void highLight() {
    Node i = this.getChildren().get(0);
    //i.
  }

}
