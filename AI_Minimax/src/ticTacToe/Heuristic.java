package ticTacToe;

/**
 * Created by saura on 4/29/2017.
 */
public class Heuristic {
    public int heuristicValue(Node node){
        //Game g=new Game();
        //g.outputBoard(node.board);
        //System.out.println(heuristicCalculator(node,Player.X)-heuristicCalculator(node,Player.O));
        return heuristicCalculator(node,Player.X)-heuristicCalculator(node,Player.O);
    }
    private int heuristicCalculator(Node node,Player p){
        int h=0;
        for(int i=node.board.length-1;i>=1;i--){
            int val=calculateHeuristicWithNandPlayer(node,i,p);
            if(i==3){
                h+=6*val;
            } else if(i==2){
                h+=3*val;
            } else{
                h+=val;
            }
        }
        return h;
    }
    private int calculateHeuristicWithNandPlayer(Node currentNode,int n,Player p){
        return  calculateRowHeuristic(currentNode,n,p)+
                calculateColumnHeuristic(currentNode,n,p)
                +calculate45degreeDiagonalHeuristic(currentNode,n,p)+
                calculate135degreeDiagonalHeuristic(currentNode,n,p);
    }

    private int calculateRowHeuristic(Node currentNode, int n, Player p) {
        int count = 0;
        for (int row = 0; row < currentNode.board.length; row++) {
            int timesOfNodeRepeated = 0;
            for (int column = 0; column < currentNode.board.length; column++) {
                Player nextPlayer = currentNode.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n) {
                count++;
            }
        }
        return count;
    }

    private int calculateColumnHeuristic(Node currentNode, int n, Player p) {
        int count = 0;
        for (int column = 0; column < currentNode.board.length; column++) {
            int timesOfNodeRepeated = 0;
            for (int row = 0; row < currentNode.board.length; row++) {
                Player nextPlayer = currentNode.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n)
                count++;
        }
        return count;
    }

    private int calculate45degreeDiagonalHeuristic(Node currentNode, int n, Player p) {
        int count = 0;
        for (int i = 0; i < currentNode.board.length; i++) {
            for (int j = 0; j < currentNode.board.length; j++) {
                if (i == j && currentNode.board[i][j] == p) {
                    count++;
                }
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }

    private int calculate135degreeDiagonalHeuristic(Node currentNode, int n, Player p) {
        int size= currentNode.board.length;
        int count = 0;
        for (int i = 0; i < currentNode.board.length; i++) {
            if (currentNode.board[i][size-i-1] == p) {
                count++;
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }
}