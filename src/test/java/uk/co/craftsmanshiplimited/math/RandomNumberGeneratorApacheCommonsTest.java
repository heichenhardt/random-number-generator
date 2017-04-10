package uk.co.craftsmanshiplimited.math;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Henrik on 09/04/2017.
 */
@RunWith(JUnit4.class)
public class RandomNumberGeneratorApacheCommonsTest extends AbstractRandomNumberGeneratorTest {

    @Override
    Randomable createInstance(int[] integers, double[] probabilities) {
        return new RandomNumberGeneratorApacheCommons(integers, probabilities);
    }
}
