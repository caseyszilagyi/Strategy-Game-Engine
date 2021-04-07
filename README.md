ooga
====

This project implements a player for multiple related games.

Names:


### Timeline

Start Date: 

Finish Date: 

Hours Spent:

### Primary Roles


### Resources Used


### Running the Program

Main class:

Data files needed: 

Features implemented:



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

Interesting data files:

Known Bugs:

Extra credit:


### Impressions

