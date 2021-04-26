package ooga.view.board;

import java.awt.Point;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import ooga.controller.ModelController;

/**
 * Purpose: a visual representation of the board.
 * Assumptions: that one calls createBoard shortly after it is made, or other methods will fail.
 * Dependencies: extends GridPane and uses Tiles.
 * An Example: Board board = new Board; createBoard(8,8);
 *
 * @author Kenneth Moore III
 */
public class Board extends GridPane {

  Tile[][] tiles;
  private static final int SQUARE_SIZE = 50;
  private final ModelController modelController;
  private final ResourceBundle pieceBundle = ResourceBundle.getBundle("ooga.view.resources.chessPieces");
  //TODO: ^^^ can the above be better through reflection
  private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.LIGHTGREEN;
  private static final Color DEFAULT_DARK_COLOR = Color.TAN;
  private static final Color DEFAULT_LIGHT_COLOR = Color.BEIGE;
  private Color myHighlightColor;

  /**
   * Constructs a {@link Board} and obtains a reference to {@link ModelController}
   *
   * @param modelController
   */
  public Board(ModelController modelController) {
    super();
    this.modelController = modelController;
  }

  /**
   * Purpose: to create a board of a specific width and height.
   * Assumptions: must be called shortly after construction, assumes width and height are 1-26.
   * @param width: the width of the board
   * @param height: the height of the board
   */
  public void createBoard(int width, int height) {
    tiles = new Tile[width][height];
    makeGrid(width, height);
    setColorsDefault();
  }

  /**
   * Creates the grid structure of the board, styling with patterns.
   */
  private void makeGrid(int width, int height) {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Tile temp;
        if ((i + j) % 2 == 1) {
          temp = createTile(i, j);
          tiles[i][j] = temp;
          this.add(temp, i, j);
        } else {
          temp = createTile(i, j);
          tiles[i][j] = temp;
          this.add(temp, i, j);
        }
      }
    }
  }

  private void colorGrid(Color firstColor, Color secondColor) {
    colorDarkSquares(firstColor);
    colorLightSquares(secondColor);
  }

  /**
   * Purpose: to color all the light colored squares a specific color.
   * Assumptions: assumes valid color
   * Parameters (purpose beyond their name if necessary)
   * @param color the new color of the light tiled squares.
   */
  public void colorLightSquares(Color color) {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if ((i + j) % 2 == 0) {
          tiles[i][j].setColor(color);
        }
      }
    }
  }

  /**
   * Purpose: to color all the dark colored squares a specific color.
   * Assumptions: assumes valid color
   * @param color the new color of the dark tiled squares.
   */
  public void colorDarkSquares(Color color) {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if ((i + j) % 2 == 1) {
          tiles[i][j].setColor(color);
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
    for (Tile[] tile : tiles) {
      for (Tile value : tile) {
        value.unHighlight();
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
    tiles[x][y].changeColor(myHighlightColor);
  }

  /**
   * Purpose: to set the highlight color of the squares.
   * Assumptions: assumes valid color
   * @param color: the value that instance variable myHighlightColor is being set to
   */
  public void setHighlightColor(Color color) {
    myHighlightColor = color;
  }

  /**
   * Removes a game piece from the board. This works similar to a {@code pop()} method
   * and will return the piece that was just removed.
   *
   * @param x x coordinate of piece
   * @param y y coordinate of piece
   * @return the {@code ImageView} of the piece that was just removed.
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
  public boolean spaceIsEmpty(int x, int y) {
    return tiles[x][y].getPiece() == null;
  }


  private Tile createTile(int i, int j) {
    return new Tile(SQUARE_SIZE, new Point(i, j), e -> handleTileClick(i, j));
  }

  /**
   * Purpose: to add a piece at a certain location
   * Assumptions: valid paramaters
   * @param x: the x position of the piece
   * @param y: the y position of the piece
   * @param color: the team/color of the piece
   * @param fileName: the type of piece it is
   */
  public void addPiece(int x, int y, String color, String fileName) {
    ImageView piece = new ImageView(new Image("BasicChessPieces/" + pieceBundle.getString(color + fileName)));
    piece.setFitHeight(SQUARE_SIZE);
    piece.setFitWidth(SQUARE_SIZE);
    tiles[x][y].addPiece(piece);
  }

  private void handleTileClick(int i, int j) {
    unhighlightAll();
    modelController.actOnCoordinates(i, j);
  }

  /**
   * Purpose: to set the board and highlight colors to the defaults
   */
  public void setColorsDefault() {
    setHighlightColor(DEFAULT_HIGHLIGHT_COLOR);
    colorGrid(DEFAULT_DARK_COLOR, DEFAULT_LIGHT_COLOR);
  }
}
