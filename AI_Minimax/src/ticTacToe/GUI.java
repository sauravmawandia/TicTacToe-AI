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
    TicTacToe ticTacToe = new TicTacToe();
    AlphaBeta alphaBeta = new AlphaBeta();
    Game game = new Game();
    HashMap<Integer, Integer> x = new HashMap();
    HashMap<Integer, Integer> y = new HashMap();


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
        for (int i = 0; i <= 15; i++) {
            buttons[i].setText("");
        }
    }

    private class ButtonListner implements ActionListener {
        Node root = ticTacToe.initializeNode();
        Node newNode=root;
        ButtonListner(){
            root.nextPlayer = Player.O;
        }
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked
            int[] humanInput = { y.get(buttonClicked.getY()),x.get(buttonClicked.getX())};
            newNode=humanMove(newNode,humanInput);
            buttonClicked.setText("O");
            newNode=machineMove(newNode);

        }
        public Node machineMove(Node node){
            Node newNode = ticTacToe.initializeNodeWithInput(node.board);
            newNode = alphaBeta.nextNodeToMove(newNode);
            if(ticTacToe.terminalTest(newNode) == true) System.out.println("Computer won!");
            else if(ticTacToe.isLeafNode(newNode) == true)  System.out.println("The game is draw");
            return newNode;
        }
        public Node humanMove(Node n,int[] humanInput){
            JButton action=new JButton("C");

            System.out.println(humanInput[0]+" "+humanInput[1]);
            Node newNode = new Node();
            newNode = ticTacToe.getSuccessor(root, humanInput);
            game.outputBoard(newNode.board);
            if (ticTacToe.terminalTest(newNode) == true) {
                JOptionPane.showConfirmDialog(null, "Congratulation you won.");
                resetButtons();
            } else if (ticTacToe.isLeafNode(newNode) == true)
                System.out.println("The game is draw");
            return newNode;

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



