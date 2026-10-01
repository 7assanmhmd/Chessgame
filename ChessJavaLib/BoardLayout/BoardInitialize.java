package BoardLayout;

import ChessCore.*;
import ChessCore.Pieces.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import javax.swing.*;

/** Renders engine squares without modifying the engine board. */
public class BoardInitialize {
    private final GameGUI frame;
    private final Map<String, ImageIcon> icons = new HashMap<>();
    private final JPanel chessboardPanel = new JPanel(new GridLayout(8, 8));

    public BoardInitialize(GameGUI frame) {
        this.frame = frame;
        for (String color : new String[]{"White", "Black"}) {
            for (String type : new String[]{"King", "Queen", "Rook", "Bishop", "Knight", "Pawn"}) {
                String name = color + type;
                URL resource = BoardInitialize.class.getResource("/ChessImages/" + name + ".png");
                if (resource == null) {
                    throw new IllegalStateException("Missing piece image: /ChessImages/" + name + ".png");
                }
                icons.put(name, new ImageIcon(resource));
            }
        }
        chessboardPanel.setPreferredSize(new Dimension(560, 560));
    }

    public JPanel getChessboardPanel() { return chessboardPanel; }

    public void render(Square selected) {
        ClassicChessGame game = frame.getGameBackend();
        boolean flipped = game.getWhoseTurn() == Player.BLACK;
        List<Square> moves = selected == null ? Collections.emptyList()
                : game.getAllValidMovesFromSquare(selected);
        chessboardPanel.removeAll();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = new Square(BoardFile.values()[flipped ? 7 - col : col],
                        BoardRank.values()[flipped ? row : 7 - row]);
                Piece piece = game.getPieceAtSquare(square);
                JButton button = new JButton();
                if (piece != null) button.setIcon(icons.get(imageName(piece)));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setBackground((row + col) % 2 == 0
                        ? new Color(235, 235, 208) : new Color(119, 148, 85));
                if (moves.stream().anyMatch(move -> sameSquare(move, square)))
                    button.setBackground(new Color(190, 170, 80));
                if (sameSquare(selected, square)) button.setBackground(new Color(220, 190, 70));
                if (piece instanceof King && piece.getOwner() == game.getWhoseTurn()
                        && (game.getGameStatus() == GameStatus.WHITE_UNDER_CHECK
                        || game.getGameStatus() == GameStatus.BLACK_UNDER_CHECK))
                    button.setBackground(new Color(220, 90, 90));
                button.setToolTipText(square.getFile() + " " + square.getRank());
                button.addActionListener(new PanelMouseListener(frame, square));
                chessboardPanel.add(button);
            }
        }
        chessboardPanel.revalidate();
        chessboardPanel.repaint();
    }

    private static boolean sameSquare(Square a, Square b) {
        return a != null && b != null && a.getFile() == b.getFile() && a.getRank() == b.getRank();
    }

    private static String imageName(Piece piece) {
        String color = piece.getOwner() == Player.WHITE ? "White" : "Black";
        String type = piece instanceof King ? "King" : piece instanceof Queen ? "Queen"
                : piece instanceof Rook ? "Rook" : piece instanceof Bishop ? "Bishop"
                : piece instanceof Knight ? "Knight" : "Pawn";
        return color + type;
    }
}
