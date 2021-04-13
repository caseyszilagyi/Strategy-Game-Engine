package ooga.model.engine.action_files;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.GameRules;
import ooga.model.initialization.pieces.PieceCreator;

public abstract class Action {

  private static final int X_INDEX = 0;
  private static final int Y_INDEX = 1;
  private FrontEndExternalAPI viewController;
  private GameBoard gameBoard;

  public Action(FrontEndExternalAPI viewController, GameBoard gameBoard){
    this.viewController = viewController;
    this.gameBoard = gameBoard;
  }

  public abstract boolean executeAction(GameBoard board, GameRules rules);

  protected GamePiece stringToPiece(String pieceAsString, Coordinate coordinate, GameRules rules){
    //TODO: get name of game from rules
    String gameName = "Chess"; //this will be overridden with todo above
    PieceCreator pieceCreator = new PieceCreator(gameName, viewController, gameBoard);
    return pieceCreator.makePiece(pieceAsString, coordinate, 1, viewController, "addTeam");
  }

  protected Coordinate stringToCoordinate(String coordinateAsString){
    String[] coordinateArray = coordinateAsString.split("\\:");
    if(!ensureStringToCoordinateTranslation(coordinateArray)){
      //TODO: Throw exception
      System.err.println("String " + coordinateAsString + " is not translatable to type Coordinate");
      return null;
    }
    return new Coordinate(Integer.valueOf(coordinateArray[X_INDEX]), Integer.valueOf(coordinateArray[Y_INDEX]));
  }

  private boolean ensureStringToCoordinateTranslation(String[] arrayToCheck){
    return (arrayToCheck.length == 2 && isInt(arrayToCheck[X_INDEX]) && isInt(arrayToCheck[Y_INDEX]));
  }

  private boolean isInt(String intToConvert){
    try{
      Integer.parseInt(intToConvert);
      return true;
    } catch (Exception E){
      return false;
    }
  }



}
