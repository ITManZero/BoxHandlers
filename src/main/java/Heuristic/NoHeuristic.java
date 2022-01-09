package Heuristic;

import search.space.State;

public class NoHeuristic implements HeuristicCalculator{
    @Override
    public int calculate(State state) {
        return 0;
    }
}
