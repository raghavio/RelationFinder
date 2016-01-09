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
        assertEquals("brother", getRelation("brother").get(0));
        assertEquals("sister", getRelation("father's daughter").get(0));
    }

    @Test
    public void paternalGrandparentsFamily() {
        assertEquals("paternal grandfather", getRelation("father's father").get(0));
        assertEquals("paternal grandmother", getRelation("father's mother").get(0));
        assertEquals("bua", getRelation("paternal_grandfather's daughter").get(0));
        assertEquals("chacha", getRelation("paternal_grandfather's son").get(0));
    }

    @Test
    public void usersFamily() {
        assertEquals("son", getRelation("daughter's brother").get(0));
        assertEquals("daughter", getRelation("son's sister").get(0));
        assertEquals("wife", getRelation("son's mother").get(0));
        assertEquals("mother-in-law", getRelation("husband's mother").get(0));
    }

    @Test
    public void sistersFamily() {
        assertEquals("jija", getRelation("sister's husband").get(0));
        assertEquals("bhanja", getRelation("jija's son").get(0));
        assertEquals("bhanji", getRelation("bhanja's sister").get(0));
    }

    @Test
    public void maternalGrandparentsFamily() {
        assertEquals("maternal grandfather", getRelation("mother's father").get(0));
        assertEquals("maternal grandmother", getRelation("mother's mother").get(0));
        assertEquals("maama", getRelation("mother's brother").get(0));
        assertEquals("mausii", getRelation("mother's sister").get(0));
    }

    @Test
    public void brothersFamily() {
        assertEquals("bhabhi", getRelation("brother's wife").get(0));
        assertEquals("bhatija", getRelation("bhabhi's son").get(0));
        assertEquals("bhatiji", getRelation("bhatija's sister").get(0));
    }

    @Test
    public void sonsFamily() {
        assertEquals("pota", getRelation("son's son").get(0));
        assertEquals("poti", getRelation("son's daughter").get(0));
        assertEquals("daughter-in-law", getRelation("son's wife").get(0));
    }

    @Test
    public void daughtersFamily() {
        assertEquals("son-in-law", getRelation("daughter's husband").get(0));
        assertEquals("navaasa", getRelation("daughter's son").get(0));
        assertEquals("navaasi", getRelation("daughter's daughter").get(0));
    }

    private static List<String> getRelation(String query) {
        Object[] data = RelationsHandler.getRelation(query);
        return (List<String>) data[1];
    }

}
