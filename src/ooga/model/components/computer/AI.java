package ooga.model.components.computer;

import ooga.model.components.GameBoard;

public interface AI {
    void determineMove(GameBoard board);
    void makeMove();
}
