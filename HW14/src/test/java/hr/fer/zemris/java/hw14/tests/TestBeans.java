package hr.fer.zemris.java.hw14.tests;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestBeans {

    private PollOption row;
    private Poll poll;

    @Before
    public void setUp() throws Exception {
        row = new PollOption(1, "Java", "java.zemris.fer.hr", 10);
        poll = new Poll(1, "Anketa", "Java Anketa");
    }

    @Test
    public void testPollOptionGetters() {
        assertEquals("Java", row.getName());
        assertEquals("java.zemris.fer.hr", row.getLink());
        assertEquals(10, row.getVotes());
        assertEquals(1, row.getId());
    }

    @Test
    public void testPollOptionSetters() {
        row.setLink("http://java.zemris.fer.hr");
        row.setName("Java8");
        row.setVotes(11);

        assertEquals("Java8", row.getName());
        assertEquals("http://java.zemris.fer.hr", row.getLink());
        assertEquals(11, row.getVotes());
    }

    @Test
    public void testPollGetters() {
        assertEquals("Anketa", poll.getTitle());
        assertEquals("Java Anketa", poll.getDescription());
        assertEquals(1, poll.getId());
    }

    @Test
    public void testPollSetters() {
        poll.setDescription("Java Anketa2");
        poll.setTitle("Anketa2");

        assertEquals("Anketa2", poll.getTitle());
        assertEquals("Java Anketa2", poll.getDescription());
        assertEquals(1, poll.getId());
    }

    @Test
    public void testPollOptionComparator() {
        PollOption entryOne = new PollOption(1, "Java", "Java", 5);
        PollOption entryTwo = new PollOption(2, "Phyton", "Phyton", 2);
        List<PollOption> result = new ArrayList<PollOption>();
        result.add(entryTwo);
        result.add(entryOne);
        assertEquals(1, result.indexOf(entryOne));
        Collections.sort(result);
        assertEquals(0, result.indexOf(entryOne));
    }

}
