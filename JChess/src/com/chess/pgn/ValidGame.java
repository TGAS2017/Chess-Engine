package com.chess.pgn;

import com.chess.pgn.Game;
import com.chess.pgn.PGNGameTags;

import java.util.List;

public class ValidGame
        extends Game {

    public ValidGame(final PGNGameTags tags,
                     List<String> moves,
                     final String outcome) {
        super(tags, moves, outcome);
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
