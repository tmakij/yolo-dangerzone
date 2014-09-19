package fi.helsinki.cs.shakki;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import position.Evaluator;
import position.Position;

/**
 * @author Timo Mäki
 */
public final class YourEvaluator extends Evaluator {

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
        if (position >= Position.WKing && position <= Position.WPawn) {
            myTroops.put(new Coordinate(x, y), position);
        } else if (position >= Position.BKing && position <= Position.BPawn) {
            enemyTroops.put(new Coordinate(x, y), position);
        }
        switch (position) {
            case Position.WKing:
                return 1000D;
            case Position.WQueen:
                return 100D;
            case Position.WRook:
                return 10D;
            case Position.WBishop:
                return 10D;
            case Position.WKnight:
                return 10D;
            case Position.WPawn:
                return 5D;
            case Position.BKing:
                return -1000D;
            case Position.BQueen:
                return -100D;
            case Position.BRook:
                return -10D;
            case Position.BBishop:
                return -10D;
            case Position.BKnight:
                return -10D;
            case Position.BPawn:
                return -5D;
            default:
                return 0.0D;
        }
    }

    private static double UnitsCanBeEaten(final Map<Coordinate, Integer> myTroops, final Map<Coordinate, Integer> enemyTroops) {
        double troopLosses = 0D;
        for (final Map.Entry<Coordinate, Integer> kvp : enemyTroops.entrySet()) {
            final Collection<Coordinate> threatened = getAttackingCoordinates(kvp.getValue(), kvp.getKey().x, kvp.getKey().y);
            for (final Map.Entry<Coordinate, Integer> myKvp : myTroops.entrySet()) {
                if (threatened.contains(myKvp.getKey())) {
                    troopLosses -= 25D;
                }
            }
        }
        return troopLosses;
    }

    private static Collection<Coordinate> getAttackingCoordinates(final int unit, final int x, final int y) {
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
            default:
                break;
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
