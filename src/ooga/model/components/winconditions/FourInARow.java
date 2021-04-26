package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

/**
 * This class is used to determine if someone has won the current game, by checking if the opponent
 * team ahs any possible moves
 *
 * This class assumes a defined FrontEndExternalAPI is passed in, as well as the current GameBoard
 *
 * This class depends upon FrontEndExternalAPI.java and GameBoard.java
 *
 * Code Example:
 * //assumes GameBoard gameBoard and FrontEndExternalAPI frontEndExternalAPI have been previously defined
 *  EndGameCondition endGameCondition = new FourInARow(frontEndExternalAPI, gameBoard);
 *  gameBoard.movePiece(0,0);
 *  if(endGameCondition.checkForWin){
 *    exit(0);
 *  }
 *  //continue the game
 *
 * @author Cole Spector
 */
public class FourInARow implements EndGameConditioin{

  private static final int FOUR = 4;

  private FrontEndExternalAPI viewController;
  private GameBoard board;
  private int width;
  private int height;

  /**
   * Initializer for the FourInARow EndGameCondition
   * @param viewController a valid FrontEndExternalAPI which the game is linked to.
   * @param board the current GameBoard to be used
   */
  public FourInARow(FrontEndExternalAPI viewController, GameBoard board){
    this.viewController = viewController;
    this.board = board;
  }

  /**
   * This method is called to determine if someone has won the current game
   * @param teamName the team name to check to see if they won
   * @return a boolean for whether or not the teamName given has won the game
   */
  @Override
  public boolean checkForWin(String teamName) {
    width = board.getWidth();
    height = board.getHeight();
    for(int x = 0; x < width; x++){
      for(int y = 0; y < height; y++){
        if(board.isPieceAtCoordinate(x,y)){
          if(checkDown(x, y, teamName) || checkRight(x, y, teamName) || checkDiagonalRight(x, y, teamName) || checkDiagonalLeft(x, y, teamName)){
            viewController.gameWin(teamName);
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

  private boolean checkDiagonalRight(int x, int y, String teamName){
    if(FOUR + y > height || FOUR + x > width){
      //System.out.println("failed diagonal bounds check");
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x + counter;
      int newY = y + counter;
      if(!board.isPieceAtCoordinate(newX,  newY) || !board.getPieceAtCoordinate(newX, newY).getPieceTeam().equals(teamName)){
        //System.out.println("failed diagonal check");
        return false;
      }
    }
    //System.out.println("passed diagonal check");
    return true;
  }

  private boolean checkDiagonalLeft(int x, int y, String teamName){
    if(FOUR + y > height || FOUR - x < 0){
      //System.out.println("failed diagonal bounds check");
      return false;
    }
    for(int counter = 0; counter < FOUR; counter ++){
      int newX = x - counter;
      int newY = y + counter;
      if(!board.isPieceAtCoordinate(newX,  newY) || !board.getPieceAtCoordinate(newX, newY).getPieceTeam().equals(teamName)){
        //System.out.println("failed diagonal check");
        return false;
      }
    }
    //System.out.println("passed diagonal check");
    return true;
  }

}
