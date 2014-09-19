package fi.helsinki.cs.shakki;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import position.Evaluator;
import position.Position;

/**
 * @author Timo Mäki
 */
public final class YourEvaluator extends Evaluator {

    private final Random rand = new Random();

    /*
     * Minä (Valkoiset) vastaan he (mustat).
     */
    @Override
    public double eval(final Position p) {
        double ret = 0;
        final Map<Coordinate, Integer> myTroops = new HashMap<Coordinate, Integer>();
        final Map<Coordinate, Integer> enemyTroops = new HashMap<Coordinate, Integer>();
        for (int x = 0; x < p.board.length; ++x) {
            final int[] boardX = p.board[x];
            for (int y = 0; y < boardX.length; ++y) {
                ret += isGood(boardX[y], x, y, myTroops, enemyTroops);
            }
        }
        ret += UnitsCanBeEaten(myTroops, enemyTroops);
        return ret;
    }

    private static double isGood(final int position, final int x, final int y, final Map<Coordinate, Integer> myTroops, final Map<Coordinate, Integer> enemyTroops) {
        boolean myUnit = false;
        if (position >= Position.WKing && position <= Position.WPawn) {
            myUnit = true;
            myTroops.put(new Coordinate(x, y), position);
        } else if (position >= Position.BKing && position <= Position.BPawn) {
            enemyTroops.put(new Coordinate(x, y), position);
        }
        final double res = getWeightOfUnit(position);
        return myUnit ? res : -res;
    }

    private static double getWeightOfUnit(final int unit) {
        switch (unit) {
            case Position.BKing:
            case Position.WKing:
                return 1000D;
            case Position.BQueen:
            case Position.WQueen:
                return 450D;
            case Position.BRook:
            case Position.WRook:
                return 25D;
            case Position.BBishop:
            case Position.WBishop:
                return 12.5D;
            case Position.BKnight:
            case Position.WKnight:
                return 12.5D;
            case Position.BPawn:
            case Position.WPawn:
                return 5D;
            default:
                return 0.0D;
        }
    }

    private double UnitsCanBeEaten(final Map<Coordinate, Integer> myTroops, final Map<Coordinate, Integer> enemyTroops) {
        final ArrayList<Double> losses = new ArrayList<Double>();
        losses.add(0.0D);
        for (final Map.Entry<Coordinate, Integer> kvp : enemyTroops.entrySet()) {
            final Collection<Coordinate> threatened = getAttackingCoordinates(kvp.getValue(), kvp.getKey().x, kvp.getKey().y, myTroops, enemyTroops);
            for (final Map.Entry<Coordinate, Integer> myKvp : myTroops.entrySet()) {
                if (threatened.contains(myKvp.getKey())) {
                    if (myKvp.getValue() == Position.WPawn && rand.nextDouble() > 0.75D) {
                        losses.add(-100D);//Random feeding
                    } else {
                        losses.add(getWeightOfUnit(myKvp.getValue()));
                    }
                }
            }
        }
        Collections.sort(losses);
        return -losses.get(losses.size() - 1);
    }

    private static Collection<Coordinate> getAttackingCoordinates(final int unit, final int x, final int y, final Map<Coordinate, Integer> myTroops, final Map<Coordinate, Integer> enemyTroops) {
        final Collection<Coordinate> dirs = new HashSet<Coordinate>();
        switch (unit) {
            case Position.BKing:
                dirs.add(new Coordinate(x, y + 1));
                dirs.add(new Coordinate(x + 1, y));
                dirs.add(new Coordinate(x + 1, y + 1 - 1));
                dirs.add(new Coordinate(x, y - 1));
                dirs.add(new Coordinate(x - 1, y - 1));
                dirs.add(new Coordinate(x - 1, y));
                dirs.add(new Coordinate(x - 1, y + 1));
                break;
            case Position.BKnight:
                dirs.add(new Coordinate(x + 1, y + 2));
                dirs.add(new Coordinate(x - 1, y + 2));

                dirs.add(new Coordinate(x + 1, y - 2));
                dirs.add(new Coordinate(x - 1, y - 2));

                dirs.add(new Coordinate(x + 2, y + 1));
                dirs.add(new Coordinate(x + 2, y - 1));

                dirs.add(new Coordinate(x - 2, y + 1));
                dirs.add(new Coordinate(x - 2, y - 1));
                break;
            case Position.BPawn:
                dirs.add(new Coordinate(x + 1, y + 1));
                dirs.add(new Coordinate(x - 1, y + 1));
                break;
            case Position.BQueen:
                dirs.addAll(attacksToDir(x, y, 1, true, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, -1, true, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, 1, false, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, -1, false, myTroops, enemyTroops));
                break;
            case Position.BRook:
                dirs.addAll(attacksToDir(x, y, 1, true, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, -1, true, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, 1, false, myTroops, enemyTroops));
                dirs.addAll(attacksToDir(x, y, -1, false, myTroops, enemyTroops));
                break;
            case Position.BBishop:
                break;
            default:
                break;
        }
        return dirs;
    }

    private static Collection<Coordinate> attacksToDir(int x, int y, int m, boolean xAxis, final Map<Coordinate, Integer> myTroops, final Map<Coordinate, Integer> enemyTroops) {
        final Collection<Coordinate> dirs = new HashSet<Coordinate>();
        int add = 1;
        int yM = xAxis ? 0 : 1;
        int xM = xAxis ? 1 : 0;
        while (true) {
            final Coordinate newC = new Coordinate(m * (x + add) * xM, yM * (y + add) * m);
            if (myTroops.containsKey(newC) || enemyTroops.containsKey(newC) || x + add > Position.bRows || x - add < 0 || y + add > Position.bCols || y - add < 0) {
                break;
            }
            dirs.add(newC);
            add++;
        }
        return dirs;
    }

    private static final class Coordinate {

        private final int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + this.x;
            hash = 89 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (Coordinate.class != obj.getClass()) {
                return false;
            }
            final Coordinate other = (Coordinate) obj;
            if (this.x != other.x) {
                return false;
            }
            return this.y == other.y;
        }
    }
}
