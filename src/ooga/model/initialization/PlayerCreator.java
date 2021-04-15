package ooga.model.initialization;

import ooga.model.components.Player;

public class PlayerCreator extends Creator{


  public Player makePlayer(String playerName){
    return new Player(playerName);
  }
}
