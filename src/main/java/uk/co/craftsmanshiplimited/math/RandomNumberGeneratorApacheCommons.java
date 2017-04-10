package uk.co.craftsmanshiplimited.math;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotFiniteNumberException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotANumberException;

/**
 * Created by Henrik on 09/04/2017.
 */
public class RandomNumberGeneratorApacheCommons implements Randomable {

    private final EnumeratedIntegerDistribution distribution;

    public RandomNumberGeneratorApacheCommons(
            final int[] randomNumbers,
            final double[] probabilities) {

        try {
            this.distribution =
                    new EnumeratedIntegerDistribution(
                        randomNumbers, probabilities);

        } catch (final DimensionMismatchException
                | NotPositiveException
                | MathArithmeticException
                | NotFiniteNumberException
                | NotANumberException e) {

            //Wrap the implementation specific exceptions.
            final String message =
                    e.getClass().getSimpleName()
                            .replace("Exception", "") + " " + e.getMessage();
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public final int nextNum() {
        return this.distribution.sample();
    }
}
