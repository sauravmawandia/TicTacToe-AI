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

    public Action alphaBetaSearch(State state){
        ActionWithValue av= maxValue(state,MIN_UTILITY,MAX_UTILITY);
        return av.getAction();
    }
    private ActionWithValue maxValue(State state,int alpha,int beta){
        if(TerminalTest.terminalOrNot(state)){
            return new ActionWithValue( null,TerminalTest.getUtility(state));
        }
        int v=MINUS_INFINITY;
        Action finalAction=null;
        for( Action action: state.getAvailableActions()){
            v=Math.max(v,minValue(result(state,action,state.getMaxPlayer()),alpha,beta).getValue());
            if(v>=beta){
                return new ActionWithValue(action,v);
            }
            alpha=Math.max(alpha,v);
            finalAction=action;
        }
        return new ActionWithValue(finalAction,v);
    }
    private ActionWithValue minValue(State state,int alpha,int beta){
        if(TerminalTest.terminalOrNot(state)){
            return new ActionWithValue( null,TerminalTest.getUtility(state));
        }
        int v=INFINITY;
        Action finalAction=null;
        for(Action action: state.getAvailableActions()){
            v=Math.min(v,maxValue(result(state,action,state.getMinPlayer()),alpha,beta).getValue());
            if(v<=alpha){
                return new ActionWithValue(action,v);
            }
            beta=Math.min(beta,v);
            finalAction=action;

        }
        return new ActionWithValue(finalAction,v);
    }

    private State result(State s, Action action,Player p){
        s.makeMove(action,p);
        return s;
    }

}
