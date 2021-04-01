# OOGA Design Plan

## Names
- Casey Szilagyi (crs79)
- Kenneth Moore III (km460)
- Shaw Phillips (sp422)
- Cole Spector (cgs26)
- Yi Chen (yc311)


### Introduction
- The goal of the project is to design a framework used to play different strategy games
- The framework will include the ability to
    - Choose different grid-based games to play (our main focus is on checkers, chess, and connect 4).
    - Keep track of different players and their game history
        - Each past game has a move history associated with it
            - This will be used in a way that games can be saved, loaded, and simulated post-hoc.
    - Change the rules of the game either through editing XML files or on startup of the game
    - Each game will have sample alterable rules (in the "customized tabs")
- Design specifications of a certain game
    - Once a specific game has been designed, the code will not be open to modificaiton
        - The game engine, piece classes, and movement rules of the pieces
    - However, XML files for the game rules and piece rules are modifiable to design new rules for the game
- View
    - Top bar has all the abilities to change screens/update information
    - Board is in the center
    - Game move history on the side
- Model
    - Basic engine that runs for all games
        - Rules and pieces that fill the board will be unique to each game
    - Have class to load in the board information and rules, as well as a class to load in piece information and make pieces


### Overview

- Will use a MVC Style
- Controller has 2 interfaces, FrontEndExternalAPI and BackEndExternalAPI that will be used to coordinate between the front and back end
- The back end will be run primarily through the GameEngine Classs
    - This will be created through the GameCreator class, which has an XML parser that will load in the data from XML files
    - This class will keep track of the players, the board, the rules, and the pieces and will serve as the class that essentially aggregates everything that a gain contains
        - The rules and pieces will be different for each game
        - The game engine will be the same for each game. Once initialized, the specific rules/board that is initialized will depend on what the user chooses in the front end
    - The individual pieces classes will be able to access the board and will contain the logic for making decisions
        - They will decide how they can move, and whether a piece is removed from the larger board
- Front end will be run primarily through the Display Interface
    - This interface can create all of the other panes that will be used to
    - There will be specific classes for displaying the
        - Board
        - Control Information
        - Splash Screen
    - Factory for making all of the objects on the front end, such as the buttons, boxes, panes, etc


### Design Details

- Splash screen begins with a shelf with game choices
    - This sends information to the back end to initialize the default version of the GameEngine through the GameCreator
- Then another splash screen describing the rules of the game along with a customize button that leads to another screen, 2 player choice boxes, and a start button that initializes the game.
    - Customize screen has check boxes that makes a XML file that is sent to the back end to initialize rules of the game. If no boxes are checked, the game will automatically use default rules
    - The players that are entered will have their player profiles loaded through method calls to the back end. If they don't exist, new files will be made for them.
- Start screen
    - Top bar with buttons to pause, switch turns, timer, reset, load game, and music
    - Board in middle of screen
    - Possibly a text box for comments, a move history pane, and a player information pane.



Model:
```java=

//This class will be extended for game, where the enum is the possible game pieces
abstract class GamePiece() {

    //Global variables, initialized in constructer
    private Coordinate myCoordinates;
    private GameBoard myGameBoard;

    //returns true if move is possible, false if not
    public abstract boolean canMove(newX, newY);
    
    public List<Coordinates> getAllPossibleMoves(Coordinates pieceCoor) {
        
    }

    
    public double getXPosition(){
        return myCoordinates.getX();
    }
    
    public double getYPosition(){
        return myCoordinates.getY();
    }
}


class GameBoard() {
    private List<GamePiece> activePieces;

    public List<Coordinates> getAllActivePieceCoordinates();
    
    public GamePiece getPieceAtCoordinate(Coordinate coordinate);
    //will return Null if no piece at coordinate, so always check when using.
    
    public void movePiece(GamePiece piece, Coordinate endingCoordinate);
    
    public void removePiece(GamePiece gamePiece);
    
    public void changePiece(GamePiece gamePiece, GamePiece newPieceType);
    
    public void addPiece(Coordinate coordinate, GamePiece newPieceType);
    
    public boolean isPieceAtCoordinate(Coordinate coordinate);
    //this method will mostly be used for testing
}


class GameEngine(){
    private GameBoard currentBoard;
    private List<UserInfo> activeUsers;
    
    public void setUpGame(File setupFile);
    //The File parameter is a csv/xml file which will be parsed to set up the game
    
    public boolean makeMove(String move);
    //returns true if turn is over, false if turn is not over
    
    public boolean isGameOver();
    
    public void makeBackendStep();
    
    public void updateFrontEnd();
    
    public void addActiveUser(UserInfo user);
    
    public void clearActiveUsers();
    
}

class XMLParser(){

    public Map<String><String> parseFile(File file);
    
    public void createUserFile(String userName);
    
    public void userWon(String userName);
    
    public void userLost(String userName);
    
    public void userTie(String userName);
    
}

class Player(){
    int numWins;
    int numLosses;
    int numTies;
    String name;
    
    public String getWinLoss();
    public String getName();

}

```


