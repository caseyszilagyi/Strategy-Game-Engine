package ooga.model.game_initialization.file_parsing;

/**
 * I think this will need to be specific for each game type because the files will be parsed
 * differently. Also need to determine what data format the information will be passed
 * down in before loading it into classes
 */
public class GameFileParser implements FileParser {

  private XMLParser parser;

  public GameFileParser(){
    try {
      parser = new XMLParser("dummy","dummy");
    }catch (Exception e){

    }
  }

  @Override
  public String initializeGame(String gameName) {
    return null;
  }

  @Override
  public String parseBoard(String boardFileName) {
    return null;
  }

  @Override
  public String parseGameRules(String rulesFileName) {
    return null;
  }

  @Override
  public String parsePlayerInfo(String playerFileName) {
    return null;
  }
}
