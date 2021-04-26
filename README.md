ooga
====

This project implements a player for multiple related games.

Names:
Kenneth Moore III (km460)
Shaw Phillips (sp422)
Yi Chen (yc311)
Cole Spector (cgs26)
Casey Szilagyi (crs79)

### Timeline

Start Date: 
March 25, 2021

Finish Date: 
April 25, 2021

Hours Spent:

Kenneth: 

Shaw:

Yi:

Cole: 20+ hours per week

Casey: ~25 hours per week

### Primary Roles

Kenneth:

Shaw:

Yi:

Cole: GameEngine, and associated classes

Casey:
  - Pieces
    - Data files
    - Initialization of classes
    - Movements/restrictions/conditions
      - Determining possible moves
      - Executing moves
  - Special chess moves (castle, en passant, etc)
  - Check/checkmate
  - Checkers
  - Player files reading in/storage
  - Move history/undo move
  - Front/back end integration (controller)


### Resources Used
https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
https://guava.dev/releases/24.0-jre/api/docs/com/google/common/base/Stopwatch.html
https://www.howtogeek.com/357092/what-is-an-xml-file-and-how-do-i-open-one/
https://stackoverflow.com/questions/24917053/collecting-hashmapstring-liststring-java-8
https://www.youtube.com/watch?v=u0I6ob8GQBg

### Running the Program

###Main class:
Main.java

###Data files needed: 
For AI: 
data/AI/chess.xml

For game rules:
data/gamelogic/gamerules/

checkers.xml

chess.xml

connectfour.xml

For basic pieces:
data/gamelogic/pieces/

bishop.xml

finiteSlideBiship.xml

king.xml

kingChecker.xml

knight.xml

normalChecker.xml

normalConnectFour.xml

pawn.xml

queen.xml

rook.xml

For basic starting states:
data/gamelogic/starting_states/

chess.xml

connectFour.xml

checkers.xml

Features implemented:

Basic:

Load Games

Preferences

Mild:

Artificial Players

Player Profiles




### Notes/Assumptions

Assumptions or Simplifications:
-  The implementation of the finiteSlide class makes the assumption that finite slide moves will be in either a directly
diagonal direction, or horizontal/vertical. This is because anything other than these cases makes
it unclear what path the piece takes, making it unworkable with the current information that the
class gets. If any movements are given that aren't directly vertical/horizontal/diagonal, the potential 
   moves may not be calculated properly

- The implementation of both slide classes makes the assumption that once the piece in question can take
another piece, the potential slide distance is done. Essentially, the piece can slide until
  the slide distance is done, or it takes another piece. Once it can take another piece,
  it can no longer continue sliding, even if that piece isn't in it's way (like if a piece
  is set up to take pieces not in its path). This assumption was made because in the majority
  of strategy games, there is no reason why a piece would be taking another one that
  isn't in its path
  
- Each piece is associated with one game, which is defined in the ``piece`` heading in its respective xml file.

- The game made has a ``starting_states`` defined under the same name

Interesting data files:

finiteSlideBiship.xml

Known Bugs:
 - When the user goes to double jump in checkers, there is no restriction
on what piece is moved the second time
   
- Stalemate is not detected in chess, which can lead to a board where
no pieces can be moved and yet the game is not over


Extra credit:

Our game engine can handle any input game (be it a placement or a movement game) which follows the same movement patterns as either chess, checkers, or connect four so long as it is given correctly formatted xml files, and can run any game if provided with complemntary TurnCondition and WinCondition files as well.

### Impressions

Kenneth:

Shaw:

Yi:

Cole: 
  I really liked the vagueness of this project, and how we were allowed to focus on the aspects of game design that mattered to us.  While there were specifications on how to do something, the broad scope of the project allowed us to create a GameEngine which can handle practically any strategy game.  We were allowed to do this because we didn't have to focus on specific design specifications provided to us, and could instead be creative and implement aspects to our game that we found interesting.  On the team front, I thought we worked very well together, as everybody communicated in the Slack whenever we needed something done, or if we had questions about code we were implementing, or code somebody else in the group had implemented.

Casey:
  - I liked that we were able to choose what type of game we wanted to work on,
as it allowed us to choose a game based on what we wanted to focus on