package ooga.model.components.player;

public class Record {

  String gameName;

  public Record(){};
  public Record(String gameName, int wins, int losses, int draws) {
    this.gameName = gameName;
    this.wins = wins;
    this.losses = losses;
    this.draws = draws;
  }

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

  public void addWin(){ wins++; }

  public void addLoss(){ losses++; }

  public void addDraw(){ draws++; }

  int wins;
  int losses;
  int draws;


}
