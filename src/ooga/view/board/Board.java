package ooga.view.board;

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
    populatePieces();
  }

  private void populatePieces() {
    populateBlackPawns();
    populateSpecialPieces("Black", 0);
    populateWhitePawns();
  }

  private void populateSpecialPieces(String color, int row) {
    addPiece(0, 0, "Black", "Rook.png");
    addPiece(1, 0, "Black", "Knight.png");
    addPiece(2, 0, "Black", "Bishop.png");
    addPiece(3, 0, "Black", "Queen.png");
    addPiece(4, 0, "Black", "King.png");
    addPiece(5, 0, "Black", "Bishop.png");
    addPiece(6, 0, "Black", "Knight.png");
    addPiece(7, 0, "Black", "Rook.png");
    addPiece(0, 7, "White", "Rook.png");
    addPiece(1, 7, "White", "Knight.png");
    addPiece(2, 7, "White", "Bishop.png");
    addPiece(3, 7, "White", "Queen.png");
    addPiece(4, 7, "White", "King.png");
    addPiece(5, 7, "White", "Bishop.png");
    addPiece(6, 7, "White", "Knight.png");
    addPiece(7, 7, "White", "Rook.png");
  }

  private void addPiece(int x, int y, String color, String fileName){
    ImageView bishop = new ImageView(new Image("BasicChessPieces/" + color + fileName));
    bishop.setFitHeight(SQUARE_SIZE);
    bishop.setFitWidth(SQUARE_SIZE);
    tiles[x][y].addPiece(bishop);
  }

  private void handleTileClick(int i, int j){
    unhighlightAll();
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

  private void populateWhitePawns() {
    Image blackPawn = new Image("BasicChessPieces/WhitePawn.png");
    ImageView tempBlackPawn;
    for (int i = 0; i<8; i++) {
      tempBlackPawn = new ImageView(blackPawn);
      tempBlackPawn.setFitHeight(SQUARE_SIZE);
      tempBlackPawn.setFitWidth(SQUARE_SIZE);
      tiles[i][6].addPiece(tempBlackPawn);
    }
  }
}