View:

```java=
class BoardView() {
    public void movePiece(Piece piece)
    
    public void removePiece()
}

class 

```

Controller:

```java=

interface BackendExternalAPI(){
    private FrontendExternalAPI frontendAPI;
    
    public void setViewController(FrontendExternalAPI newFrontentAPI);
    
    public void setLanguage(String lang);
    
    public void updateRules(File ruleFile);
    
    public void modePiece(double x, double y);
}

interface FrontendExternalAPI(){
    
    private BackendExternalAPI backendAPI;
    
    public void setModelController(BackendExternalAPI newBackendAPI);
    
    public void setGameType(String gameType);
    
    public void setBoardDimensions(double width, double height);
    
    public void setBoardSpace(double, double, String);
    
    public boolean gameWin();
    
    public boolean gameLoose();
    
    public void changeScore(int newScore);
    
    public void getUserProfile(String userName);
    
    
}

```


### Design Considerations

- How to store boards
    - most likely in a csv file
        - header with game type
        - grid of game piece
            - In parsing, will have to make sure that this alligns with the game type (can't have a pawn in connect four).

- How to store move history
    - most likely in a XML or JSON file
    - each move will store:
        - a list of pieces added (`{position, piece name, player}`)
            - In parsing, will have to make sure that piece name aligns with the game type (can't have a pawn in connect four).
            - In parsing, will have to make sure that player aligns with game type (i.e. black and white for chess)
        - a list of pieces moved ({starting position, ending position})
        - a list of pieces removed ({ending position})
            - Important to note that the first value is the **ending** position.  This allows for a moved piece to be removed without confusion
        - a list of pieces changed ({ending position, new piece})
            - Important to note that the first value is the **ending** position.  This allows for a moved piece to be changed without confusion


- What will each game have in common, which methods are general or specific to games
    - Each game will have pause, hasWon
    - Each game has a set rules, players, a board, and pieces
        - However, the rules and pieces will be different for each game type


- How many different data files to have
    - Right now we have
        - Game rules
        - Piece rules
        - Player
        - Game History (which has 0 moves if it is the default game, or it has a move history associated with it if not)
    - But how these data files are arranged and stored is still up in the air
        - Will the game history file store the names of the players and the rules used to play the game?



### Example games

#### Checkers
Checkers is a very basic example of a grid-based strategy game, given the limited set of moves and its singular piece type. Each piece can only move diagonally one space, and takes pieces by jumping diagonally across them. This will serve as the basic implementation of our game as we only need to be able to calculate a limited set of possible moves and only a single piece type. However, it will also be able to support multiples moves of a piece per turn, as pieces can take more than one piece in a single turn.
#### Chess
Chess is a much more complicated version of checkers as described above, given the seven unique pieces and more complex move set. Though chess is much more complicated than checkers, at its foundation it follows the same structure and is extendable to our model. Our game will, as in checkers, determine the possible moves of every piece and handle the taking of pieces. However, in addition to these two commonalities, chess's rules are stricter and the ending of the game isn't simply limited to taking all of your opponent's pieces.

#### Othello
Othello is an extension to the standard strategy grid games in that instead of "taking" pieces, you flip them to show your color. Whereas in chess and checkers, it is a piece that is either contacting or jumping over that takes, in othello, it's however many pieces that are surrounded vertically, horizontally, or diagonally by the opposing player's pieces. In addition, pieces in othello are never removed as in chess and checkers, so every piece that has been played will remain in the grid until the end of the game.



### Test Plan


#### 

Tests will do both "happy" and "sad" cases. A specific strategy that our team will implement to make our API's are easily testable will be making sure we have good methods. We will ensure we have good methods by ensuring methods have useful paramaters and return values. We will also ensure that methods are small and have a specific purpose.

- Project Feature One:
    - Test Scenario One
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Two
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Three
        - Description of expected result from user action and steps that lead to result:

- Project Feature Two:
    - Test Scenario One
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Two
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Three
        - Description of expected result from user action and steps that lead to result:

- Project Feature Three:
    - Test Scenario One
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Two
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Three
        - Description of expected result from user action and steps that lead to result:

- Project Feature Four:
    - Test Scenario One
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Two
        - Description of expected result from user action and steps that lead to result:
    - Test Scenario Three
        - Description of expected result from user action and steps that lead to result:
