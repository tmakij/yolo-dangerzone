package fi.helsinki.cs.shakki;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ourevaluator.OurEvaluator;
import position.Evaluator;
import position.Position;

public class Main {

    static Evaluator ce; // current evaluator
    static Evaluator oe; // our evaluator
    static Evaluator ye; // your evaluator

    static class PositionComparator implements Comparator<Position> {

        Map<Position, Double> evaluations = new HashMap<Position, Double>();

        PositionComparator(List<Position> positions) {
            for (Position p : positions) {
                evaluations.put(p, ce.eval(p));
            }
        }

        @Override
        public int compare(Position p1, Position p2) {
            return Double.compare(evaluations.get(p1), evaluations.get(p2));
        }
    }

    static double alphabeta(Position p, int depth, double alpha, double beta, int player) {
        // 0 tries to maximize, 1 tries to minimize
        if (p.winner == -1) {
            return -1E10 - depth; // prefer to win sooner
        }
        if (p.winner == +1) {
            return +1E10 + depth; // and lose later
        }
        if (depth == 0) {
            return ce.eval(p);
        }
        List<Position> positions = p.getNextPositions();
        Collections.sort(positions, new PositionComparator(positions));
        if (player == 0) {
            Collections.reverse(positions);
        }

        if (player == 0) {
            for (int i = 0; i < positions.size(); ++i) {
                alpha = Math.max(alpha, alphabeta(positions.get(i), depth - 1, alpha, beta, 1));
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        }

        for (int i = 0; i < positions.size(); ++i) {
            beta = Math.min(beta, alphabeta(positions.get(i), depth - 1, alpha, beta, 0));
            if (beta <= alpha) {
                break;
            }
        }

        return beta;
    }

    static double minmax(Position p, int depth, int player) {
        double alpha = -Double.MAX_VALUE, beta = Double.MAX_VALUE;
        return alphabeta(p, depth, alpha, beta, player);
    }

    public static void main(String[] args) {
        // you get the white pieces, we play the black pieces
        oe = new OurEvaluator();
        ye = new YourEvaluator();
        int depth = 5;
        Position p = new Position();
        p.setStartingPosition();
        p.print();

        System.out.println("Eval: " + oe.eval(p));

        long ms = System.currentTimeMillis();

        while (true) {
            List<Position> positions = p.getNextPositions();

            if (p.winner == +1) {
                System.out.println("White won.");
                break;
            }

            if (p.winner == -1) {
                System.out.println("Black won.");
                break;
            }

            if (positions.size() == 0) {
                System.out.println("No more available moves");
                break;
            }

            Position bestPosition = new Position();
            if (p.whiteToMove) {
                ce = ye;
                double max = -1. / 0.;
                for (int i = 0; i < positions.size(); ++i) {
                    double val = minmax(positions.get(i), depth, 1);
                    if (max < val) {
                        bestPosition = positions.get(i);
                        max = val;
                    }
                }
            } else {
                ce = oe;
                double min = 1. / 0.;
                for (int i = 0; i < positions.size(); ++i) {
                    double val = minmax(positions.get(i), depth, 0);
                    if (min > val) {
                        bestPosition = positions.get(i);
                        min = val;
                    }
                }
            }

            assert p.whiteToMove != bestPosition.whiteToMove;
            p = bestPosition;
            p.print();

            long curtime = System.currentTimeMillis();
            System.out.println("Move took " + ((curtime - ms) / 1000.0) + " seconds");
            ms = curtime;
        }
    }
}
