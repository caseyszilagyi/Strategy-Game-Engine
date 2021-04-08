package ooga.model.game_initialization.file_parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.BoardCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTesting {
    private BoardCreator boardCreator;
    private GameBoard testBoard;
    private GamePiece[][] gameBoard;

    @BeforeEach
    private void setUp() {
        boardCreator = new BoardCreator("Chess");
        testBoard = boardCreator.makeBoard();
        gameBoard = testBoard.getBoardArray();
    }

    @Test
    void testCreator() {
        boardCreator = new BoardCreator("Chess");
        boardCreator.makeBoard();
    }

    @Test
    void testOpponentPieces() {
        assertEquals("rook", gameBoard[0][0].getPieceName());
        assertEquals("knight", gameBoard[0][1].getPieceName());
        assertEquals("pawn", gameBoard[1][4].getPieceName());
    }

    @Test
    void testUserPieces() {
        assertEquals("pawn", gameBoard[6][0].getPieceName());
        assertEquals("rook", gameBoard[7][0].getPieceName());
        assertEquals("king", gameBoard[7][4].getPieceName());
    }


}
