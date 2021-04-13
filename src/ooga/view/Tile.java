package ooga.view;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tile extends Parent {

  public Tile(Color fill, int size){
    super();
    this.getChildren().add(new Rectangle(size, size, fill));
  }

  public void addPiece(ImageView piece){
    this.getChildren().add(piece);
  }
}
