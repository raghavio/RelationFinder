package in.relationfinder;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Tests for RelationHandler class.
 *
 * @author Raghav
 */
public class RelationHandlerTest {

    @Test
    public void fathersFamily() {
        assertEquals("father", getRelation("mother's husband").get(0));
        assertEquals("mother", getRelation("father's wife").get(0));
        assertEquals("brother", getRelation("brother's").get(0));
        assertEquals("sister", getRelation("father's daughter").get(0));
    }

    private static List<String> getRelation(String query) {
        Object[] data = RelationsHandler.getRelation(query);
        return (List<String>) data[1];
    }

}
