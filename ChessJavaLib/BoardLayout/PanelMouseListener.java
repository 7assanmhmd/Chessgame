package BoardLayout;

import ChessCore.Square;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Handles square buttons, including keyboard activation. */
public class PanelMouseListener implements ActionListener {
    private final GameGUI frame;
    private final Square square;

    public PanelMouseListener(GameGUI frame, Square square) {
        this.frame = frame;
        this.square = square;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        frame.selectSquare(square);
    }
}
