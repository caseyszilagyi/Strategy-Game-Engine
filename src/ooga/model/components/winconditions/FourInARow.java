package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

public class FourInARow implements EndGameConditioin{

  private static final int FOUR = 4;

  private FrontEndExternalAPI viewController;
  private GameBoard board;
  private int width;
  private int height;

  public FourInARow(FrontEndExternalAPI viewController, GameBoard board){
    this.viewController = viewController;
    this.board = board;
  }

  @Override
  public boolean checkForWin(String teamName) {
    width = board.getWidth();
    height = board.getHeight();
    for(int x = 0; x < width; x++){
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
    if(FOUR + y > height){
      //System.out.println("failed down bounds check");
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newY = y + counter;
      if(!board.isPieceAtCoordinate(x,  newY)){
        //System.out.printf("failed down check because no piece at coordinate %d:%d\n", x, newY);
        return false;
      }
      if(!board.getPieceAtCoordinate(x, newY).getPieceTeam().equals(teamName)){
        //System.out.printf("failed down check because  piece at coordinate %d:%d is on team %s, not team %s\n", x, newY, board.getPieceAtCoordinate(x, newY).getPieceTeam(), teamName);
        return false;
      }
    }
    //System.out.println("passed down check");
    return true;
  }

  private boolean checkRight(int x, int y, String teamName){
    if(FOUR + x > width){
      //System.out.println("failed right bounds check");
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x - counter;
      if(!board.isPieceAtCoordinate(newX,  y) || !board.getPieceAtCoordinate(newX, y).getPieceTeam().equals(teamName)){
        //System.out.println("failed right check");
        return false;
      }
    }
    //System.out.println("passed right check");
    return true;
  }

  private boolean checkDiagonal(int x, int y, String teamName){
    if(FOUR + y > height || FOUR + x > width){
      //System.out.println("failed diagonal bounds check");
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x - counter;
      int newY = y - counter;
      if(!board.isPieceAtCoordinate(newX,  newY) || !board.getPieceAtCoordinate(newX, newY).getPieceTeam().equals(teamName)){
        //System.out.println("failed diagonal check");
        return false;
      }
    }
    //System.out.println("passed diagonal check");
    return true;
  }

}
