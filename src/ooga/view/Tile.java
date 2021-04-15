package ooga.view;

import java.awt.Point;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Parent {
  Point myPosition;
  boolean hasPiece;
  ImageView pieceImage;

  public Tile(Color fill, int size, Point position) {
    super();
    this.getChildren().add(new Rectangle(size, size, fill));
    myPosition = position;
    hasPiece = false;
  }

  public void addPiece(ImageView piece) {
    this.getChildren().add(piece);
    hasPiece = true;
    pieceImage = piece;
  }

  public void removePiece() {
    this.getChildren().remove(1);//does this work
    hasPiece = false;
    pieceImage = null;
  }

  public Point getPosition() {
    return new Point(myPosition);
  }
}
