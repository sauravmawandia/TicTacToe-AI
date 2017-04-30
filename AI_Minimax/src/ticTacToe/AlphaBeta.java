package ticTacToe;

import java.util.Vector;

/**
 * Created by saura on 4/29/2017.
 */
public class AlphaBeta {
    private final TicTacToe ticTacToe;
    private final Heuristic heuristic;
    Vector<Node> possibleNextMoveNodes = new Vector<Node>();
    private long cutOffTime;
    private boolean cutOffOccurred;


    public AlphaBeta() {
        ticTacToe = new TicTacToe();
        heuristic = new Heuristic();
    }

    public Node nextNodeToMove(Node currentNode) {
        cutOffOccurred=false;
        cutOffTime = System.currentTimeMillis() + 8000;
        this.minimaxAlphaBetaPruning(currentNode, TicTacToe.O_WINS,TicTacToe.X_WINS);
        Node newNode = ticTacToe.getMaxNodeInList(possibleNextMoveNodes);
        possibleNextMoveNodes.removeAllElements();
        if(cutOffOccurred){
            System.out.println("CutOff occurred");
        }
        return newNode;
    }

    public int minimaxAlphaBetaPruning(Node currentNode, int alpha, int beta) {
        int alphaOfCurrentNode = alpha;
        int betaOfCurrentNode = beta;
        if (ticTacToe.isLeafNode(currentNode))
            return this.miniMaxLeafNode(currentNode);
        else if (currentNode.nextPlayer == Player.O)
            return this.maxValue(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
        else return this.minValue(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
    }

    public int maxValue(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        Vector<Node> allSuccessors = ticTacToe.getAllSuccessors(currentNode);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            Node aSuccessor = allSuccessors.get(atIndex);
            int currentMin = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            betaOfCurrentNode = Integer.min(betaOfCurrentNode, currentMin);

            currentNode.heuristicValue = Integer.min(currentNode.heuristicValue, betaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) break;
        }
        if (this.possibleNextMoveNodes(currentNode) != null) possibleNextMoveNodes.add(currentNode);
        return betaOfCurrentNode;
    }

    public int minValue(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        Vector<Node> allSuccessors = ticTacToe.getAllSuccessors(currentNode);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            Node aSuccessor = allSuccessors.get(atIndex);
            int currentMax = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            alphaOfCurrentNode = Integer.max(alphaOfCurrentNode, currentMax);
            currentNode.heuristicValue = Integer.max(currentNode.heuristicValue, alphaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) break;
        }
        if (this.possibleNextMoveNodes(currentNode) != null) possibleNextMoveNodes.add(currentNode);
        return alphaOfCurrentNode;
    }

    // Get next move
    public Node possibleNextMoveNodes(Node currentNode) {
        if (currentNode.atDepth == 1) return currentNode;
        else
            return null;
    }

    public int miniMaxLeafNode(Node currentNode) {
        if (this.possibleNextMoveNodes(currentNode) != null)
            possibleNextMoveNodes.add(currentNode);
        return ticTacToe.getUtilityOfState(currentNode);
    }

}
