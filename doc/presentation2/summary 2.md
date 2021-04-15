# Sprint 1 Presentation

Start by showing off the features of your running program:

run the program from the master branch through a planned series of steps/interactions (including showing how bad data or interactions are handled — if any are yet)

NOTE, as each feature is shown, someone on the team should briefly describe how work they did over the past week relates to it

The Finite State Machine for our Project
- ![Overall FSM diagram](https://i.imgur.com/oIjmmhT.png)

What we've done so far
- ![FSM done diagram](https://i.imgur.com/0jNPDnI.png)

What we need to do
- ![FSM todo diagram](https://i.imgur.com/rY4sjsR.png)


## View

The view hierarchy is show below:
![Imgur](https://imgur.com/J1x5W4s.jpg)

Overall structure:
- `GameWindow` is an interface, and concrete subclasses include `StageWindow`, which extends `Stage`, and `FloatingWindow`, which extends `PopupWindow`. These windows behave differently:
    - `StageWindow` are used for displaying persistent windows that persist for the duration of the game.
    - `FloatingWindow` is used to display configurations, pause menus, alerts, popups, and other views that the user can dismiss. These must have a parent window and will appear floating on top of their parent.
- Each `GameWindow` is associated with at least 1 `GameScene`, which extends `Scene`. A `GameWindow` can switch to other `GameScene`s at any time.
- `GameScene` objects are created using a parent object created by a `FXML` file.
- `GameWindow` and `GameScene` objects are created by their respective factories, so that the interface remains extensible to new windows and scenes.
- Each `GameScene` has its own FXML file, and may share a CSS file with other scenes to maintain unified color scheme.
- At initialization, an `init.properties` data file determines the first `GameWindow` and `GameScene` to show.

## Java Method Examples

```java=

// This method is in the PieceMovement class
public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board, String teamName) {
    List<Coordinate> possibleMoves = new ArrayList<>();
    if (checkIfValidMove(coordinates, teamName)) {
      Coordinate result = makeCoordinate(coordinates.getX() + getChangeX(),
          coordinates.getY() + getChangeY());
      possibleMoves.add(result);
    }
    return possibleMoves;
  }
  
// This method is in the GamePiece class
public Set<Coordinate> getAllLegalMoves() {
    Set<Coordinate> possibleMoveLocations = new HashSet<>();
    for (PieceMovement move : allPossibleMoves) {
      List<Coordinate> currentPossibilities = move
          .getAllPossibleMoves(pieceCoordinates, gameBoard, pieceTeam);
      possibleMoveLocations
          .addAll(currentPossibilities);
    }
    return possibleMoveLocations;
}

```


Initializing the first view:

```java  
public void start(Stage primaryStage) throws Exception {
    
    initFile = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + "init");

    GameWindow initialWindow = getInitialWindow();
    String initSceneName = initFile.getString("initialWindowScene");
    GameScene newScene = sceneFactory.makeScene(initSceneName);
    initialWindow.showScene(newScene);
    initialWindow.makeVisible();
  }
```


## Data Files

### Model

#### Piece File
```xml
<piece piece = "Chess">
  <moves>
    <InfiniteSlide>
      <changeX>1</changeX>
      <changeY>1</changeY>
      <mustTake>False</mustTake>
      <takeX>null</takeX>
      <takeY>null</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>-1</changeX>
      <changeY>1</changeY>
      <mustTake>False</mustTake>
      <takeX>null</takeX>
      <takeY>null</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>1</changeX>
      <changeY>-1</changeY>
      <mustTake>False</mustTake>
      <takeX>null</takeX>
      <takeY>null</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>-1</changeX>
      <changeY>-1</changeY>
      <mustTake>False</mustTake>
      <takeX>null</takeX>
      <takeY>null</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>1</changeX>
      <changeY>1</changeY>
      <mustTake>True</mustTake>
      <takeX>0</takeX>
      <takeY>0</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>1</changeX>
      <changeY>-1</changeY>
      <mustTake>True</mustTake>
      <takeX>0</takeX>
      <takeY>0</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>-1</changeX>
      <changeY>1</changeY>
      <mustTake>True</mustTake>
      <takeX>0</takeX>
      <takeY>0</takeY>
    </InfiniteSlide>
    <InfiniteSlide>
      <changeX>-1</changeX>
      <changeY>-1</changeY>
      <mustTake>True</mustTake>
      <takeX>0</takeX>
      <takeY>0</takeY>
    </InfiniteSlide>
  </moves>
</piece>
```

#### Game Board File
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<piece piece="Chess">
    <params>
        <numrows>8</numrows>
        <numcols>8</numcols>
    </params>
    <board>
        <opponent>
            <a8>rook</a8>
            <b8>knight</b8>
            <c8>bishop</c8>
            <d8>queen</d8>
            <e8>king</e8>
            <f8>bishop</f8>
            <g8>knight</g8>
            <h8>rook</h8>
            <a7>pawn</a7>
            <b7>pawn</b7>
            <c7>pawn</c7>
            <d7>pawn</d7>
            <e7>pawn</e7>
            <f7>pawn</f7>
            <g7>pawn</g7>
            <h7>pawn</h7>
        </opponent>
        <user>
            <a2>pawn</a2>
            <b2>pawn</b2>
            <c2>pawn</c2>
            <d2>pawn</d2>
            <e2>pawn</e2>
            <f2>pawn</f2>
            <g2>pawn</g2>
            <h2>pawn</h2>
            <a1>rook</a1>
            <b1>knight</b1>
            <c1>bishop</c1>
            <d1>queen</d1>
            <e1>king</e1>
            <f1>bishop</f1>
            <g1>knight</g1>
            <h1>rook</h1>
        </user>
    </board>
</piece>

```

### View

#### Example init file to create the first `GameWindow`

```properties
# Specifies the initial view settings of the game

initialWindowType = Stage
initialWindowScene = BoardScene
```

#### Example properties file for a `GameScene`
- The file provides information about the scene size, as well as the names of the structural and stylizing files.
```properties
FXML = BoardScene.fxml
CSS = BoardSceneDefault.css
sceneWidth = 1000
sceneHeight = 1000
```

#### Example FXML file for a `GameScene` with an HBox on top and some buttons

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.image.Image?>
<BorderPane xmlns="http://javafx.com/javafx"
  xmlns:fx="http://javafx.com/fxml"
  fx:controller="ooga.view.FXMLController"
  prefHeight="1000.0" prefWidth="1000.0" styleClass="root">
  <HBox BorderPane.alignment="TOP_CENTER" minHeight="120" styleClass="hbox"
  spacing="20">
    <Button text="TestButton1" id="button" minWidth="80"/>
    <Button text="TestButton2" id="button" minWidth="80"/>
    <Button text="TestButton3" id="button" minWidth="80"/>
    <Button text="AnotherButton" id="button" minWidth="80"/>
  </HBox>

  <stylesheets>
    <URL value="@BoardSceneDefault.css"/>
  </stylesheets>
</BorderPane>

```

#### Example CSS files for stylizing scenes

```css
.root {
  -fx-font-size: 8pt;
  -fx-font-family: "Courier New";
  -fx-base: rgb(67, 67, 67);
  -fx-background: rgb(76, 76, 76);
}

.hbox {
  -fx-border-color: #000000;
  -fx-background-color: #5cbde7;
  -fx-background-radius: 5.0;
  -fx-padding: 8;
}

.button {
  -fx-text-fill: #7e7e7e;
  -fx-background-color: #d4d4d4;
  -fx-border-radius: 10;
  -fx-background-radius: 20;
  -fx-padding: 8;
}

.button:hover {
  -fx-background-color: #3a3a3a;
}
```

## JUnit Tests


```java
/**
   * Tests the creator superclass methods, makes sure that they are functioning properly and that the XML file is able to be parsed as expected
   */
@Test
void testGeneralCreator(){
    rootNodeMap = makeRootNodeMap("knight");
    subNodeMap = makeSubNodeMap(rootNodeMap.get("moves").get(0));
    attributeMap = makeAttributeMap(subNodeMap.get("FiniteJump").get(0));
    checkAttributeMapping("changeX", "1");
    checkAttributeMapping("changeY", "2");
  }

/**
   * Tests faulty file information
   */
@Test
void testWrongGameType(){
    try{
      makeRootNodeMap("normalChecker");
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "InvalidFileType");
    }
  }
  

/**
   * Testing the getLegalMoves for a night in the middle of the board
   */  
@Test
  void testBasicKnightGetMoves(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 4, 4);
    knight.setPieceTeam("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "5:6 5:2: 6:5 6:3 3:6 3:2 2:5 2:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }
  
/**
   * Testing whether the knight can recognize that it can land on other pieces, and can't
   * land on friendly pieces
   *
   * Should be able to take piece at 6:5 but not 5:6
   */
@Test
void testKnightTakeMovement(){
    makeEmptyBoard();
    GamePiece knight = makePiece("knight", 7, 7);
    knight.setPieceTeam("Casey");
    dummyBoard[5][6] = makeDummyGamePiece("notCasey");
    dummyBoard[6][5] = makeDummyGamePiece("Casey");
    knight.setDummyBoard(dummyBoard);
    allLegalMoves = knight.getAllLegalMoves();
    String expected = "6:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal move coordinates for a rook on an empty board
   */
@Test
void TestEmptyBoardRookMovement(){
    makeEmptyBoard();
    GamePiece rook = makePiece("rook", 6, 5);
    rook.setPieceTeam("Casey");
    rook.setDummyBoard(dummyBoard);
    allLegalMoves = rook.getAllLegalMoves();
    String expected = "6:6 6:7 6:4 6:3 6:2 6:1 6:0 0:5 1:5 2:5 3:5 4:5 5:5 7:5";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

  /**
   * Testing the legal moves of a rook on a board with friendly/enemy pieces
   */
@Test
void TestRookWithFriendlyAndOpponentPieces(){
    makeEmptyBoard();
    GamePiece rook = makePiece("rook", 4, 4);
    rook.setPieceTeam("Casey");
    dummyBoard[4][6] = makeDummyGamePiece("notCasey");
    dummyBoard[4][2] = makeDummyGamePiece("notCasey");
    dummyBoard[6][4] = makeDummyGamePiece("Casey");
    dummyBoard[2][4] = makeDummyGamePiece("Casey");
    rook.setDummyBoard(dummyBoard);
    allLegalMoves = rook.getAllLegalMoves();
    String expected = "2:4 3:4 5:4 6:4 4:5 4:3";
    assertTrue(testActualExpectedCoordinates(expected, allLegalMoves));
  }

```

## Implementation Plan/What We learned

## What Was Learned
- Abstracting strange behaviors as much as possible will be key to hard-coding the least amount possible for each game
    - Castling
    - En Passant
    - Pawn promotion
        - These are all special move types, but can still take on the form of a PieceMovement object
- Checkmate requires a lot of work to check
    - For each of the pieces of the player in check
        - Check if any move can get the player out of check
        - This requires checking whether the opponent can check the king for these moves

## Plan For Next Sprint
- Entire Program
    - Link the model and view to be able to move pieces through clicks and highlight possible moves
- Model
    - Work on special move conditions for chess
    - Work on a checkmate algorithm
    - If there is more time
        - Player files
        - Checkers
- View
    - Create storyboard flow logic that can switch between scenes and stages.
    - Connect front end board to board model in backend.
    - Continue to create FXML files for each `GameScene` and
    - Create control classes for each `GameScene` that provide a reliable way for the backend to communicate with the view.

## Planned Features Complete and What Helped/Impeded Progress
- Model
    - All of the non special cases for possible moves were implemented for the pieces
        - Still need to do castling, en passant, pawn promotion
    - Board can be read in from XML file and initializes generic pieces from data files
    - We set out to implement the GamePiece class, and we essentially did that. However, it definitely took a little longer than expected so we didn't get to some of our more ambitious goals for this sprint
- View
    - Created a data-driven view rendering system using FXML that can be highly flexible with no changes to the source code.
    - Initially: tried to invent our own view data files from scratch, which became complicated very quickly.
    - FXML simplified much of the process of creating flexible views.

## Significant Events/Teamwork

- Positive
    - We were initially going to attempt to create a new file format for reading in view configurations, however we discovered FXML which simplifies a lot of the details and leaves us with more freedom to create an extensible view structure. We learned that with data files, we don't always have to reinvent the wheel because there might be existing data file formats created that can be very useful.

- Issues
    - Place piece games (othello, connect 4) and move piece games (checkers, chess) have some core behaviors that are different, but still follow the same logic flow
    - Figuring out how to deal with this was tricky
        - Clicking on a piece for a move game highlights all possible moves
        - Clicking on a spot in a place piece game places the piece
    - Decided that the game engine will need to have a logic flow to determine what to do with coordinates sent from the front end

- Teamwork
    - Slack works very well for us
    - Planning to have mid-week goals to streamline focus on certain features

## Next Sprint
- Casey
    - Start working on piece restrictions/special cases
        - Will need to abstract these behaviors as much as possible.
        - En Passant
        - Castling
        - Pawn promotion
    - Once this is completed, will start working on the player files
- Cole
    - Get gameEngine working such that a series of Actions can be called and a chess game will be run
- Shaw
- Yi
    - Was very busy this week so could not get as much view done as planned
    - Will work more closely with Kenny on the view classes
- Kenny
    - Work with Yi to figure out how to implement control buttons with FXML


