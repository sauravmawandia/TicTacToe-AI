    /*
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */
    package ticTacToe;

    import java.util.ArrayList;
    import java.util.List;

    public class Node {

        public Action action;
        public int point;
        public Board board;
        public Node parent;
        public ArrayList<Node> childrenNode = new ArrayList<Node>();

        public Node() {
            this.board = new Board();
        }

        public Node(Board board) {
            this.board = board;
        }

        public void AddChildren(Node node) {
            childrenNode.add(node);
            node.Parent(this);
        }

        private void Parent(Node node) {
            this.parent = node;
        }

        public void copy(Node n) {
            this.point=n.point;

            this.board.copy(n.board);

        }

        public void print() {
            System.out.println();
            System.out.println("Point" + this.point);
            System.out.println("moveBox" + this.action.toString());
            this.board.print();
            System.out.println();
        }

    }
