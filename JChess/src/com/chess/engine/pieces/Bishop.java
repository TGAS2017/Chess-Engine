package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MajorAttackMove;
import com.chess.engine.classic.board.Move.MajorMove;
import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance pieceAlliance,
                  final int piecePosition) {
        super( PieceType.BISHOP, piecePosition, true );//states to create constructor event though the constructor does not affect this piece of code//
    }

    public Bishop(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super( PieceType.BISHOP, pieceAlliance, piecePosition, isFirstMove);
    }

    @Override
    public Collection <Move> calculateLegalMoves(final Board board) {
        final List <Move> legalMoves = new ArrayList <>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (com.chess.engine.classic.board.BoardUtils.isValidTileCoordinate( candidateDestinationCoordinate )) {
                if (isFirstColumnExclusion( currentCandidateOffset, candidateDestinationCoordinate ) || isEighthColumnExclusion( currentCandidateOffset, candidateDestinationCoordinate )) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (com.chess.engine.classic.board.BoardUtils.isValidTileCoordinate( candidateDestinationCoordinate )) {
                    final Piece pieceAtDestination = board.getPiece( candidateDestinationCoordinate );
                    if (pieceAtDestination == null) {
                        legalMoves.add( new MajorMove( board, this, candidateDestinationCoordinate ) );
                    } else {
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAllegiance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add( new MajorAttackMove( board, this, candidateDestinationCoordinate, pieceAtDestination ) );
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf( legalMoves );
    }

    @Override
    protected boolean queenSideCastleCapable() {
        return false;
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus( this.piecePosition );
    }//bishopBonus to be a method

    @Override
    public Bishop movePiece(final Move move) {
        return PieceUtils.INSTANCE.getMovedBishop( move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate() );
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }//made public//

    private static boolean isFirstColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
        return (com.chess.engine.classic.board.BoardUtils.INSTANCE.FIRST_COLUMN.get( candidateDestinationCoordinate ) && ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
        return com.chess.engine.classic.board.BoardUtils.INSTANCE.EIGHTH_COLUMN.get( candidateDestinationCoordinate ) && ((currentCandidate == -7) || (currentCandidate == 9));
    }

    @Override
    public Alliance getPieceAlliance() {
        return null;
    }
}

