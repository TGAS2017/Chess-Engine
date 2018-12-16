package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public class MoveTransition {

    private final com.chess.engine.classic.board.Board transitionBoard;
    private Move move;
    private MoveStatus moveStatus;

    public MoveTransition(final com.chess.engine.classic.board.Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveTransition(Board board, MoveStatus illegalMove) {

    }

    public MoveTransition(Board board, Board transitionedBoard, Move move, MoveStatus done) {

    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

public com.chess.engine.classic.board.Board getTransitionBoard() {
    return this.transitionBoard;
}

    public Board getToBoard() {
        return null;
    }

    public long getTransitionMove() {
        return 0;
    }

    public long getFromBoard() {
        return 0;
    }
}