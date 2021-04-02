1. Player creates profile by entering a new name
  * when starting the game, and "Please enter player names:" box is up, and the user types in a new name
  - Calls BackendExternalAPI .addPlayer with the parameter being the name input
    - Check to see if *name*Profile.xml file exists, will return false because there is none
  - Call XMLParser .createUserProfile with username as the parameter
  - a Player object will be created from that file (will have 0 wins, losses and ties, and the name will be the same as before)
  - Call GameEngine .addActiveUser with the UserInfo object as the parameter.
2. Player logs into an existing profile by entering an existing name
  * when starting the game, and "Please enter player names:" box is up, and the user types in a new name
  - Calls BackEndExternalAPI .addPlayer with the parameter being the name input
    - Check to see if *name*Profile.xml file exists, will return true because there is one
  - a Player object will be created from that file by reading in the info
  - Call GameEngine .addActiveUser with the UserInfo object as the parameter.
3. The game is over and someone wins, updating the player files
  - GameEngine will Call XMLParser .userWon with the UserInfo of the player who won
    - This will update the XMLFile
  - GameEngine will Call XMLParser .userLost with the UserInfo of all the players who lost
    - This will update the XMLFile
  - Private methods will update the UserInfo of the winner and loser.
  - FrontEndExternalAPI gameWin method will be called and passed the player name
4. The game is over and it is a tie, updating the player files
  - GameEngine will Call XMLParser .userTie with the UserInfo for all the players.
    - This will update the XMLFile
  - Private methods will update the UserInfo of the winner and loser.
5. Player clicks on chess to start chess game
  - Calls modelController setGameType("Chess")
  - This leads to a chain of commands to call the gameCreator initializeGame("Chess"), which will initialize chess with the default pieces
    - Will use the XMLParser and GameClassLoader to read in the information
  - These steps will make an engine, and will be prepared to receive player information detials and rule update details if the player chooses to do so
6. Player clicks on chess and chooses a special rules box
  - View method clickBox()
  - makes a file with an XMLParser that has the details of the special rules, based on which boxes were checked
  - This new file name is passed to the back end through the setGameRules method, which takes the file name and makes a GameRules object
  - This will go to the gameEngine method setRules, which takes a GameRules object.
7. Player clicks a knight, highlighting all possible moves
  - A modelController method getAllPossibleMoves is called, and passes the coordinates of the clicked box
  - This results in a chain of commands to the board, which calls the same method on the piece.
  - The board then calls a method on the viewController called giveAllPossibleMoves that passes the coordinates of the possible moves up to highlight

8. Player clicks a pawn that can't move
  - A modelController method getAllPossibleMoves is called, and passes the coordinates of the clicked box
  - This results in a chain of commands to the board, which calls the same method on the piece.
  - The board then calls a method on the viewController called giveAllPossibleMoves that passes an empty data structure
9. Player moves a pawn to the end of the board, prompting the choice of a new piece
  - The `View` calls the `BackendExternalAPI .movePiece` with the starting `Coordinate` of the pawn, and the `Coordinate` the pawn is moving to
  - `BackendExternalAPI` calls `GameEngine .movePiece` with the same parameters above
  - `GameEngine` calls `GameBoard .movePiece` with same parameters
  - `GameBoard` calls .move on the pawn with the end `Coordinate` as the only parameter
    - This will create a PieceAction object
  - `PawnPiece` will call an EventHandler which will prompt the view and return the piece to change into.
  - `PawnPiece` will call `XMLParser .addMove` with the `PieceAction` as a parameter:
10. Player clicks pause button, stopping the timer
11. Player adds a comment to their opponent's profile
12. Player adds a comment to the game
13. Player clicks on an opponents piece to try to move it
  - The `View` calls the `BackendExternalAPI .canAugmentPiece'
  - `BackendExternalAPI` calls `GameEngine .isPieceAugmentable`
  - `GameEngine` calls `GameBoard .getAugmentablePieces` on all pieces
  - `GameBoard` calls `Piece .getOwner` and `Piece .getCanMove` for each piece
    - This will return true/false, which will help create the List of `Pieces`, which will be checked for the Piece in question
16. Player tries to double jump when double jump is turned off in checkers rules
17. Chess game ends in stalemate, the king can't move but is not currently in check
18. A player forfeits the game
19. The player chooses a board that is parsed through and initialized
20. Player restarts a game
22. Player saves a game
23. player chooses a file for initial game configuration
24. Player chooses a rule set that is parsed through and made into a GameRules object
25. Create a data file from the selected rule checkboxes
26. Player exits the game, returning to the game selection screen
  - The View loads the splashscreen displaying game selection, resetting the game board
27. Player tries to flip opponent pieces in Othello
  - The controller will call `addPiece` within `GameBoard` to add the recently placed piece in addition to calling `changePiece` to "flip" all the captured pieces
28. Player tries to move a different piece while in check
29. "Smart" player is chosen to be played against
30. Player goes from settings screen back to shelf screen
31. Player presses help icon
32. Player presses undo button in chess
33. Given the option to take the user's piece, the computer will choose to take
34. If there are no pieces for the computer to take, randomly choose a move from the available pieces
35. Players  gets to choose if want to complete double jump for checkers
36. A King tries to go backwards
37. Player wants to edit the board, prompting dynamic GUI mode that allows any piece to be moved anywhere
38. Player clicks piece and gets to see different possible moves.
39. A player chooses 2 move at a time chess rules
40. A player chooses to play chess with time limits on both sides
41. A player choses to play chess with no time limits
42. A player clicks to castle
43. A player choses to play wrap-around chess
44. A player sets up a unique initial configuration of chess
45. PLayer changes the color configuration of board
46. Player jumps more than once in a single turn in checkers
47. Player places a piece that flips more than a single line of opponents pieces in Othello, i.e. horizontally and diagonally
48. If the computer's piece is threatened, choose to move that piece even if there is an option to take
49. When the king is put in check, only allow moves that would un-check the king
  - The `View` calls the `BackendExternalAPI .movePiece` with the starting `Coordinate` of the pawn, and the `Coordinate` the pawn is moving to
  - `BackendExternalAPI` calls `GameEngine .movePiece` with the same parameters above
  - `GameEngine` calls `GameBoard .movePiece` with same parameters
  - `GameBoard` calls .move on the pawn with the end `Coordinate` as the only parameter
    - This will create a PieceAction object
  - `PawnPiece` will call an EventHandler which will prompt the view and return the piece to change into.
  - `PawnPiece` will call `XMLParser .addMove` with the `PieceAction` as a parameter: