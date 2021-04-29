# API_Changes.md

## Back End External

- The back end external API, used by the front end to call methods on the back end, is completely contained within the BackEndExternalAPI interface in the controller package
- Original Methods:
    - setViewController
    - setLanguage(String)
    - updateRules(File)
    - movePiece(x,y)

- The biggest change
    - movePiece was changed to actOnCoordinates
        - We decided that the front end should just be passing coordinates to the back end every time the board is clicked. That way, the front end doesn't have to know when a piece is being moved or where the pieces are.
- Most changes were implemented due to features that we hadn't originally considered in depth
    - setBoardState
        - Allows the user to set a custom board file
        - Late in the project, we added another one of these methods that take a File in addition to the one that takes a String, for the file chooser on the front end
    - setGameType
        - To chose what game to play
    - setPlayers
        - Chooses the names of the players that will play
        - Back end loads in custom files
    - undoTurn
        - Updates the back end when the corresponding button is hit on the front end
- Essentially, most changes were made becasue we didn't fully consider how information would be passed around upon initialization of the game
    - Once we did this, we found it pretty easy to agree upon standardized methods
        - Most methods took strings, which made the changes easy to understand


## Back End Internal

### Piece

- Most changes were because we initially anticipated the piece doing less
    - We thought the game board would keep track of this
    - We soon realized that having a piece keep track of more was going to make more sense
        - Added getter/setter methods to account for this for:
            - Coordinates
            - Team
            - Piece type
        - Also have more methods to determine moves
            - Instead of just legal moves, we can get take moves and restrictionless moves (useful for chess).
            - Can also see if a piece has a take move

        - Pieces also execute their moves
            - This is easier because then the piece can execute the move via the corresponding PieceMovement object
            - All the information for move execution is contained within that

### PieceMovement

- This API was newly designed towards the beginning of the project
    - We realized that definining and implementing each piece class would not be flexible
    - Instead, we chose to have each piece have PieceMovement objects associated with it
        - These can have Restrictions set
        - They can also have Conditions set
- We felt that this made our code flexible, becuase any piece can have any number of piece movement objects
    - Since this change didn't affect anything outside the GamePiece class, it didn't have a large effect on other parts of the program

### GameBoard

- Ended up being much more complicated then we originally anticipated
- The core structure remained the same
    - Add Piece
    - Remove Piece
    - Move Piece
        - Have methods for all of these
        - Added methods that took coordinates, as well as ints for convenience
        - Also had to add methos that could move pieces only on the backend, for the purpose of determining check/checkmate
    - The fact that the core structure was kept the same made it easy to progress through the project without a large impact on other components of the project
- Determined that more query methods were needed
    - Needed these to determine legal piece movement locations
    - Can check if
        - Any piece is at a location
        - A friendly/enemy piece is at a location
        - If a location is a legal move for the active piece
    - These query methods fit nicely into the existing structure
- Also, the determining of legal moves is done through the gameboard by calling the method on the appropriate piece
    - Because it has all the coordinates of pieces
    - This was not anticipated in the design
- There are also methods to keep track of move history
    - This means the board has to
        - Count turns
        - Can undo turns
- Essentially, we felt that the GameBoard was very well equipped to handle the tasks of querying/moving pieces.
    - We didn't anticipate the usefullnes of querying information from a piece with certain coordinates at the beginning

### Engine

- The design of the engine API remained relatively stable, with few modifications that affected the outside
    - We originally thought a move would be passed in with a string, but quickly realized that a simple set of coordinates each time the board was clicked would suffice
    - API was refactored to have ClickExecutor and TurnManager about half way through
        - These changes did not affect interactions for users of the API
- Had to add methods for
    - undoTurn
    - setAI
    - Simply did not realize at the beginning that these features would run through the engine
-

### Initializer

- This API remained pretty much the same through out the project
- We always knew we would have a game type, players, and a board
    - Didn't need to change anything as the project went on to keep this consistent

### Rules

- This was something that we knew we would need, but we didn't quite know how it would fit into our program at the beginning
- We soon came to realize that the main components of the rules are
    - When turns are switched
    - When someone wins
    - The rest can be left to the pieces
- Our rules file reflected this
    - checkWinConditions
    - checkForNextTurn
- Also have other convenience methods, like a getter for gameName and a setter for the board


## Front End External

- The front end external API, used by the back end to call methods on the front end, is completely contained within the FrontEndExternalAPI interface in the controller package
- Original Methods
  * public void setModelController(BackendExternalAPI newBackendAPI);
  - public void setGameType(String gameType);
  * public void setBoardDimensions(double width, double height);
  * public void setBoardSpace(double, double, String);
  * public boolean gameWin();
  - public boolean gameLoose();
  - public void changeScore(int newScore);
  - public void getUserProfile(String userName);

- New methods
    - public void movePiece(int startX, int startY, int endX, int endY);
    - public void removePiece(int row, int column);
    - public void giveAllPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves)
    - public void givePieceChangeOptions(Iterable< String> pieceChangeOptions);

- Changes
    - setBoardDimensions() with ints
    - setBoardSpace with two Strings to identify piece
    - gameWin() has String parameter for who won so that no need for GameLoose(), as whoever did not win, lost
    - eliminated methods:
        - setGameType()
            - this method is in Bank End External
        - gameLoose()
            - no longer needed because of the change to gameWin()
        - changeScore()
            - we did not implement a score feature
        - getUserProfile()
            - our profile system is basic and not integrated in front end

## Front End Internal

### Tile

- designed at beginning of project as the basis of board
- methods
    - public ImageView getPiece()
    - public void addPiece(ImageView piece)
    - public void removePiece()
    - public void changeColor(Color color)
        - used to temporarily change color (mostly highlight)
    - public void unHighlight()
    - public void setColor(Color color)
        - sets instance variable to color and then calls changeColor
            - so that whenever a color is edited it all flows to same place, for easier changing if need be

### Board
- designed at beginning of project in order to represent backend
- methods
    - public void createBoard(int width, int height)
    - public void colorLightSquares(Color color)
        - for button in gui, also used when board is setup for default color
    - public void colorDarkSquares(Color color)
        - for button in gui, also used when board is setup for default color
    - public void movePiece(int startX, int startY, int endX, int endY)
    - public void showPossibleMoves(Iterator<Pair<Integer, Integer>> possibleMoves)
    - public void unhighlightAll()
    - public void highlightTile(int x, int y)
    - public void setHighlightColor(Color color)
    - public ImageView removePiece(int x, int y)
    - public boolean spaceIsEmpty(int x, int y)
    - public void addPiece(int x, int y, String color, String fileName)
        - for initial piece set up
    - public void setColorsDefault()
        - for button in gui
    - public void updatePieceFolder(String directory)
        - unimplemented, to update pieces in gui midgame
    - public void setPieceFolder(String directory)

