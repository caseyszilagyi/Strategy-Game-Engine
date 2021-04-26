package ooga.model.components.player;

import ooga.exceptions.PlayerFileException;

public class Player{

  private String fullName;
  private String firstName;
  private String lastName;
  private Record[] records;

  public Player() {};
  public Player(String name){
    this.fullName = name;
  }
  public Player(String firstName, String lastName, Record[] records) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.records = records;
    this.fullName = firstName + " " + lastName;
  }

  public Record getRecord(String gameName){
    for(Record record: records){
      if(record.getGameName().equals(gameName)){
        return record;
      }
    }
    throw new PlayerFileException("noSuchGame");
  }

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

  public String getFullName(){ return fullName; }

  public void setName(String... name){
    firstName = name[0];
    fullName = firstName;
    if(name.length > 1){
      lastName = name[1];
      fullName = fullName + " " + lastName;
    }
  }



  @Override
  public boolean equals(Object obj){
    return obj.toString().equals(fullName);
  }

  @Override
  public String toString(){
    return getFullName();
  }

  @Override
  public int hashCode(){
    int result = 7;
    for(char c: fullName.toCharArray()){
      result = result *43 + c;
    }
    return result;
  }

}
