package uk.co.craftsmanshiplimited.math;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.exception.*;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Henrik on 09/04/2017.
 */
public class RandomNumberGeneratorCustom implements Randomable {

    private final int[] randomNumbers;
    private final double[] probabilities;
    private final Random random;

    public RandomNumberGeneratorCustom(
            final int[] randomNumbers,
            final double[] probabilities) {

        this.random = new Random();
        this.randomNumbers = randomNumbers;

        if(randomNumbers.length != probabilities.length) {
            throw new IllegalArgumentException(
                    "DimensionMismatch " + randomNumbers.length + "!=" + probabilities.length);
        }
        if(Arrays.stream(probabilities).anyMatch(x->x < 0)) {
            throw new IllegalArgumentException(
                    "NotPositive - Probabilities must be 0 or positive" );
        }

        if(Arrays.stream(probabilities).anyMatch(x-> Double.isInfinite(x))) {
            throw new IllegalArgumentException(
                    "NotFiniteNumber - Probabilities must be finit" );
        }
        if(Arrays.stream(probabilities).anyMatch(x-> Double.isNaN(x))) {
            throw new IllegalArgumentException(
                    "NotANumber - Probabilities must not be NaN" );
        }

        final double[] normalizedProbabilities =
                MathArrays.normalizeArray(probabilities, 1.0);
        this.probabilities = new double[normalizedProbabilities.length];
        double sum=0;
        for(int index=0; index < normalizedProbabilities.length; index++) {
            sum += normalizedProbabilities[index];
            this.probabilities[index] = sum;
        }
    }

    @Override
    public final int nextNum() {
        double randomNumber = this.random.nextDouble();
        for(int index=0; index<this.probabilities.length; index++) {
            if(randomNumber < this.probabilities[index]) {
                return this.randomNumbers[index];
            }
        }
        return this.randomNumbers[this.randomNumbers.length - 1];
    }
}
