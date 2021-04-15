package ooga.view;

import java.awt.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Board extends GridPane {
  Tile tiles[][];
  private static final int SQUARE_SIZE = 50;

  public Board(int width, int height) {
    super();
    tiles = new Tile[width][height];

    Tile temp;
    for (int i = 0; i<8; i++) {
      for (int j = 0; j<8; j++) {
        if((i+j)%2 ==1) {
          temp = new Tile(Color.TAN, SQUARE_SIZE, new Point(i, j));
          tiles[i][j] = temp;
          this.add(temp, i, j);
        } else {
          temp = new Tile(Color.BEIGE, SQUARE_SIZE, new Point(i, j));
          tiles[i][j] = temp;
          this.add(temp, i, j);
        }
      }
    }
    populateBoard();
  }
  
  private void populateBoard(){
    populateBlackPieces();
  }

  private void populateBlackPieces() {
    populateBlackPawns();
    populateSpecialPieces("Black", 0);
  }

  private void populateSpecialPieces(String color, int row) {
    for(int i = 0; i<6; i+=5) {
      ImageView rook = new ImageView(new Image("BasicChessPieces/" + color + "Rook.png"));
      rook.setFitHeight(SQUARE_SIZE);
      rook.setFitWidth(SQUARE_SIZE);
      tiles[i][row].addPiece(rook);
      ImageView knight = new ImageView(new Image("BasicChessPieces/" + color + "Knight.png"));
      knight.setFitHeight(SQUARE_SIZE);
      knight.setFitWidth(SQUARE_SIZE);
      tiles[i+1][row].addPiece(knight);
      ImageView bishop = new ImageView(new Image("BasicChessPieces/" + color + "Bishop.png"));
      bishop.setFitHeight(SQUARE_SIZE);
      bishop.setFitWidth(SQUARE_SIZE);
      tiles[i+2][row].addPiece(bishop);
    }
  }

  private void populateBlackPawns() {
    Image blackPawn = new Image("BasicChessPieces/BlackPawn.png");
    ImageView tempBlackPawn;
    for (int i = 0; i<8; i++) {
      tempBlackPawn = new ImageView(blackPawn);
      tempBlackPawn.setFitHeight(SQUARE_SIZE);
      tempBlackPawn.setFitWidth(SQUARE_SIZE);
      tiles[i][1].addPiece(tempBlackPawn);
    }
  }
}
