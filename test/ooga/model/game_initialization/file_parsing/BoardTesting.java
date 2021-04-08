package ooga.model.game_initialization.file_parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.model.game_components.GamePiece;
import ooga.model.game_initialization.BoardCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTesting {
    private BoardCreator boardCreator;
    private GamePiece[][] testBoard;

    @BeforeEach
    private void setUp() {
        boardCreator = new BoardCreator("Chess");
        testBoard = boardCreator.makeBoard();
    }

    @Test
    void testCreator() {
        boardCreator = new BoardCreator("Chess");
        boardCreator.makeBoard();
    }

    @Test
    void testOpponentPieces() {
        assertEquals("rook", testBoard[0][0].getPieceName());
        assertEquals("knight", testBoard[1][0].getPieceName());
        assertEquals("pawn", testBoard[4][1].getPieceName());
    }

    @Test
    void testUserPieces() {
        assertEquals("pawn", testBoard[0][6].getPieceName());
        assertEquals("rook", testBoard[0][7].getPieceName());
        assertEquals("king", testBoard[4][7].getPieceName());
    }


}
