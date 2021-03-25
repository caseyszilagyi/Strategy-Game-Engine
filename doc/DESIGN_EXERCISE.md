
# OOGA Lab Discussion
## Names and NetIDs
Kenneth Moore III (km460)
Shaw Phillips (sp422)
Yi Chen (yc311)
Cole Spector (cgs26)
Casey Szilagyi (crs79)
## Fluxx

### High Level Design Ideas

- Have an interface for cards
    - have an abstract class for action cards
    - have an abstract class for rule cards
    - have an abstract class for the winning-type cards

- Win condition needs to be separate from the current state of the game
    - Some object that checks whether a player has met the win condition
        - Needs to be able to be changed with card objects

- Idea of a "turn" needs to be flexible
    - Maybe have a turn object
    - If a player changes the rules of a turn, then this has to be reflected in each of the other players. So each player has to have an object representing the turn possibly


### CRC Card Classes



```java=

public class Deck {
    private Queue<Card> deck;
    
    public Card draw(){
        return deck.pop();
    }
}
public interface Card extends <Enum E extends E> {
    protected E cardType;
    private String name;
    private String description;
    
    init(String name, String description);
    
    public E getCardType(){
        return cardType;
    }
}

public abstract class actionCard implements Card {
    
    
    public void act();
}

public abstract class ruleCard implements Rule {
    public void setRule();
}
```



### Use Cases

### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);