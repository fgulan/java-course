package fg47942.android.fer.hr.operations;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by Gulan on 02.07.2015..
 */
public class IOperationTest extends TestCase {

    private IOperation operation;
    private Double result;
    private static final double DELTA = 1e-8;

    @Test
    public void testAddOperation() {
        operation = new AddOperation();
        result = operation.calculate(23.0, 25.0);
        assertEquals(48.0, result, DELTA);
    }

    @Test
    public void testSubOperation() {
        operation = new SubOperation();
        result = operation.calculate(23.0, 25.0);
        assertEquals(-2.0, result, DELTA);
    }
    @Test
    public void testMulOperation() {
        operation = new MulOperation();
        result = operation.calculate(2.5, 2.0);
        assertEquals(5.0, result, DELTA);
    }
    @Test
    public void testDivOperation() {
        operation = new DivOperation();
        result = operation.calculate(20.0, 5.0);
        assertEquals(4.0, result, DELTA);
    }
}