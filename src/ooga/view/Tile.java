package ooga.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tile extends GridPane {

  public Tile(Color fill, int size){
    super();
    this.add(new Rectangle(size, size, fill), 0, 0);
  }

  public void addPiece(ImageView piece){
    this.add(piece, 0, 0);
  }
}
