package ooga.model.game_components;

public class Coordinate{

  private int x;
  private int y;

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  @Override
  public boolean equals(Object coordinates){
    if(coordinates == null){ return false; }
    if(coordinates == this) { return true; }
    if(!(coordinates instanceof Coordinate)) { return false; }

    Coordinate otherCoordinates = (Coordinate) coordinates;
    if(x == otherCoordinates.getX() && y == otherCoordinates.getY()){
      return true;
    }
    return false;
  }

  @Override
  public int hashCode(){
    return x*100 + y;
  }
}
