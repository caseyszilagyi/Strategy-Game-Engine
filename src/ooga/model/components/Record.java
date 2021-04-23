package ooga.model.components;

public class GameRecord {

  String gameName;
  int wins;

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public int getLosses() {
    return losses;
  }

  public void setLosses(int losses) {
    this.losses = losses;
  }

  public int getDraws() {
    return draws;
  }

  public void setDraws(int draws) {
    this.draws = draws;
  }

  int losses;
  int draws;


}
