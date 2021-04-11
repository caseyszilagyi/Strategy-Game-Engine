package ooga.model.game_engine.action_files;

import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_initialization.piece_initialization.PieceCreator;

public abstract class Action {

  private static final int X_INDEX = 0;
  private static final int Y_INDEX = 1;


  public abstract boolean executeAction(GameBoard board, GameRules rules);

  protected GamePiece stringToPiece(String pieceAsString, Coordinate coordinate, GameRules rules){
    //TODO: get name of game from rules
    String gameName = "Chess"; //this will be overridden with todo above
    PieceCreator pieceCreator = new PieceCreator(gameName);
    return pieceCreator.makePiece(pieceAsString, coordinate, 1);
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
