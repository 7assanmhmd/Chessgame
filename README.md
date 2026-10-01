# Chessgame

Java 17 Swing chess game with legal-move highlighting, automatic board rotation,
pawn promotion, and undo. Undo replays the move history to restore the full game state.

## Build and run

Install JDK 17 or newer and Apache Ant, then run:

```sh
ant clean jar
java -jar dist/Chessgame.jar
```

Alternatively, open this folder as a NetBeans project and use Run Project.
Java sources live in `ChessJavaLib/ChessCore` and `ChessJavaLib/BoardLayout`.

Select a piece, then a highlighted destination. The board rotates after each move.
The Undo button restores the previous move. All twelve piece images are loaded
from `ChessJavaLib/ChessImages/` as classpath resources and included in the built
JAR, so the game does not depend on machine-specific image paths.

## Known limitations

- Castling validation does not reject castling out of check, verify that the
  matching rook is present, or check B1/B8 occupancy for queenside castling.
  Capturing a rook also does not revoke its castling right.
- En passant move simulation does not remove the captured pawn before checking
  king safety, so it can incorrectly accept or reject moves.
- Insufficient-material detection is simplified and can incorrectly declare a
  draw when each side has a single bishop or knight in addition to its king.
- Repetition and move-count draw rules are not implemented.

## Credits and licensing

See [ATTRIBUTION.md](ATTRIBUTION.md) for the available source and asset credits.
The project owner confirms that all supplied code and image assets are their
original work. The project owner has chosen not to grant a reuse license at this time.
