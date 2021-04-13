package ooga.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import ooga.model.components.Coordinate;

public class DummyViewController implements FrontEndExternalAPI {

  private Set<Coordinate> allPossibleMoves;
  int remX;
  int remY;
  String identifier;
  String teamName;
  int width;
  int height;
  int startX;
  int startY;
  int endX;
  int endY;

  Map<Coordinate, String> pieceMap = new HashMap<>();
  Map<Coordinate, String> teamMap = new HashMap<>();


  @Override
  public void setModelController(BackEndExternalAPI modelController) {

  }

  @Override
  public void setBoardDimensions(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public void setBoardSpace(int x, int y, String identifier, String teamName) {
    Coordinate curr = makeCoordinates(x, y);
    pieceMap.put(curr, identifier);
    teamMap.put(curr, teamName);
  }

  @Override
  public void movePiece(int startX, int startY, int endX, int endY) {
    Coordinate startingCoords = makeCoordinates(startX, startY);
    Coordinate endingCoords = makeCoordinates(endX, endY);
    identifier = pieceMap.get(startingCoords);
    teamName = teamMap.get(startingCoords);
    pieceMap.keySet().remove(makeCoordinates(startX, startY));
    teamMap.keySet().remove(makeCoordinates(startX, startY));
    pieceMap.put(endingCoords, teamName);
    teamMap.put(endingCoords, teamName);
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
  }

  @Override
  public void removePiece(int x, int y) {
    Coordinate startingCoords = makeCoordinates(x,y);
    remY = x;
    remX = y;
    pieceMap.remove(startingCoords);
    teamMap.remove(startingCoords);
  }

  // turning this into coordinates for testing
  @Override
  public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves) {
    Set<Coordinate> moves = new HashSet<>();
    for (Iterator<Pair<Integer, Integer>> it = possibleMoves; it.hasNext(); ) {
      Pair p = it.next();
      moves.add(makeCoordinates((int) p.getKey(), (int) p.getValue()));
    }
    allPossibleMoves = moves;
  }

  // for getting for testing
  public Set<Coordinate> getAllPossibleMoves(){
    return allPossibleMoves;
  }

  private Coordinate makeCoordinates(int x, int y){
    return new Coordinate(x, y);
  }

  @Override
  public void gameEnd(String playerName) {

  }

  @Override
  public void givePieceChangeOptions(Iterable<String> pieceChangeOptions) {

  }


  // getters for testing

  public int getRemY() {
    return remY;
  }

  public int getRemX() {
    return remX;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getIdentifier(int x, int y) {
    return pieceMap.get(makeCoordinates(x,y));
  }

  public String getTeamName(int x, int y) {
    return teamMap.get(makeCoordinates(x,y));
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public int getEndX() {
    return endX;
  }

  public int getEndY() {
    return endY;
  }
}
