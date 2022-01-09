
package search.algorithms;

import search.space.State;

import java.util.*;

public interface SearchAlgorithms {

    State search(State initialState);

    default void showPath(State solution) {

        List<String>[][] s = new List[solution.getBoard().length][solution.getBoard()[0].length];
        List<Integer> rows = new ArrayList<>();
        List<Integer> clos = new ArrayList<>();


        for (int i = 0; i < s.length; i++)
            rows.add(1);

        for (int i = 0; i < s[0].length; i++)
            clos.add(1);

        for (int i = 0; i < s.length; i++)
            for (int j = 0; j < s[i].length; j++)
                s[i][j] = new ArrayList<>();

        int max = Integer.MIN_VALUE;

        while (solution != null) {
            s[solution.getCurrentI()][solution.getCurrentJ()].add(solution.getSteps() + "");
            if (s[solution.getCurrentI()][solution.getCurrentJ()].size() > rows.get(solution.getCurrentI()))
                rows.set(solution.getCurrentI(), s[solution.getCurrentI()][solution.getCurrentJ()].size());
            for (String subCol : s[solution.getCurrentI()][solution.getCurrentJ()])
                if (max < subCol.length()) max = subCol.length();
            if (max > clos.get(solution.getCurrentJ()))
                clos.set(solution.getCurrentJ(), max);
            solution = solution.getPreState();
        }

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++)
                if (s[i][j].size() != rows.get(i))
                    for (int k = 0; k <= rows.get(i) - s[i][j].size(); k++)
                        s[i][j].add(".");
        }

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++)
                for (int k = 0; k < s[i][j].size(); k++) {
                    if (s[i][j].get(k).length() != clos.get(j))
                        for (int p = 0; p < clos.get(j) - s[i][j].get(k).length(); p++)
                            s[i][j].set(k, s[i][j].get(k) + " ");
                }
        }

        int co = 0;

        for (int colsize : clos) {
            co += colsize;
        }

        for (int i = 0; i < s.length; i++)
            for (int j = 0; j < s[i].length; j++)
                Collections.reverse(s[i][j]);

        for (int i = 0; i < s.length; i++) {
            for (int k = 0; k < rows.get(i); k++) {
                for (int j = 0; j < s[i].length; j++) {
                    System.out.print(s[i][j].get(k) + " | ");
                }
                System.out.println();
            }
            for (int c = 0; c < co * 2 + clos.size(); c++)
                System.out.print("-");
            System.out.println();
        }

    }
}
