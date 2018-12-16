package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.pieces.King;
import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<com.chess.engine.classic.board.Move> legalMoves;
    protected final boolean isInCheck;

    Player(final Board board,
           final Collection<com.chess.engine.classic.board.Move> playerLegals,
           final Collection<com.chess.engine.classic.board.Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegals).isEmpty();
        playerLegals.addAll(calculateKingCastles(playerLegals, opponentLegals));
        this.legalMoves = ImmutableList.copyOf(playerLegals);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    private King establishKing() {
        return (King) getActivePieces().stream().filter(piece ->
                piece.getPieceType().isKing()).findAny().orElseThrow(RuntimeException::new);
    }

    private boolean hasEscapeMoves() {
        return this.legalMoves.stream().anyMatch(move -> makeMove(move).getMoveStatus().isDone());
    }

    public Collection<com.chess.engine.classic.board.Move> getLegalMoves() {
        return this.legalMoves;
    }

    static Collection<com.chess.engine.classic.board.Move> calculateAttacksOnTile(final int tile,
                                                                                  final Collection<com.chess.engine.classic.board.Move> moves) {
        return moves.stream().filter(move -> move.getDestinationCoordinate() == tile)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    public MoveTransition makeMove(final com.chess.engine.classic.board.Move move) {
        if (!this.legalMoves.contains(move)) {
            return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionedBoard = move.execute();
        final Collection<com.chess.engine.classic.board.Move> kingAttacks = Player.calculateAttacksOnTile(
                transitionedBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionedBoard.currentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(this.board, transitionedBoard, move, MoveStatus.DONE);
    }

    public MoveTransition unMakeMove(final com.chess.engine.classic.board.Move move) {
        return new MoveTransition(this.board, move.undo(), move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract WhitePlayer getOpponent();
    protected abstract Collection<com.chess.engine.classic.board.Move> calculateKingCastles(Collection<com.chess.engine.classic.board.Move> playerLegals,
                                                                                            Collection<com.chess.engine.classic.board.Move> opponentLegals);

}
