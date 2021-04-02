#Sprint Plan
## Names and NetIDs
Kenneth Moore III (km460)
Shaw Phillips (sp422)
Yi Chen (yc311)
Cole Spector (cgs26)
Casey Szilagyi (crs79)

## First Sprint

- Overall plan of the first sprint is to get a visual display of the board up with chess pieces that can move and take
- We are not expecting all of the rules to be fully implemented, player profiles to be working fully, or rules to be fully editable
- View priorities
    - Get a visual display of the board up with the ability to represent different pieces
        - If this is done, try to work on piece highlighting and the control bar at the top

- Model priorities
    - Get a working version of the game engine running. This includes the dynamics of loading the base version of the game
        - Not overly concerned with the complexities of storing player information yet, as we just want a playable version of the game
        - Data files of priority are the game data file, which keeps track of the board, moves and move history
        - The piece classes should be implemented by then and should be relatively bug free
    - Specific features that this work can be broken into:
        - Reading in XML files for the
            - Board
            - Player
            - Rules
            - Pieces
        - Loading the classes corresponding with these XML files, and setting up the board
        - Working on the logic in each of the piece classes
        - The game engine
            - Methods it will need to run a game
            - How it will communicate with the front end

    - For these features to be implemented properly, the APIS can be roughly split up as follows
        - XMLParser
            - Methods for getting elements, tags, and then storing this information back in an XML file
            - Each type of data file will also have their own APIS for parsing through data
        - Piece and Board
            - The methods that pieces have to communicate with each other, and get necessary information from the board
        - Engine
            - The necessary methods to ensure the game runs smoothly from move to move
            - Will use methods from the board, piece, and rules classes
        - Rules
            - How the engine will interface with the rules of the game
        - Player
            - The information that the player will be stored
            - Methods to modify a players information
    
## Second Sprint

- Ideally, the second sprint will have a functional working version of the game of chess.
- There will be no moves that are illegal that the player will be able to make
- At this point player files, board storage, and rule changing should be almost completed
or in effect
  
- There should be a relatively clear path to implementing checkers at this point
