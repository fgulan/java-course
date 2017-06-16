package hr.fer.zemris.java.hw13.test;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.hw13.beans.Band;
import hr.fer.zemris.java.hw13.beans.TrigonometricFun;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestBeans {

    private Band band;
    private TrigonometricFun function;
    private static final double DELTA = 1E-8;

    @Before
    public void setUp() throws Exception {
        band = new Band("Iron Maiden", "www.ironmaiden.com", 5, 1);
        function = new TrigonometricFun(0);
    }

    @Test
    public void testBandGetters() {
        assertEquals("Iron Maiden", band.getName());
        assertEquals("www.ironmaiden.com", band.getLink());
        assertEquals(Integer.valueOf(5), band.getVotes());
        assertEquals(Integer.valueOf(1), band.getId());
    }

    @Test
    public void testTrigonometricGetters() {
        assertEquals(0, function.getValue());
        assertEquals(1, function.getCosValue(), DELTA);
        assertEquals(0, function.getSinValue(), DELTA);
    }
}
