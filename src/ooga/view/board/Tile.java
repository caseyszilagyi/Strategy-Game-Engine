package ooga.view.board;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tile extends Parent {
  private Point myPosition;
  private boolean hasPiece;
  private ImageView myPieceImage;
  private EventHandler myEventHandler;
  private Rectangle rec;
  private static final Color HIGHLIGHT_COLOR = Color.LIGHTGREEN;
  private final Color DEFAULT_COLOR;

  public Tile(Color fill, int size, Point position, EventHandler<MouseEvent> e) {
    super();
    DEFAULT_COLOR = fill;
    rec = new Rectangle(size, size, DEFAULT_COLOR);
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
    rec.setFill(HIGHLIGHT_COLOR);
  }

  public void unHighlight() {
    rec.setFill(DEFAULT_COLOR);
  }

}
