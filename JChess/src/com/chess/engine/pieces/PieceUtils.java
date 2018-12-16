package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.gui.Table;
import com.google.common.collect.ImmutableTable;

public enum PieceUtils {

    INSTANCE;

    private final Table<Alliance, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleMovedQueens();//change the field, change the signature of"Table", migrate, or make "AllPossibleMovedQueens"
    private final Table<Alliance, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleMovedRooks();//following comment applies//
    private final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();//following comment applies//
    private final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();//following comment applies//
    private final Table<Alliance, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();//following comment applies//

    Pawn getMovedPawn(final Alliance alliance,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(alliance, destinationCoordinate); //make "getMovedPawn" return to com.chess.engine.gui.Table, change signature of get(Alliance, int), create method get in Table, or remove get//
    }

    Knight getMovedKnight(final Alliance alliance,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(alliance, destinationCoordinate);//make "getMovedKnight" return to com.chess.engine.gui.Table, change signature of get(Alliance, int), create method get in Table, or remove get//
    }

    Bishop getMovedBishop(final Alliance alliance,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(alliance, destinationCoordinate);//make "getMovedBishops" return to com.chess.engine.gui.Table, change signature of get(Alliance, int), create method get in Table, or remove get
    }

    Rook getMovedRook(final Alliance alliance,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(alliance, destinationCoordinate);//make "getMovedRooks" return to com.chess.engine.gui.Table, change signature of get(Alliance, int), create method get in Table, or remove get
    }

    public Table getMovedQueen(final Alliance alliance,
                               final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(alliance, destinationCoordinate);//make "getMovedQueen" return to com.chess.engine.gui.Table, change signature of get(Alliance, int), create method get in Table, or remove get
    }

    private static ImmutableTable<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {
        final ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < com.chess.engine.classic.board.BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Pawn(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static ImmutableTable<Alliance, Integer, Knight> createAllPossibleMovedKnights() {
        final ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < com.chess.engine.classic.board.BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Knight(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static ImmutableTable<Alliance, Integer, Bishop> createAllPossibleMovedBishops() {
        final ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < com.chess.engine.classic.board.BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Bishop(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static ImmutableTable<Alliance, Integer, Rook> createAllPossibleMovedRooks() {
        final ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < com.chess.engine.classic.board.BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Rook(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static ImmutableTable<Alliance, Integer, Queen> createAllPossibleMovedQueens() {
        final ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < com.chess.engine.classic.board.BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Queen(alliance, i, false));
            }
        }
        return pieces.build();
    }

}
