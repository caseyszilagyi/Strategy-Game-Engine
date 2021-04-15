package ooga.view;

import java.awt.Point;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import ooga.controller.ModelController;

public class Board extends GridPane {
  Tile tiles[][];
  private static final int SQUARE_SIZE = 50;
  private final ModelController modelController;

  /**
   * Constructs a {@link Board} of the desired width and height. Also
   * obtains a reference to a {@link ModelController}
   * @param width desired board width
   * @param height desired board height
   * @param modelController
   */
  public Board(int width, int height, ModelController modelController) {
    super();
    tiles = new Tile[width][height];
    this.modelController = modelController;
    makeGrid(); // TODO: Make this flexible
    populateBoard();
  }

  /**
   * Creates the grid structure of the board, styling with patterns.
   */
  public void makeGrid(){
    for (int i = 0; i<8; i++) {
      for (int j = 0; j<8; j++) {
        Tile temp;
        if((i+j)%2 ==1) {
          temp = createTile(i, j, Color.TAN);
          tiles[i][j] = temp;
          this.add(temp, i, j);
        } else {
          temp = createTile(i, j, Color.BEIGE);
          tiles[i][j] = temp;
          this.add(temp, i, j);
        }
      }
    }
  }

  /**
   * Moves a piece from one coordinate on a board to another. This method assumes
   * that the move is valid.
   * @param startX starting x coordinate
   * @param startY starting y coordinate
   * @param endX ending x coordinate
   * @param endY ending y coordinate
   */
  public void movePiece(int startX, int startY, int endX, int endY){
    ImageView piece = removePiece(startX, startY);
    tiles[endX][endY].addPiece(piece);
  }


  /**
   * Highlights all possible spaces a clicked piece can move to.
   * @param possibleMoves an {@link Iterator} of {@link Pair} objects of 2 integers
   */
  public void showPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves){
    while (possibleMoves.hasNext()){
      Pair<Integer, Integer> p = possibleMoves.next();
      highlightTile(p.getKey(), p.getValue());
    }
  }

  /**
   * Unhighlights all {@link Tile} on the board
   */
  public void unhighlightAll(){
    for (int i = 0; i<8; i++) {
      for (int j = 0; j<8; j++) {
        tiles[i][j].unHighlight();
      }
    }
  }

  /**
   * Highlights a single {@link Tile} on the board, specified by x and y coordinate.
   * @param x x coordinate of tile
   * @param y y coordinate of tile
   */
  public void highlightTile(int x, int y){
    tiles[x][y].highLight();
  }

  /**
   * Removes a game piece from the board.
   * @param x x coordinate of piece
   * @param y y coordinate of piece
   * @return
   */
  public ImageView removePiece(int x, int y){
    ImageView piece = tiles[x][y].getPiece();
    tiles[x][y].removePiece();
    return piece;
  }


  private Tile createTile(int i, int j, Color color){
    return new Tile(color, SQUARE_SIZE, new Point(i, j), e -> handleTileClick(i, j));
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

  private void handleTileClick(int i, int j){
    modelController.actOnCoordinates(i, j);
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
