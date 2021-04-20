package ooga.model.components.computer;

import ooga.model.components.GameBoard;
import java.util.Map;


public class ChessAI implements AI{
    private static final Map<String, Integer> levels = Map.of("beginner", 0, "intermediate", 1, "advanced", 2);
    private static final Map<String, Integer> pieceValues = Map.of("pawn", 10, "knight", 30, "bishop", 30, "rook", 50, "queen", 90, "king", 900);
    private int AILevel;

    public ChessAI(String level) {
        this.AILevel = levels.get(level);
    }

    @Override
    public void determineMove(GameBoard board) {

    }

    @Override
    public void makeMove() {

    }
}
