# Design.md

## Role(s)

- Casey
    - GamePiece implementation
        - Designed data files
    - PieceMovement implementation
        - Getting of possible moves
        - Executing moves
            - `Restriction` and `Condition` classes
              - Used for en passant, castle, etc
        - Did all of the piece movement implementations for chess
    - Check/Checkmate and win conditions
    - Controller (Integrated front/back end communication)
    - Checkers
    - Player file saving/loading
    - Move history/undo move
    - Helped with `GameEngine` integration
- Cole
    - Primarily worked on `GameEngine` and its components
        - `TurnConditions`
        - `GameBoard`
        - `GameRules`
    - Made connect four implementation
- Shaw
    - `AI`
    - Initializer for the gameboards
- Yi
    - Frontend aesthetics
    - Control flow of windows using `ViewManager`
    - `GameWindow` and `GameScene` factories and subclasses
    - Front end `.properties` and CSS files
    - Frontend tests
- Kenny
    - The BoardScene
        - interactions with buttons and the different
    - Tile and Board classes
    - methods in ViewManager
    - All pieces, alternate and basic, and properties file

## Design Goals
Create a game engine capable of handling any grid-based strategy game (Chess, Checkers, Connect4, Othello, etc.) without hardcoding the gamerules. Flexible design in order to make it easier to handle different games using the same structure, i.e. minimizing the number of game-specific classes.


## High-Level Design

### View
The view of the game is managed by the `ViewManager` class, which creates all `GameWindow` and `GameScene` subclasses and handles button events in all windows.
- At game startup, a `init.properties` file is read to to specify initial window and scene to show, as well as language and game piece image settings.
- All `GameWindow` and `GameScene` subclasses are created by `GameWindowFactory` and `GameSceneFactory`, and they return the abstract versions of the objects they create.
- Dependency injection model is used to give a `GameWindow` a `GameScene` to show. This allows any `GameWindow` type to show ay `GameScene` which has a `Board` with many `Tile`s.
- A unified button handler method is used to handle all click events in all windows using reflection by getting the button's ID.

### Model
- Game is started by the `EngineInitializer`, which implements the `Initializer` interface
    - This makes the GameEngine, and methods can be called to set the players and the initial board state
- Engine takes a set of coordinates from the front end through the runTurn method
    - The TurnManager starts the turn, if it is not currently ongoing
    - The clickExecutor determines what methods to call based on the team and what was clicked. This will result in methods called on the gameboard
        - Highlighting possible moves
        - Unhighlighting possible moves (if the user clicks not on a move)
        - Executing a move
        - Doing nothing
            - If a piece is at the location of the click, the highlight/move execution is done through the GamePiece class, which uses PieceMovement objects
    - Then, the TurnManager is queried again to check if the turn is over
        - Swaps it if so
    - If an AI is being used, the AI is then automatically queried to make it's move


## Assumptions or Simplifications

Assumptions or Simplifications:
-  The implementation of the finiteSlide class makes the assumption that finite slide moves will be in either a directlydiagonal direction, or horizontal/vertical.
    - This is because anything other than these cases makesit unclear what path the piece takes, making it unworkable with the current information that the class gets.
    - If any movements are given that aren't directly vertical/horizontal/diagonal, the potential
      moves may not be calculated properly

- The implementation of both slide classes makes the assumption that once the piece in question can take another piece, the potential slide distance is done.
    - Essentially, the piece can slide until the slide distance is done, or it takes another piece. Once it can take another piece, it can no longer continue sliding, even if that piece isn't in it's way (like if a piece is set up to take pieces not in its path).
    - This assumption was made because in the majority of strategy games, there is no reason why a piece would be taking another one that isn't in its path

- The way the piece files are designed assumes that the board is symmetric and that movements should be defined the same on either side.
    - So checking for a location 2 to the right of a piece will actually check 2 to the left of a piece if it is the team on top

- The max board size is 26x26 due to the letter system that was used in the board file

- Each piece is associated with one game, which is defined in the ``piece`` heading in its respective xml file.

- The game made has a ``starting_states`` defined under the same name



- `TurnCondition` extension filenames end with "TurnCondition.java"

- user chooses colors that make sense in GUI
- user closes out game instead of continuing to play after they have won (especially relevant for Connect4)




## Changes from the Plan
- Frontend Clicks
    - We initially planned on having the frontend pass us sets of 2 clicks, one for selecting a piece and one for then moving it, but we realized that would force the frontend to know which game it's playing, because Chess/Checkers share the 2 click mechanism whereas Connect4 has a single click.
    - We changed this by instead handling every click made on the gameboard within the backend and determining what should happen with each click based on the game. This also allowed the frontend to not worry about clicking on the wrong piece, as the backend would determine what was a valid click.
- ViewManager
    - Originally, we thought to create individual view controllers for each window we create. However, we decided to create a `ViewManager` class that handles the logical of windows and scenes, as well as the button clicks in each scene. `ViewManager` is essentially a unified view controller.

- Switching Othello to Connect4
    - Othello was the last game we planned on implementing, as Chess and checkers share much of the same foundation. After implementing chess, we realized that the difference between othello and chess/checkers was a little to great to handle in the remaining time, so we switched to Connect4 considering that it's a much simpler game and, as such, much easier to implement given our existing framework.

## How to Add New Features
### View
- To add new buttons, add the id and label of the button in the desired place in the properties file for the scene.
- To change pieces in GUI, a button needs to be added that calls updatePieceFolder() in board


### Model
- Adding new features to our project is very simple.  To add a new piece, all one needs to do is create a new `.xml` file for that piece and define its ruleset in the `rules` header. Then, all one needs to do is fill out its movement options (if any) within that file under the `changeX`, `changeY`, `mustTake`, `takeX`, and `takeY` headers, along with any `restrictions` or `conditions` and import this `.xml` file into the `data/gamelogic/pieces` directory
- Similarly, making a new board layout is very simple, all you need to do is create a new `.xml` file and define the game type within the `piece` header.  To define the the width and height, do so in a broad `params` header, under the headings `numcols` and `numrows` resptively.  To define piece starting spots, all one needs to do is define a broad `board` heading and `opponent`, and `user` subheadings.  Within the `opponent` or `user` heading, encapsulate piece names (based on the names of the xml file) within a header defining its location in chess-notation.  Once finished, the `.xml` file should be imported into the `data/gamelogic/starting_states` directory
    - Adding a new `Restriction` or `Condition` class is as simple as implementing the interface and implementing the single method for each interface
    - These can then be used to defined a piece movement
- To Add a new game different steps will need to be done depending on if it is extending existing rules (turn and win conditions), or creating new conditions.  First, no matter what, a `.xml` file must be setup with the game name -- which matches the file name -- specified in a `rules` header.  Along with this, the new game type (either Place or Move) should be specified within a `gameType` header.  Then, if using existing turn and win conditions (see src/ooga/model/components/turnconditions for a list of the current turn condition files, and see src/ooga/model/components/turnconditions for a list of current win condition), simply state these conditions within either a `turnConditions` or `winConditions` header, within a smaller header `condition`.  To create a new win or turn conditions, one would need to make a new file within the respective directory described above which extends either `TurnCondition.java` or `EndGameCondition.java` (depending on what type of condition you are using), implementing the singular public method for each condition type.  Once the `.xml` file is finished, you can store it in the `data/gamelogic/game_rules` directory and use its name (without the `.xml`) as the parameter of the call to `ModelController.setGameType()`; however, more work is needed to be able to choose to load this game in JavaFX.