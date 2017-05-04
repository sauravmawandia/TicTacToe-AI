package ticTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by saura on 4/30/2017.
 */
public class GUI extends JPanel {
    GameBoard gameBoard = new GameBoard();
    AlphaBeta alphaBeta = new AlphaBeta();
    Game game = new Game();
    HashMap<Integer, Integer> x = new HashMap();
    HashMap<Integer, Integer> y = new HashMap();
    BoardNode root = gameBoard.initializeNode();



    JButton buttons[] = new JButton[16];
    int alternate = 0;//if this number is a even, then put a X. If it's odd, then put an O

    public GUI() {
        setLayout(new GridLayout(4, 4));
        initializebuttons();
        x.put(1, 0);
        x.put(70, 1);
        x.put(139, 2);
        x.put(208, 3);
        y.put(0, 0);
        y.put(61, 1);
        y.put(122, 2);
        y.put(183, 3);
        root.nextPlayer = Player.O;


    }

    public void initializebuttons() {
        for (int i = 0; i <= 15; i++) {
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].addActionListener(new ButtonListner());

            add(buttons[i]);
        }
    }

    public void resetButtons() {
        root = gameBoard.initializeNode();
        for (int i = 0; i <= 15; i++) {
            buttons[i].setText("");
        }
    }

    private class ButtonListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked
            int[] humanInput = {y.get(buttonClicked.getY()), x.get(buttonClicked.getX())};
            buttonClicked.setText("O");
        }

    }


    public static void main(String[] args) {
        JFrame window = new JFrame("Tic-Tac-Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new GUI());
        window.setBounds(300, 200, 300, 300);
        window.setVisible(true);
        GUI g = new GUI();

    }

}



