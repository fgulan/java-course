package hr.fer.zemris.java.hw13.test;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.hw13.beans.Band;
import hr.fer.zemris.java.hw13.servlets.ServerUtilty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestServerUtility {

    private List<Band> bands;
    private Map<Integer, Integer> votes;
    private List<Band> results;

    @Before
    public void setUp() throws Exception {
        Path pathBand = Paths.get("lib/Bands_Test.txt").toAbsolutePath();
        bands = ServerUtilty.getBands(pathBand.toString());
        Path pathVotes = Paths.get("lib/Bands_Votes.txt").toAbsolutePath();
        votes = ServerUtilty.getVotes(pathVotes.toString());
        results = ServerUtilty.getResults(pathBand.toString(),
                pathVotes.toString());
    }

    @Test
    public void testGetBands() {
        Band band = new Band("The Beatles", null, null, 1);
        assertEquals(true, bands.contains(band));
    }

    @Test
    public void testGetBandsSize() {
        assertEquals(7, bands.size());
    }

    @Test
    public void testGetVotes() {
        assertEquals(Integer.valueOf(13), votes.get(Integer.valueOf(1)));
    }

    @Test
    public void testGetVotesSize() {
        assertEquals(7, votes.size());
    }

    @Test
    public void testGetResultsSize() {
        assertEquals(7, results.size());
    }

    @Test
    public void testGetResults() {
        Band band = new Band("The Beatles", null, null, 1);
        int index = results.indexOf(band);
        assertEquals(Integer.valueOf(13), results.get(index).getVotes());
    }
}
