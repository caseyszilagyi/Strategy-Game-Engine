package ooga.view.board;

import java.awt.Point;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import ooga.controller.ModelController;
import ooga.view.Configuration;

public class Board extends GridPane {

  Tile tiles[][];
  private static final int SQUARE_SIZE = 50;
  private final ModelController modelController;
  private final ResourceBundle pieceBundle = ResourceBundle.getBundle("ooga.view.resources.chessPieces");

  /**
   * Constructs a {@link Board} of the desired width and height. Also obtains a reference to a
   * {@link ModelController}
   *
   * @param width           desired board width
   * @param height          desired board height
   * @param modelController
   */
  public Board(int width, int height, ModelController modelController) {
    super();
    tiles = new Tile[width][height];
    this.modelController = modelController;
    makeGrid(); // TODO: Make this flexible
    //populateBoard();
  }

  /**
   * Creates the grid structure of the board, styling with patterns.
   */
  public void makeGrid() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Tile temp;
        if ((i + j) % 2 == 1) {
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
   * Moves a piece from one coordinate on a board to another. This method assumes that the move is
   * valid.
   *
   * @param startX starting x coordinate
   * @param startY starting y coordinate
   * @param endX   ending x coordinate
   * @param endY   ending y coordinate
   */
  public void movePiece(int startX, int startY, int endX, int endY) {
    ImageView piece = removePiece(startX, startY);
    tiles[endX][endY].addPiece(piece);
  }


  /**
   * Highlights all possible spaces a clicked piece can move to.
   *
   * @param possibleMoves an {@link Iterator} of {@link Pair} objects of 2 integers
   */
  public void showPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {
    while (possibleMoves.hasNext()) {
      Pair<Integer, Integer> p = possibleMoves.next();
      highlightTile(p.getKey(), p.getValue());
    }
  }

  /**
   * Unhighlights all {@link Tile} on the board
   */
  public void unhighlightAll() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        tiles[i][j].unHighlight();
      }
    }
  }

  /**
   * Highlights a single {@link Tile} on the board, specified by x and y coordinate.
   *
   * @param x x coordinate of tile
   * @param y y coordinate of tile
   */
  public void highlightTile(int x, int y) {
    tiles[x][y].highLight();
  }

  /**
   * Removes a game piece from the board.
   *
   * @param x x coordinate of piece
   * @param y y coordinate of piece
   * @return
   */
  public ImageView removePiece(int x, int y) {
    ImageView piece = tiles[x][y].getPiece();
    tiles[x][y].removePiece();
    return piece;
  }

  /**
   * Determines whether the queried tile is empty.
   * @param x x coordinate
   * @param y y coordinate
   * @return a boolean
   */
  public boolean spaceIsEmpty(int x, int y){
    return tiles[x][y].getPiece() == null;
  }


  private Tile createTile(int i, int j, Color color) {
    return new Tile(color, SQUARE_SIZE, new Point(i, j), e -> handleTileClick(i, j));
  }

  private void populateBoard() {
    //populatePieces("data/gamelogic/starting_states/chess.xml");
  }

//  private void populatePieces(String file) {
//    try {
//      Configuration c = new Configuration(file);
//      Map<String, String> opponentPositionMap = c.getOpponentPositionMap();
//      Map<String, String> userPositionMap = c.getUserPositionMap();
//      populatePiecesFromMap(opponentPositionMap, "Black");
//      populatePiecesFromMap(userPositionMap, "White");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  private void populatePiecesFromMap(Map<String, String> positionMap, String color) {
    Set<String> keys = positionMap.keySet();
    for (String s : keys) {
      addPiece(getPointFromChessString(s), capitalizeFirstLetterOfWord(color),
          capitalizeFirstLetterOfWord(positionMap.get(s)) + ".png");
    }
  }

  private String capitalizeFirstLetterOfWord(String s) {
    String returned = s.substring(0, 1).toUpperCase() + s.substring(1);
    return returned;

  }

  private Point getPointFromChessString(String s) {
    //TODO: Error checking
    int column = s.toLowerCase().charAt(0) - 97;//ascii offset to get a to 0
    int row =
        8 - (Integer.parseInt(s.substring(1)));//to account for 0-index and to flip coordinates
    return new Point(column, row);
  }

  private void addPiece(Point p, String color, String fileName) {
    addPiece((int) p.getX(), (int) p.getY(), color, fileName);
  }

  public void addPiece(int x, int y, String color, String fileName) {
    ImageView piece = new ImageView(new Image("BasicChessPieces/" + pieceBundle.getString(color+fileName)));
    piece.setFitHeight(SQUARE_SIZE);
    piece.setFitWidth(SQUARE_SIZE);
    tiles[x][y].addPiece(piece);
  }

  private void handleTileClick(int i, int j) {
    unhighlightAll();
    modelController.actOnCoordinates(i, j);
  }

}
