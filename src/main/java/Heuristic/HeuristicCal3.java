package Heuristic;

import components.Box;
import search.space.State;
import Tools.Utils;

public class HeuristicCal3 implements HeuristicCalculator {

    @Override
    public int calculate(State state) {
        int max = Integer.MIN_VALUE;
        for (Box box : state.getBoxes()) {
            int loadCost = Utils.distance(state.getCurrentPosition(), box.getFrom());
            int deliverCostFromCurrent = Utils.distance(state.getCurrentPosition(), box.getTo());
            int deliverCostFromLoad = Utils.distance(box.getFrom(), box.getTo());
            int backToStartCostFromCurrent = Utils.distance(state.getCurrentPosition(), state.getStart());
            int backToStartCostFromDeliver = Utils.distance(box.getTo(), state.getStart());
            int cost;
            if (state.getLoadedBoxes().contains(0))
                cost = deliverCostFromCurrent + backToStartCostFromDeliver+ state.getPayload();
            else if (state.getDeliveredBoxes().contains(0)) cost = backToStartCostFromCurrent+ state.getPayload();
            else cost = loadCost + deliverCostFromLoad + backToStartCostFromDeliver+ state.getPayload();
            if (max < cost) max = cost;
        }
        return max;
    }
}
