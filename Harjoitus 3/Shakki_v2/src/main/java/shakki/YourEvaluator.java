package shakki;

import java.util.Random;
import position.Evaluator;
import position.Position;

public final class YourEvaluator extends Evaluator {

    private final Random rand = new Random();

    @Override
    public double eval(final Position p) {
        double ret = rand.nextDouble() * 0.5D;
        final int boradLenght = p.board.length;
        for (int x = 0; x < boradLenght; ++x) {
            final int xBoard[] = p.board[x];
            final int xLenght = xBoard.length;
            for (int y = 0; y < xLenght; ++y) {
                final int unit = xBoard[y];
                final double value = getWeightOfUnit(unit, x, y);
                ret += unit >= Position.WKing && unit <= Position.WPawn ? value : -value;
            }
        }
        return ret;
    }

    private static double getWeightOfUnit(final int unit, final int x, final int y) {
        final int arrayLocation = x + y * 6;
        switch (unit) {
            case Position.BKing:
            case Position.WKing:
                return 20000D + king[arrayLocation];
            case Position.BQueen:
            case Position.WQueen:
                return 900D + queen[arrayLocation];
            case Position.BRook:
            case Position.WRook:
                return 500D + rook[arrayLocation];
            case Position.BKnight:
            case Position.WKnight:
                return 320D + knight[arrayLocation];
            case Position.BPawn:
            case Position.WPawn:
                return 100D + pawn[arrayLocation];
            default:
                return 0.0D;
        }
    }

    private static final double pawn[] = new double[]{
        0, 0, 0, 0, 0, 0,
        50, 50, 50, 50, 50, 50,
        10, 20, 30, 30, 20, 10,
        5, -10, 20, 20, -10, 5,
        5, 10, -20, -20, 10, 5,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final double knight[] = new double[]{
        -50, -40, -30, -30, -40, -50,
        -40, -20, 0, 0, -20, -40,
        -30, 0, 15, 15, 0, -30,
        -30, 5, 15, 15, 5, -30,
        -40, -20, 5, 5, -20, -40,
        -50, -40, -30, -30, -40, -50
    };

    private static final double rook[] = new double[]{
        0, 0, 0, 0, 0, 0,
        5, 10, 10, 10, 10, 5,
        -5, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, -5,
        0, 0, 5, 5, 0, 0
    };

    private static final double queen[] = new double[]{
        -20, -10, -5, -5, -10, -20,
        -10, 0, 0, 0, 0, -10,
        -10, 2.5, 5, 5, 2.5, -10,
        -5, 2.5, 5, 5, 2.5, -5,
        0, 2.5, 5, 5, 2.5, -5,
        -10, 5, 5, 5, 0, -10,
        -10, 0, 5, 0, 0, -10,
        -20, -10, -5, -5, -10, -20
    };

    private static final double king[] = new double[]{
        -30, -40, -50, -50, -40, -30,
        -30, -40, -50, -50, -40, -30,
        -30, -40, -50, -50, -40, -30,
        -30, -40, -50, -50, -40, -30,
        -20, -30, -40, -40, -30, -20,
        -10, -20, -20, -20, -20, -10,
        20, 20, 0, 0, 20, 20,
        20, 30, 10, 10, 30, 20
    };
}
