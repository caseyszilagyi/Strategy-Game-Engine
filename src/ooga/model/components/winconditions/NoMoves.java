package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

public class NoMoves implements EndGameConditioin {

  private FrontEndExternalAPI viewController;
  private GameBoard board;

  public NoMoves(FrontEndExternalAPI viewController, GameBoard board){
    this.viewController = viewController;
    this.board = board;
  }

  @Override
  public boolean checkForWin(String teamName) {
    if(!board.determineIfOppositeTeamHasMove(teamName)) {
      viewController.gameEnd(teamName);
      return true;
    }
    return false;

  }
}
