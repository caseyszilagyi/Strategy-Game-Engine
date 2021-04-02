package ooga;

import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.controller.ModelController;
import ooga.controller.ViewController;
import ooga.model.game_components.Player;
import ooga.model.game_engine.Engine;
import ooga.model.game_engine.GameEngine;
import ooga.model.game_initialization.Creator;
import ooga.model.game_initialization.GameClassLoader;
import ooga.model.game_initialization.GameCreator;
import ooga.model.game_initialization.Loader;
import ooga.model.game_initialization.file_parsing.FileParser;
import ooga.model.game_initialization.file_parsing.GameFileParser;
import ooga.model.game_initialization.file_parsing.XMLParser;

public class BasicCases {

  FrontEndExternalAPI viewController;
  BackEndExternalAPI modelController;
  Creator gameCreator;
  Engine gameEngine;
  Loader gameClassLoader = new GameClassLoader();
  FileParser fileParser = new GameFileParser();


  public UseCase(){
    viewController = new ViewController();
    modelController = new ModelController();
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);
    gameCreator = new GameCreator(viewController);
    gameEngine = new GameEngine(viewController);
  }

  // Player creates profile
  public void createPlayerProfile(){
    gameCreator.setPlayer("PlayerName");
    //which calls
    String playerDetails = fileParser.parsePlayerInfo("PlayerName");
    //This will just return default values, that can be passed to
    Player newPlayer = gameClassLoader.makePlayer(playerDetails);
  }

  // Player loads profile
  public void loadProfile(){
    gameCreator.setPlayer("PlayerName");
    //which calls
    String playerDetails = fileParser.parsePlayerInfo("PlayerName");
    // parses the info and passes it to
    Player newPlayer = gameClassLoader.makePlayer(playerDetails);
  }

  // Player loads profile
  public void loadDefaultProfile(){
    gameCreator.setPlayer("PlayerName");
    //which calls this method, and returns the default values based on a conditional when
    // the player doesn't exist
    String playerDetails = fileParser.parsePlayerInfo("PlayerName");
    // parses the info and passes it to
    Player newPlayer = gameClassLoader.makePlayer(playerDetails);
  }

}
