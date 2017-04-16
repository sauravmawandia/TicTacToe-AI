package ticTacToe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saura on 4/15/2017.
 */
public class AlphaBeta {
    private static int MIN_UTILITY=-1000;
    private static int MAX_UTILITY=1000;
    private static int INFINITY=Integer.MAX_VALUE;
    private static int MINUS_INFINITY=Integer.MIN_VALUE;
    private List<ActionWithValue> vValue;

    public Action alphaBetaSearch(State state){
        vValue=new ArrayList<>();
        int v= maxValue(state,MIN_UTILITY,MAX_UTILITY);
        for(ActionWithValue av:vValue){
            if(av.getV()==v){
                return av.getA();
            }
        }
        return new Action(1,1);
    }
    private int maxValue(State state,int alpha,int beta){
        if(TerminalTest.terminalOrNot(state)){
            return TerminalTest.getUtility(state);
        }
        int v=MINUS_INFINITY;
        for(Action a: state.getAvailableActions()){
            v=Math.max(v,minValue(result(state,a),alpha,beta));
            vValue.add(new ActionWithValue(a,v));
            if(v>=beta){
                return v;
            }
            alpha=Math.max(alpha,v);
        }
        return v;
    }
    private int minValue(State state,int alpha,int beta){
        if(TerminalTest.terminalOrNot(state)){
            return TerminalTest.getUtility(state);
        }
        int v=INFINITY;
        for(Action a: state.getAvailableActions()){
            v=Math.min(v,maxValue(result(state,a),alpha,beta));
            vValue.add(new ActionWithValue(a,v));
            if(v<=alpha){
                return v;
            }
            beta=Math.min(beta,v);
        }
        return v;
    }

    public State result(State s, Action p){
        s.makeMove(p.getX(),p.getY(),s.getPlayer());
        return s;
    }

}
