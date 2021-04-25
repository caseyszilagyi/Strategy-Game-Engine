package ooga.model.components.computer;

import ooga.controller.BoardController;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChessAI{
    private static final Map<String, Integer> levels = Map.of("beginner", 0, "intermediate", 1, "advanced", 2);
    private static final Map<String, Integer> pieceValues = Map.of("pawn", 10, "knight", 30, "bishop", 30, "rook", 50, "queen", 90, "king", 900);
    private BoardController controller;
    private int AILevel;

    public ChessAI(String level) {
        this.AILevel = levels.get(level);
        this.controller = new BoardController();
    }
    //random movements for now
    @Override
    public void determineMove(GameBoard board) {
        Coordinate randomPieceCoords = chooseRandomPiece(board.getPieceCoordinateMap());
        GamePiece randomPiece = board.getPieceAtCoordinate(randomPieceCoords);
        List<Coordinate> moves = new ArrayList<>(randomPiece.determineAllLegalMoves());
        makeMove(randomPieceCoords, moves.get((int) (Math.random() * moves.size())));
    }

    private Coordinate chooseRandomPiece(Map<Coordinate, GamePiece> pieceCoordinateMap) {
        List<Coordinate> coordinateList = new ArrayList<>(pieceCoordinateMap.keySet());
        int randomIndex = (int) (Math.random() * coordinateList.size());
        return coordinateList.get(randomIndex);
    }

    @Override
    public void makeMove(Coordinate startingCoord, Coordinate endingCoord) {
        controller.movePiece(startingCoord.getX(), startingCoord.getY(), endingCoord.getX(), endingCoord.getY());
    }

    @Override
    public AI createGameAI() {
        return null;
    }
}
