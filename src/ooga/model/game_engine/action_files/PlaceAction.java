package ooga.model.game_engine.action_files;

import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;

public class PlaceAction extends Action {

  private static final int PARAM_ONE_INDEX = 0;
  private static final int PARAM_TWO_INDEX = 1;

  private Coordinate pieceCoordinate;
  private String pieceName;

  /**
   * Expects parameters to be of size 2, where the first String (index 0) is representing a coordinate,
   * in the form x:y, where x and y can be cast to an int, while the second String (index 0) is the name of
   * the piece to be created
   * @param parameters
   */
  public PlaceAction(List<String> parameters, FrontEndExternalAPI viewController){
    super(viewController);
    pieceCoordinate = stringToCoordinate(parameters.get(PARAM_ONE_INDEX));
    pieceName = parameters.get(PARAM_TWO_INDEX);
  }

  @Override
  public boolean executeAction(GameBoard board, GameRules rules) {
    GamePiece pieceToAdd = stringToPiece(pieceName, pieceCoordinate, rules);
    return board.addPiece(pieceToAdd);
  }
}
