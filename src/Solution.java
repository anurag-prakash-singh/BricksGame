import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Hashtable;

/**
 * Created by anurags on 8/13/15.
 */
public class Solution {
    private static Hashtable<Integer, BigInteger> sScoreCache;

    private static BigInteger minOfBigIntegers(BigInteger[] bigIntegers) {
        if (bigIntegers.length == 0) {
            return new BigInteger("0");
        }

        BigInteger minBigInteger = bigIntegers[0];

        for (int i = 1; i < bigIntegers.length; i++) {
            if (minBigInteger.compareTo(bigIntegers[i]) > 0) {
                minBigInteger = bigIntegers[i];
            }
        }

        return minBigInteger;
    }

    private static BigInteger maxOfBigIntegers(BigInteger[] bigIntegers) {
        if (bigIntegers.length == 0) {
            return new BigInteger("0");
        }

        BigInteger maxBigInteger = bigIntegers[0];

        for (int i = 1; i < bigIntegers.length; i++) {
            if (maxBigInteger.compareTo(bigIntegers[i]) < 0) {
                maxBigInteger = bigIntegers[i];
            }
        }

        return maxBigInteger;
    }

    private static BigInteger getMaxScoreBottomUp(int[] brickValues) {
        BigInteger maxScore = new BigInteger("0");

        for (int i = brickValues.length - 1; i >= 0; i--) {
            maxScore = getMaxScore(brickValues, i);
        }

        return maxScore;
    }

    private static BigInteger getMaxScore(int[] brickValues, int from) {
        if (from >= brickValues.length) {
            return new BigInteger("0");
        }

        if (sScoreCache.containsKey(from)) {
            return sScoreCache.get(from);
        }

        BigInteger[] nextMoveScores = new BigInteger[7];

        for (int i = 2; i < nextMoveScores.length; i++) {
            nextMoveScores[i] = getMaxScore(brickValues, from + i);
        }

        // If a is chosen
        BigInteger choiceAScore = new BigInteger(brickValues[from] + "").
                add(
                        minOfBigIntegers(new BigInteger[]{
                                nextMoveScores[2],
                                nextMoveScores[3],
                                nextMoveScores[4]}));

        // If a, b are chosen
        BigInteger choiceBScore = new BigInteger("0");

        if (from + 1 < brickValues.length) {
            choiceBScore = new BigInteger(brickValues[from] + "")
            .add(new BigInteger(brickValues[from + 1] + ""))
            .add(
                    minOfBigIntegers(new BigInteger[]{
                            nextMoveScores[3],
                            nextMoveScores[4],
                            nextMoveScores[5]}));
        }

        // If a, b, c are chosen
        BigInteger choiceCScore = new BigInteger("0");

        if (from + 2 < brickValues.length) {
            choiceCScore = new BigInteger(brickValues[from] + "")
                    .add(new BigInteger(brickValues[from + 1] + ""))
                    .add(new BigInteger(brickValues[from + 2] + ""))
                    .add(
                            minOfBigIntegers(new BigInteger[]{
                                    nextMoveScores[4],
                                    nextMoveScores[5],
                                    nextMoveScores[6]}));
        }

        BigInteger maxScore = maxOfBigIntegers(
                new BigInteger[] {choiceAScore, choiceBScore, choiceCScore}
        );

        sScoreCache.put(from, maxScore);

        return maxScore;
    }

    public static void main(String[] args) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());

            for (int i = 0; i < n; i++) {
                int numBricks = Integer.parseInt(br.readLine());
                String brickValuesLine = br.readLine();
                String[] brickValuesComponents = brickValuesLine.split(" ");

                int[] bricks = new int[numBricks];

                for (int j = 0; j < numBricks; j++) {
                    bricks[j] = Integer.parseInt(brickValuesComponents[j]);
                }

                sScoreCache = new Hashtable<Integer, BigInteger>();
                BigInteger maxScore = getMaxScoreBottomUp(bricks);

                System.out.println(maxScore);
            }
        } catch (Exception exception) {
        }
    }
}
