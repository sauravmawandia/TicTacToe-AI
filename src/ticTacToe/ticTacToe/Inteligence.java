/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ticTacToe;

public class Inteligence {

    public int nodeCount;
    private final int size = 3;
    private final static int INFINITY = Integer.MAX_VALUE;
    private final static int MINUS_INFINITY = Integer.MIN_VALUE;
    private static int MIN_UTILITY = -1000;
    private static int MAX_UTILITY = 1000;
    public Node rootNode;

    public Inteligence() {
    }

    public Inteligence(Board b) {
        Board newB = new Board(b);
        rootNode = new Node(newB);

    }


    public Action move() {
        int max = MINUS_INFINITY;
        Node bestNode = new Node();
        for (int i = 0; i < size; i++) {
            for (int j = 0; i < size; i++) {
                Node n = new Node();
                n.copy(rootNode);
                Action a = new Action(i, j);
                if (n.board.setMove(a) == true) {
                    rootNode.AddChildren(n);
                    n.action = a;
                    int val = minimaxAB(n, true, 100, MIN_UTILITY, MAX_UTILITY);
                    if (val >= max) {
                        max = val;
                        bestNode = n;

                    }
                }
            }
        }
        return bestNode.action;
    }

    private int minimaxAB(Node node, boolean min, int depth, int alpha, int beta) {
        int utility = node.board.getUtility();
        if (utility != -1) {
            node.point = utility;
            return utility;
        } else {
            if (min == true) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; i < size; i++) {
                        Node n = new Node();
                        n.copy(node);
                        Action a = new Action(i, j);
                        if (n.board.setMove(a) == true) {
                            node.AddChildren(n);
                            n.action = a;
                            //System.out.println("In min:"+ min);
                            int val = minimaxAB(n, false, depth - 1, alpha, beta);
                            if (val < beta) {
                                beta = val;
                                n.parent.point = val;
                            }
                        }

                    }
                }
                //System.out.println("Out min:"+ min);
                return beta;
            }

            if (min == false) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; i < size; i++) {
                        Node n = new Node();
                        n.copy(node);
                        Action a = new Action(i, j);
                        if (n.board.setMove(a) == true) {
                            node.AddChildren(n);
                            n.action = a;
                            //System.out.println("In min:"+ min);
                            int val = minimaxAB(n, true, depth - 1, alpha, beta);

                            if (val > alpha) {
                                alpha = val;
                                n.parent.point = val;
                            }


                        }
                    }

                }
                //System.out.println("Out min:"+ min);
                return alpha;

            }

        }
        return MINUS_INFINITY;
    }
}
