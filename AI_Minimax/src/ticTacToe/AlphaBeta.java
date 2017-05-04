package ticTacToe;

import java.util.Vector;

/**
 * Created by saura on 4/19/2017.
 */
public class AlphaBeta {
    private static final int CUT_OFF_TIME=12000;
    private final GameBoard gameBoard;
    private final Heuristic heuristic;
    Vector<State> possibleNextMoveStates;
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
        possibleNextMoveStates =new Vector<State>();
        initialize();
    }
    // initialize statistics variable
    private void initialize() {
        cutOffOccurred = false;
        maxDepth = 0;
        depth = 0;
        pruningMax = 0;
        pruningMin = 0;
        nodesGenerated = 0;
        cutOffTime = System.currentTimeMillis() + CUT_OFF_TIME;
    }

    //print statistics
    private void printStat() {
        System.out.println("Max Depth reached= " + maxDepth);
        if (cutOffOccurred) {
            System.out.println("CutOff occurred");
        }
        System.out.println("Nodes Generated= " + nodesGenerated);
        System.out.println("Number of Pruning in Max= " + pruningMax);
        System.out.println("Number of Pruning in Min= " + pruningMin);
    }

    //decide the next move computer will take
    public State nextMove(State currentState) {
        initialize();
        this.minimaxAlphaBeta(currentState, GameBoard.O_WINS, GameBoard.X_WINS);
        State newState = getMaxNodeInList(possibleNextMoveStates);
        possibleNextMoveStates.removeAllElements();
        printStat();
        return newState;
    }

    //use minimax algorithm recursively
    public int minimaxAlphaBeta(State currentState, int alpha, int beta) {
        depth++;
        maxDepth = depth > maxDepth ? depth : maxDepth;
        nodesGenerated++;

        int alphaOfCurrentNode = alpha;
        int betaOfCurrentNode = beta;
        if (gameBoard.isTerminalState(currentState)) {
            depth = 0;
            return this.ifLeafNode(currentState);
        } else if (System.currentTimeMillis() > cutOffTime) {
            int hval = heuristic.heuristicValue(currentState);
            currentState.vValue = hval;
            cutOffOccurred = true;
            depth = 0;
            return hval;
        } else if (currentState.nextPlayer == Player.X) {
            return this.maxValue(currentState, alphaOfCurrentNode, betaOfCurrentNode);
        } else {
            return this.minValue(currentState, alphaOfCurrentNode, betaOfCurrentNode);
        }
    }

    //returns the max value of move
    public int maxValue(State currentState, int alphaOfCurrentNode, int betaOfCurrentNode) {
        if (System.currentTimeMillis() > cutOffTime) {
            currentState.vValue = Integer.max(heuristic.heuristicValue(currentState), alphaOfCurrentNode);
            alphaOfCurrentNode = currentState.vValue;
            return alphaOfCurrentNode;

        }
        Vector<State> allSuccessors = gameBoard.getAllSuccessors(currentState);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            State aSuccessor = allSuccessors.get(atIndex);
            int currentMax = this.minimaxAlphaBeta(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            alphaOfCurrentNode = Integer.max(alphaOfCurrentNode, currentMax);
            currentState.vValue = Integer.max(currentState.vValue, alphaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) {
                pruningMax++;
                break;
            }
        }
        if (this.getPossibleMoves(currentState) != null) possibleNextMoveStates.add(currentState);
        return alphaOfCurrentNode;
    }

    //returns the minValue of move
    public int minValue(State currentState, int alphaOfCurrentNode, int betaOfCurrentNode) {
        if (System.currentTimeMillis() > cutOffTime) {
            currentState.vValue = Integer.min(heuristic.heuristicValue(currentState), betaOfCurrentNode);
            betaOfCurrentNode = currentState.vValue;
            return betaOfCurrentNode;
        }
        Vector<State> allSuccessors = gameBoard.getAllSuccessors(currentState);
        for (int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            State aSuccessor = allSuccessors.get(atIndex);
            int v = this.minimaxAlphaBeta(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            betaOfCurrentNode = Integer.min(betaOfCurrentNode, v);
            currentState.vValue = Integer.min(currentState.vValue, betaOfCurrentNode);
            if (alphaOfCurrentNode >= betaOfCurrentNode) {
                pruningMin++;
                break;
            }
        }
        if (this.getPossibleMoves(currentState) != null) possibleNextMoveStates.add(currentState);
        return betaOfCurrentNode;
    }

    //check if more moves are possible
    public State getPossibleMoves(State currentState) {
        if (currentState.depth == 1) return currentState;
        else
            return null;
    }

    //check if current Board node is leaf node
    public int ifLeafNode(State currentState) {
        if (this.getPossibleMoves(currentState) != null)
            possibleNextMoveStates.add(currentState);
        return gameBoard.getUtilityOfState(currentState);
    }

    //get node with lesser heuristic val
    public State getMaxNodeInList(Vector<State> node) {
        State maxState = node.get(0);
        int listSize = node.size();
        for (int i = 0; i < listSize; i++)
            if (maxState.vValue < node.get(i).vValue)
                maxState = node.get(i);
        return maxState;
    }
}
