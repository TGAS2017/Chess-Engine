package com.chess;
import com.chess.engine.classic.board.Board;
import com.chess.engine.gui.Table;

public class JChess {

    public static void JChess(String [] args) {

        com.chess.engine.classic.board.Board board = com.chess.engine.classic.board.Board.createStandardBoard();//.board is not recognized//

        System.out.println(board);

        Table.get().show();
    }
}
