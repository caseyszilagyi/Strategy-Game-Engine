package ooga.model.components.computer;

import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AI {
    private String game;
    private String level;

    public AI(String game, String level){
        this.game = game;
        this.level = level;
    }
    private void determineMove(GameBoard board) {
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
    public void makeMove(Coordinate startingCoord, Coordinate endingCoord) {
        controller.movePiece(startingCoord.getX(), startingCoord.getY(), endingCoord.getX(), endingCoord.getY());
    }
}
