package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.classic.board.Move.MajorAttackMove;
import static com.chess.engine.classic.board.Move.MajorMove;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    private final boolean isCastled;//states that is not initialized but initialized//
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;

    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) {
        super(PieceType.KING, pieceAlliance, piecePosition, true);
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) {
        super(PieceType.KING, pieceAlliance, piecePosition, isFirstMove);
        this.isCastled = isCastled();
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }

    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable();
    }

    @Override//Method does not override method from its superclass//
    public Collection <com.chess.engine.classic.board.Move> calculateLegalMoves(com.chess.engine.classic.board.Board board) {

        final List<com.chess.engine.classic.board.Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if (isFirstColumnExclusion( this.piecePosition, currentCandidateOffset ) || isEighthColumnExclusion( this.piecePosition, currentCandidateOffset )) {
                continue;
            }
            if (com.chess.engine.classic.board.BoardUtils.isValidTileCoordinate( candidateDestinationCoordinate )) {
                Tile candidateDestinationTile = board.getTile( candidateDestinationCoordinate );//.getTile is stated to only rename reference//
                candidateDestinationTile = board.getTile( candidateDestinationCoordinate );
                if (!candidateDestinationCoordinate.isTileOccupied()) {
                    legalMoves.add( new MajorMove( board, this, candidateDestinationCoordinate ));//MajorMove() in MajorMove cannot be applied to//
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add( new MajorAttackMove( board, this, candidateDestinationCoordinate, pieceAtDestination ) );//cannot resolve constructor
                    }
                }
            }

        }
        return ImmutableList.copyOf( legalMoves );
    }

    @Override
    public King movePiece(final Move move) {//states if already defined can you check on that//
        return new King(move.getMovedPiece().getPieceAlliance(),
                move.getDestinationCoordinate(),
                false,
                move.isCastlingMove());
    }

    @Override
    public int locationBonus() {
        return 0;
    }

    @Override
    public Piece movePiece(Move move) {//same for this part//
        return null;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        return null;
    }

    @Override
    protected boolean queenSideCastleCapable() {
        return false;
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
    @Contract(pure = true)
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return com.chess.engine.classic.board.BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);
    }

    @Contract(pure = true)
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return com.chess.engine.classic.board.BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);
    }

    @Override
    public Alliance getPieceAlliance() {
        return null;
    }
}
