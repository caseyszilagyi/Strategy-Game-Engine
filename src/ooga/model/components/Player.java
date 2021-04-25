package ooga.model.components;

public class Player{

  public Player() {};
  public Player(String firstName, String lastName, Record[] records) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.records = records;
  }

  String firstName;
  String lastName;
  Record[] records;

  public Record[] getRecords() {
    return records;
  }

  public void setRecords(Record[] records) {
    this.records = records;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


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

  @Override
  public boolean equals(Object obj){
    return obj.toString().equals(name);
  }

  @Override
  public String toString(){
    return name;
  }

  @Override
  public int hashCode(){
    int result = 7;
    for(char c: name.toCharArray()){
      result = result *43 + c;
    }
    return result;
  }

}
