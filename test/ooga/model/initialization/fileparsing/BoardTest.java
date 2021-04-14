package ooga.model.initialization.fileparsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.controller.FrontEndExternalAPI;
import ooga.controller.DummyViewController;
import ooga.model.components.GamePiece;
import ooga.model.initialization.BoardCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

public class BoardTest {
    private BoardCreator boardCreator;
    private Map<String, List<Node>> boardNodes;
    private Map<String, String> userPieces;
    private Map<String, String> opponentPieces;
    private GamePiece[][] testBoard;
    private int rows;
    private int cols;
    private FrontEndExternalAPI dummyViewController = new DummyViewController();

    @BeforeEach
    private void setUp(){

    }
    @Test
    void testCreator(){
        boardCreator = new BoardCreator("chess", dummyViewController);
        boardCreator.makeBoard();
    }



}
