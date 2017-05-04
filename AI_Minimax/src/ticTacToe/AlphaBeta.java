package ticTacToe;

import java.util.Vector;

/**
 * Created by saura on 4/19/2017.
 */
public class AlphaBeta {
    private final GameBoard gameBoard;
    private final Heuristic heuristic;
    Vector<BoardNode> possibleNextMoveBoardNodes;
    private volatile long cutOffTime;
    private volatile boolean cutOffOccurred;
    private  int maxDepth;
    private  int depth;
    private int pruningMax;
    private int pruningMin;
    private int nodesGenerated;

    public AlphaBeta() {
        gameBoard = new GameBoard();
        heuristic = new Heuristic();
        possibleNextMoveBoardNodes=new Vector<BoardNode>();
        initialize();
    }

    private void initialize() {
        cutOffOccurred = false;
        maxDepth = 0;
        depth = 0;
        pruningMax = 0;
        pruningMin = 0;
        nodesGenerated = 0;
        cutOffTime = System.currentTimeMillis() + 8000;
    }

    private void printStat() {
        System.out.println("Max Depth reached= " + maxDepth);
        if (cutOffOccurred) {
            System.out.println("CutOff occurred");
        }
        System.out.println("Nodes Generated= " + nodesGenerated);
        System.out.println("Number of Pruning in Max= " + pruningMax);
        System.out.println("Number of Pruning in Min= " + pruningMin);
    }

    public BoardNode nextMove(BoardNode currentBoardNode) {
        initialize();
        this.minimaxAlphaBeta(currentBoardNode, GameBoard.O_WINS, GameBoard.X_WINS);
        BoardNode newBoardNode = getMaxNodeInList(possibleNextMoveBoardNodes);
        possibleNextMoveBoardNodes.removeAllElements();
        printStat();
        return newBoardNode;
    }

    public int minimaxAlphaBeta(BoardNode currentBoardNode, int alpha, int beta) {
        depth++;
        maxDepth = depth > maxDepth ? depth : maxDepth;
        nodesGenerated++;

        int alphaOfCurrentNode = alpha;
        int betaOfCurrentNode = beta;
        if (gameBoard.isLeafNode(currentBoardNode)) {
            depth = 0;
            return this.ifLeafNode(currentBoardNode);
        } else if (System.currentTimeMillis() > cutOffTime) {
            int hval = heuristic.heuristicValue(currentBoardNode);
            currentBoardNode.heuristicValue = hval;
            cutOffOccurred = true;
            depth = 0;
            return hval;
        } else if (currentBoardNode.nextPlayer == Player.X) {
            return this.maxValue(currentBoardNode, alphaOfCurrentNode, betaOfCurrentNode);
        } else {
            return this.minValue(currentBoardNode, alphaOfCurrentNode, betaOfCurrentNode);
        }
    }

    public int maxValue(BoardNode currentBoardNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        if (System.currentTimeMillis() > cutOffTime) {
            currentBoardNode.heuristicValue = Integer.max(heuristic.heuristicValue(currentBoardNode), alphaOfCurrentNode);
            alphaOfCurrentNode = currentBoardNode.heuristicValue;
            return alphaOfCurrentNode;

        }
        Vector<BoardNode> allSuccessors = gameBoard.getAllSuccessors(currentBoardNode);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            BoardNode aSuccessor = allSuccessors.get(atIndex);
            int currentMax = this.minimaxAlphaBeta(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            alphaOfCurrentNode = Integer.max(alphaOfCurrentNode, currentMax);
            currentBoardNode.heuristicValue = Integer.max(currentBoardNode.heuristicValue, alphaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) {
                pruningMax++;
                break;
            }
        }
        if (this.getPossibleMoves(currentBoardNode) != null) possibleNextMoveBoardNodes.add(currentBoardNode);
        return alphaOfCurrentNode;
    }

    public int minValue(BoardNode currentBoardNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        if (System.currentTimeMillis() > cutOffTime) {
            currentBoardNode.heuristicValue = Integer.min(heuristic.heuristicValue(currentBoardNode), betaOfCurrentNode);
            betaOfCurrentNode = currentBoardNode.heuristicValue;
            return betaOfCurrentNode;
        }
        Vector<BoardNode> allSuccessors = gameBoard.getAllSuccessors(currentBoardNode);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            BoardNode aSuccessor = allSuccessors.get(atIndex);
            int v = this.minimaxAlphaBeta(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            betaOfCurrentNode = Integer.min(betaOfCurrentNode, v);
            currentBoardNode.heuristicValue = Integer.min(currentBoardNode.heuristicValue, betaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) {
                pruningMin++;
                break;
            }
        }
        if (this.getPossibleMoves(currentBoardNode) != null) possibleNextMoveBoardNodes.add(currentBoardNode);
        return betaOfCurrentNode;
    }

    public BoardNode getPossibleMoves(BoardNode currentBoardNode) {
        if (currentBoardNode.atDepth == 1) return currentBoardNode;
        else
            return null;
    }

    public int ifLeafNode(BoardNode currentBoardNode) {
        if (this.getPossibleMoves(currentBoardNode) != null)
            possibleNextMoveBoardNodes.add(currentBoardNode);
        return gameBoard.getUtilityOfState(currentBoardNode);
    }

    public BoardNode getMaxNodeInList(Vector<BoardNode> aVectorBoardNode) {
        BoardNode maxBoardNode = aVectorBoardNode.get(0);
        int listSize = aVectorBoardNode.size();
        for (int index = 0; index < listSize; index++)
            if (maxBoardNode.heuristicValue < aVectorBoardNode.get(index).heuristicValue)
                maxBoardNode = aVectorBoardNode.get(index);
        return maxBoardNode;
    }
}
