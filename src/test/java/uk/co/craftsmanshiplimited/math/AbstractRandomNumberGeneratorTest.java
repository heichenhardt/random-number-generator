package uk.co.craftsmanshiplimited.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Henrik on 10/04/2017.
 */
public abstract class AbstractRandomNumberGeneratorTest {
    public static final int ITERATIONS = 100000;
    public static final int COUNT = 10;
    private Randomable randomNumberGenerator;

    abstract Randomable createInstance(int[] integers, double[] probabilities);

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorOnMisMatchedIntegerAndProbabilitiesInput() throws Exception {
        this.randomNumberGenerator = createInstance(new int[]{1, 2, 3}, new double[]{0.1, 0.1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorOnNegativeProbabilitiesInput() throws Exception {
        this.randomNumberGenerator = createInstance(new int[]{1, 2, 3}, new double[]{0.1, -0.1, 0.1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorOnInfinityProbabilitiesInput() throws Exception {
        this.randomNumberGenerator = createInstance(new int[]{1, 2, 3}, new double[]{0.1, 1.0 / 0.0, 0.1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorOnNANProbabilitiesInput() throws Exception {
        this.randomNumberGenerator = createInstance(new int[]{1, 2, 3}, new double[]{0.1, Double.NaN, 0.1});
    }

    @Test
    public void shouldCalculateCorrectProbabilities() throws Exception {
        final Random random = new Random();
        int[] integers =
                Stream.generate(random::nextInt)
                        .mapToInt(x->x)
                        .limit(COUNT)
                        .sorted()
                        .toArray();

        double[] probabilities =
                Stream.generate(random::nextDouble)
                        .mapToDouble(x->x)
                        .limit(COUNT)
                        .toArray();

        this.randomNumberGenerator = createInstance(integers, probabilities);

        //Run test
        final Map<Integer, Long> testResult =
                Stream.generate(randomNumberGenerator::nextNum)
                    .limit(COUNT * ITERATIONS)
                    .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        assertEquals(COUNT * ITERATIONS, testResult.values().stream().mapToLong(x->x).sum());

        double sumOfProb = Arrays.stream(probabilities).sum();
        testResult.entrySet().stream().forEach(entry -> {
            int index = Arrays.binarySearch(integers, entry.getKey());
            assertEquals(
                    probabilities[index] / sumOfProb,
                    (double)entry.getValue() / (COUNT * ITERATIONS),
                    0.001);
        });
    }
}
