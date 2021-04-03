package ooga.model.game_components.move_types;

import java.util.List;
import ooga.model.game_components.move_types.move_restrictions.GeneralRestriction;

public abstract class PieceMove {

  // A list of restrictions that the subclass has to check for before declaring a move valid
  private List<GeneralRestriction> restrictions;

  public void checkIfValidMove(){ };

}
