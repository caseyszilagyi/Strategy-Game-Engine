package ooga.model.components;

public class Player{
  int numWins;
  int numLosses;
  int numTies;
  String name;

  public Player(String name){
    this(name, 0, 0, 0);
  }
  public Player(String name, int numWins, int numLosses, int numTies){
    this.name = name;
    this.numWins = numWins;
    this.numLosses = numLosses;
    this.numTies = numTies;
  }

  public String getWinLoss(){
    return Integer.toString(numWins) + ":" + Integer.toString(numLosses) + ":" + Integer.toString(numTies);
  }
  public String getName(){ return name; }

}
