package Heuristic;

import search.space.State;
import Tools.Utils;

public class HeuristicCal1 implements HeuristicCalculator {
    @Override
    public int calculate(State state) {
        return Utils.distance(state.getCurrentPosition(), state.getStart());
    }
}
