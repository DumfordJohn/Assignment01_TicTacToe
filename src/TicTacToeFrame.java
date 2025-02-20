import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame implements ActionListener {
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private boolean xTurn = true;
    private int moveCount = 0;
    JButton quitBtn;
    JPanel mainPnl, titlePnl, boardPnl, cmdPnl, playerPnl;
    JLabel titleLbl, playerLbl;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.75);
        int height = (int) (screenSize.height * 0.75);
        setSize(width, height);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPnl = new JPanel(new BorderLayout());
        createTitlePanel(mainPnl);
        createPlayerPanel(mainPnl);
        createBoard(mainPnl);
        createQuitPanel(mainPnl);

        add(mainPnl);
        setVisible(true);
    }

    public void createTitlePanel(JPanel mainPnl){
        titlePnl = new JPanel();
        titleLbl = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 30));
        titlePnl.add(titleLbl);
        mainPnl.add(titlePnl, BorderLayout.NORTH);
    }

    public void createPlayerPanel(JPanel mainPnl){
        playerPnl = new JPanel();
        playerLbl = new JLabel("Turn: X", SwingConstants.CENTER);
        playerLbl.setFont(new Font("Arial", Font.BOLD, 20));
        playerPnl.add(playerLbl);
        mainPnl.add(playerPnl, BorderLayout.SOUTH);
    }

    public void createQuitPanel(JPanel mainPnl){
        cmdPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent e) -> System.exit(0));
        cmdPnl.add(quitBtn);
        mainPnl.add(cmdPnl, BorderLayout.SOUTH);
    }

    public void createBoard(JPanel mainPnl){
        boardPnl = new JPanel((new GridLayout(3, 3)));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[row][col].addActionListener(this);
                boardPnl.add(buttons[row][col]);
            }
        }
        mainPnl.add(boardPnl, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        TicTacToeButton button = (TicTacToeButton) e.getSource();
        if (!button.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
            return;
        }

        button.setText(xTurn ? "X" : "O");
        moveCount++;

        if(checkWin()) {
            int response = JOptionPane.showConfirmDialog(this, (xTurn ? "X" : "O") + "wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION){
                resetGame();
            } else {
                System.exit(0);
            }
        }

        if (moveCount == 9){
            int response = JOptionPane.showConfirmDialog(this, "It's a Tie! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION){
                resetGame();
            } else {
                System.exit(0);
            }
        }

        xTurn = !xTurn;
        playerLbl.setText("Turn: " + (xTurn ? "X" : "0"));
    }

    private boolean checkWin(){
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText()) && !buttons[i][0].getText().equals("")){
                return true;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText()) && !buttons[0][i].getText().equals("")){
                return true;
            }
        }

        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()) && !buttons[0][0].getText().equals("")) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText()) && !buttons[0][2].getText().equals("")) {
            return true;
        }
        return false;
    }

    private void resetGame(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        xTurn = true;
        moveCount = 0;
    }
}