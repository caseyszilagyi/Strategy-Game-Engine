package ooga.model.components;

import ooga.model.components.movehistory.Record;

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
    if(name.length > 1){
      lastName = name[1];
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
