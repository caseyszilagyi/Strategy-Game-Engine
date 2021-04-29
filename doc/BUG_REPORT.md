## Description

Summary of the feature's bug (without describing implementation details)

The current bug is that if the user is playing connect four, and makes a horizontal line not against the left wall it doesn't register that the user won.

## Expected Behavior

Once the user gets four in a row horizontally, no matter where on the board, it will register as a win.

## Current Behavior

If the user is playing connect four, and if they make a horizontal four in a row against the left wall, it registers a win. However, if the horizontal four in a row is not against the left wall it doesn't register a win

## Steps to Reproduce

1. Run Main.java
2. Click the Connect Four button
3. Enter any player name for player 1 and player 2
4. Click the Start button
5. Click the tile 3 from the left at the top of the board
6. Click the tile 3 from the left at the top of the board again
7. Click the tile 1 to the right of that prior tile
8. Click that same tile again
9. Click the tile 1 to the right of that prior tile
10. Click that same tile again
11. Click the tile 1 to the right of that prior tile

## Failure Logs


## Hypothesis for Fixing the Bug

In the FourInARow.java method, within the private checkRight method, on line 92 ```int newX = x - counter;``` needs to be changed to ```int newX = x + counter;```

The test testRightWinConditionNotOnWall within the BasicPlaceGameTest.java class should pass if this issue is resolved.