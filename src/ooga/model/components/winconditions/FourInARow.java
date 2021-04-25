package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

public class FourInARow implements EndGameConditioin{

  private static final int FOUR = 4;

  private FrontEndExternalAPI viewController;
  private GameBoard board;

  public FourInARow(FrontEndExternalAPI viewController, GameBoard board){
    this.viewController = viewController;
    this.board = board;
  }

  @Override
  public boolean checkForWin(String teamName) {
    int width = board.getWidth();
    int height = board.getHeight();
    for(int x = 0; x < height; x++){
      for(int y = 0; y < height; y++){
        if(board.isPieceAtCoordinate(x,y)){
          if(checkDown(x, y, teamName) || checkRight(x, y, teamName) || checkDiagonal(x, y, teamName)){
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean checkDown(int x, int y, String teamName){
    if(board.getHeight() - y < FOUR - 1){
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newY = y - counter;
      if(!board.isPieceAtCoordinate(x,  newY) || !board.getPieceAtCoordinate(x, newY).getPieceTeam().equals(teamName)){
        return false;
      }
    }
    return true;
  }

  private boolean checkRight(int x, int y, String teamName){
    if(board.getWidth() - x < FOUR - 1){
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x - counter;
      if(!board.isPieceAtCoordinate(newX,  y) || !board.getPieceAtCoordinate(newX, y).getPieceTeam().equals(teamName)){
        return false;
      }
    }
    return true;
  }

  private boolean checkDiagonal(int x, int y, String teamName){
    if(board.getWidth() - x < FOUR - 1 || board.getHeight() - y < FOUR - 1){
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x - counter;
      int newY = y - counter;
      if(!board.isPieceAtCoordinate(newX,  newY) || !board.getPieceAtCoordinate(newX, newY).getPieceTeam().equals(teamName)){
        return false;
      }
    }
    return true;
  }

}
