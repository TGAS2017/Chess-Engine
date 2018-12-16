package com.chess.engine.player.AI;

import com.chess.engine.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public final class StandardBoardEvaluator implements BoardEvaluator {

private static final int CHECK_BONUS = 50;
private static final int CHECK_MATE_BONUS = 10000;
private static final int DEPTH_BONUS = 100;
    private static final int CASTLED_BONUS = 60;

    @Override
    public int evaluate(final Board board,
                        int depth) {
        return scorePlayer(board.whitePlayer(), depth) -
               scorePlayer(board.blackPlayer(), depth);
    }

    private int scorePlayer(WhitePlayer whitePlayer, int depth) {
        return 0;
    }

    private int scorePlayer(final Board board,
                            final Player player,
                            final int depth) {
        return pieceValue(player) +
                mobility(player) +
                check(player) +
                checkmate(player, depth) +
                castled(player);
        // +checkmate, check, castled, mobility....
    }

    private int castled(Player player) {
        return player.isCastled() ? CASTLED_BONUS : 0;
    }

    private int checkmate(Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }
    private int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for(final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();

        }

        return pieceValueScore;
    }
}
