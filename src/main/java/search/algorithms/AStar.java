
package search.algorithms;

import search.space.State;
import Tools.Utils;

import java.util.*;


public class AStar implements SearchAlgorithms {

    private Queue<State> queue;

    private List<State> visited;

    private long producedNodes;
    private long processedNodes;
    private long visitedNodes;

    public AStar() {
        this.queue = new PriorityQueue<>((o1, o2) -> (o1.getCost() + o1.getHeuristic()) - (o2.getCost() + o2.getHeuristic()));
        this.visited = new ArrayList<>();
        this.producedNodes = 0;
        this.processedNodes = 0;
        this.visitedNodes = 0;
    }

    @Override
    public State search(State InitialState) {

        long startTime = System.currentTimeMillis();

        queue.add(InitialState);
        visitedNodes++;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            processedNodes++;

//             check if it's a solution return state
            if (Utils.isGoal(state)) {
//                System.out.println("visited nodes :" + visitedNodes);
                System.out.println("produced nodes :" + producedNodes);
                System.out.println("processed nodes :" + processedNodes);
                System.out.println("Total Cost: " + state.getSteps() + " steps");
                System.out.println("execution time : " + (System.currentTimeMillis() - startTime) + " ms");
                reset();
                return state;
            }

            for (State.Direction direction : state.getDirections()) {
                State newState = state.move(direction);
                if (!visited.contains(newState)) {
                    queue.add(newState);
                    producedNodes++;
                }
            }
            visited.add(state);
            visitedNodes++;
        }

        reset();
        return null;
    }

    private void reset() {
        this.queue = new PriorityQueue<>((o1, o2) -> (o1.getCost() + o1.getHeuristic()) - (o2.getCost() + o2.getHeuristic()));
        this.visited = new ArrayList<>();
        this.producedNodes = 0;
        this.processedNodes = 0;
        this.visitedNodes = 0;
    }
}

