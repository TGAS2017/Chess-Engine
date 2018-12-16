package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.classic.board.Move.MajorAttackMove;
import static com.chess.engine.classic.board.Move.MajorMove;


public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Alliance pieceAlliance,
                  final int piecePosition) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);//constructor affects Piece class in errors//
    }

    public Knight(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT, pieceAlliance, piecePosition, isFirstMove);
    }


    @Override
    public ImmutableList<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;//create variable, create field 'currentCandidateOffset, create parameter 'currentCandidateOffset'
            if (com.chess.engine.classic.board.BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExculsion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExculsion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationCoordinate.isTileOccupied()) {//Do I create isTileOccupied as a method//
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();//Do I create a method for getPieceAlliance//
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    protected boolean queenSideCastleCapable() {
        return false;
    }

    @Override
    public int locationBonus() {
        return 0;
    }

    @Override
    public Piece movePiece(Move move) {
        return null;
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    @Contract(pure = true)
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {

        return com.chess.engine.classic.board.BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10) ||
                candidateOffset == 6 || candidateOffset == 15;
    }

    @Contract(pure = true)
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return com.chess.engine.classic.board.BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10) || candidateOffset == 6;
    }

    @Contract(pure = true)
    private static boolean isSeventhColumnExculsion(final int currentPosition, final int candidateOffset) {
        return com.chess.engine.classic.board.BoardUtils.SEVENTH_COLUMNS[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    @Contract(pure = true)
    private static boolean isEighthColumnExculsion(final int currentPosition, final int candidateOffset) {
        return com.chess.engine.classic.board.BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15) || candidateOffset == -6 ||
                candidateOffset == 10 || (candidateOffset == 17);


    }

}