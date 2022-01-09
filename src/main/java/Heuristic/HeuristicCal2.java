package Heuristic;

import search.space.State;
import Tools.Utils;

public class HeuristicCal2 implements HeuristicCalculator {
    @Override
    public int calculate(State state) {

        int loadCost = Utils.distance(state.getCurrentPosition(), state.getBoxes().get(0).getFrom());
        int deliverCostFromCurrent = Utils.distance(state.getCurrentPosition(), state.getBoxes().get(0).getTo());
        int deliverCostFromLoad = Utils.distance(state.getBoxes().get(0).getFrom(), state.getBoxes().get(0).getTo());
        int backToStartCostFromCurrent = Utils.distance(state.getCurrentPosition(), state.getStart());
        int backToStartCostFromDeliver = Utils.distance(state.getBoxes().get(0).getTo(), state.getStart());
        if (state.getLoadedBoxes().contains(0))
            return deliverCostFromCurrent + backToStartCostFromDeliver + state.getPayload();
        if (state.getDeliveredBoxes().contains(0)) return backToStartCostFromCurrent + state.getPayload();
        return loadCost + deliverCostFromLoad + backToStartCostFromDeliver + state.getPayload();
    }
}
