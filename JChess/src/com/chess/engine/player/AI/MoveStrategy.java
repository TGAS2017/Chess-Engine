package com.chess.engine.player.AI;

import com.chess.engine.board.Board;

public interface MoveStrategy {

   com.chess.engine.classic.board.Move execute(Board board);
}
