package BoardLayout;

import ChessCore.*;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/** Swing view; game coordinates always remain independent of board orientation. */
public class GameGUI extends JFrame {
    private ClassicChessGame gameBackend = new ClassicChessGame();
    private final List<Move> history = new ArrayList<>();
    private final BoardInitialize boardFrame = new BoardInitialize(this);
    private final JLabel status = new JLabel();
    private final JButton undoButton = new JButton("Undo");
    private Square selected;

    public GameGUI() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(boardFrame.getChessboardPanel(), BorderLayout.CENTER);
        JPanel controls = new JPanel(new BorderLayout());
        controls.add(status, BorderLayout.CENTER);
        controls.add(undoButton, BorderLayout.EAST);
        add(controls, BorderLayout.SOUTH);
        undoButton.addActionListener(event -> undo());
        refresh();
        pack();
        setLocationRelativeTo(null);
    }

    public ClassicChessGame getGameBackend() { return gameBackend; }
    public BoardInitialize getBoardFrame() { return boardFrame; }

    public void selectSquare(Square square) {
        if (gameBackend.isGameEnded()) return;
        Piece piece = gameBackend.getPieceAtSquare(square);
        if (piece != null && piece.getOwner() == gameBackend.getWhoseTurn()) {
            selected = square;
            boardFrame.render(selected);
            return;
        }
        if (selected == null) return;
        PawnPromotion promotion = PawnPromotion.None;
        Piece moving = gameBackend.getPieceAtSquare(selected);
        if (moving instanceof Pawn && (square.getRank() == BoardRank.FIRST
                || square.getRank() == BoardRank.EIGHTH)) {
            Object choice = JOptionPane.showInputDialog(this, "Promote pawn to:", "Promotion",
                    JOptionPane.QUESTION_MESSAGE, null,
                    new PawnPromotion[]{PawnPromotion.Queen, PawnPromotion.Rook,
                        PawnPromotion.Bishop, PawnPromotion.Knight}, PawnPromotion.Queen);
            if (choice == null) return;
            promotion = (PawnPromotion) choice;
        }
        Move move = new Move(selected, square, promotion);
        if (gameBackend.makeMove(move)) {
            history.add(move);
            selected = null;
            refresh();
        }
    }

    /** Replay accepted moves to restore all engine state, including castling and en passant. */
    public void undo() {
        if (history.isEmpty()) return;
        history.remove(history.size() - 1);
        gameBackend = new ClassicChessGame();
        for (Move move : history) {
            if (!gameBackend.makeMove(move)) throw new IllegalStateException("Cannot replay move history");
        }
        selected = null;
        refresh();
    }

    private void refresh() {
        boardFrame.render(selected);
        undoButton.setEnabled(!history.isEmpty());
        switch (gameBackend.getGameStatus()) {
            case WHITE_WON: status.setText("White wins by checkmate"); break;
            case BLACK_WON: status.setText("Black wins by checkmate"); break;
            case STALEMATE: status.setText("Draw: stalemate"); break;
            case INSUFFICIENT_MATERIAL: status.setText("Draw: insufficient material"); break;
            case WHITE_UNDER_CHECK: status.setText("White to move — check"); break;
            case BLACK_UNDER_CHECK: status.setText("Black to move — check"); break;
            default: status.setText(gameBackend.getWhoseTurn() + " to move");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().setVisible(true));
    }
}
