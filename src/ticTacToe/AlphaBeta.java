package ticTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by saura on 4/15/2017.
 */
public class AlphaBeta {
    private static int MIN_UTILITY=-1000;
    private static int MAX_UTILITY=1000;
    private static int INFINITY=Integer.MAX_VALUE;
    private static int MINUS_INFINITY=Integer.MIN_VALUE;
    private List<ActionWithValue> vValue;

    public Action alphaBetaSearch(State x){

        State newState= x;
        vValue=new ArrayList<>();
        int v= maxValue(newState,MIN_UTILITY,MAX_UTILITY);
        for(ActionWithValue av:vValue) {
            if (av.getValue() == v) {
                return av.getAction();
            }
        }
        return vValue.get(0).getAction();
    }
    private int maxValue(State state,int alpha,int beta){
        if(TerminalTest.terminalOrNot(state)){
            return TerminalTest.getUtility(state);
        }
        int v=MINUS_INFINITY;
        for(Action action: state.getAvailableActions()){
            v=Math.max(v,minValue(result(state,action,state.getMaxPlayer()),alpha,beta));
            vValue.add(new ActionWithValue(action,v));
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
            v=Math.min(v,maxValue(result(state,a,state.getMinPlayer()),alpha,beta));
            vValue.add(new ActionWithValue(a,v));
            if(v<=alpha){
                return v;
            }
            beta=Math.min(beta,v);

        }
        return v;
    }

    private State result(State s, Action action,Player p){
        s.makeMove(action,p);
        return s;
    }

}
