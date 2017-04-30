package ticTacToe;

import java.util.Vector;

/**
 * Created by saura on 4/29/2017.
 */
public class AlphaBeta {
    private final TicTacToe ticTacToe;
    Vector<Node> possibleNextMoveNodes = new Vector<Node>();
    public int getMinimax(Node currentNode) {
        if(ticTacToe.isLeafNode(currentNode)==true) return this.miniMaxLeafNode(currentNode);
        else return this.miniMaxNonLeafNode(currentNode);
    }
    public int miniMaxNonLeafNode(Node currentNode) {
        Vector<Node> allSuccessors = ticTacToe.getAllSuccessors(currentNode);
        for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            Node aSuccessor = allSuccessors.get(atIndex);
            if(currentNode.nextPlayer == Player.O) currentNode.heuristicValue = ticTacToe.getMinTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));
            else currentNode.heuristicValue = ticTacToe.getMaxTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));
        }
        if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
        return currentNode.heuristicValue;
    }
    public int miniMaxLeafNode(Node currentNode){
        if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
        return ticTacToe.evaluateHeuristicValue(currentNode);
    }
    public AlphaBeta(){
        ticTacToe=new TicTacToe();
    }
    public int initializeAlpha(Node currentNode) {
        if(ticTacToe.isLeafNode(currentNode)==true) return ticTacToe.evaluateHeuristicValue(currentNode);
        else return -TicTacToe.INFINITY;
    }
    public int initializeBeta(Node currentNode) {
        if(ticTacToe.isLeafNode(currentNode)==true) return ticTacToe.evaluateHeuristicValue(currentNode);
        else return TicTacToe.INFINITY;
    }
    public int minimaxAlphaBetaPruning(Node currentNode, int alpha, int beta) {
        int alphaOfCurrentNode = alpha; int betaOfCurrentNode = beta;
        if (ticTacToe.isLeafNode(currentNode)==true)  return this.miniMaxLeafNode(currentNode);
        else if (currentNode.nextPlayer == Player.O) return this.minimaxAlpha_CurrentMaxNode(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
        else return this.minimaxBeta_CurrentMinNode(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
    }
    public int minimaxAlpha_CurrentMaxNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        Vector<Node> allSuccessors = ticTacToe.getAllSuccessors(currentNode);
        for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            Node aSuccessor = allSuccessors.get(atIndex);
            int currentMin = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            betaOfCurrentNode = ticTacToe.getMinTwoIntegers(betaOfCurrentNode, currentMin);
            currentNode.heuristicValue = ticTacToe.getMinTwoIntegers(currentNode.heuristicValue, betaOfCurrentNode);
            if(alphaOfCurrentNode >= betaOfCurrentNode) break;
        }
        if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
        return betaOfCurrentNode;
    }
    public int minimaxBeta_CurrentMinNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
        Vector<Node> allSuccessors = ticTacToe.getAllSuccessors(currentNode);
        for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
            Node aSuccessor = allSuccessors.get(atIndex);
            int currentMax = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
            alphaOfCurrentNode = ticTacToe.getMaxTwoIntegers(alphaOfCurrentNode, currentMax);
            currentNode.heuristicValue = ticTacToe.getMaxTwoIntegers(currentNode.heuristicValue,alphaOfCurrentNode);
            if(alphaOfCurrentNode >= betaOfCurrentNode) break;
        }
        if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
        return alphaOfCurrentNode;
    }

    // Get next move
    public Node possibleNextMoveNodes (Node currentNode) {
        if(currentNode.atDepth == 1) return currentNode;
        else return null;
    }
    public Node nextNodeToMove(Node currentNode) {
        //this.getMinimax(currentNode);		// Change this to see how different between two algorithms.
        this.minimaxAlphaBetaPruning(currentNode, this.initializeAlpha(currentNode), this.initializeBeta(currentNode));
        Node newNode = ticTacToe.getMaxNodeInList(possibleNextMoveNodes);
        possibleNextMoveNodes.removeAllElements();
        return newNode;
    }
}
