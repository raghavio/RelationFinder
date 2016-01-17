package in.relationfinder;

import static org.junit.Assert.assertEquals;

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
        assertEquals("father", getRelationToX("mother's husband").get(0));
        assertEquals("son", getRelationToUser("mother's husband").get(0));

        assertEquals("mother", getRelationToX("father's wife").get(0));
        assertEquals("son", getRelationToUser("father's wife").get(0));

        assertEquals("brother", getRelationToX("brother").get(0));
        assertEquals("brother", getRelationToUser("brother").get(0));

        assertEquals("sister", getRelationToX("father's daughter").get(0));
        assertEquals("brother", getRelationToUser("father's daughter").get(0));

        /*assertEquals("father-in-law", getRelationToX("husband's father").get(0));
        assertEquals("daughter-in-law", getRelationToUser("husband's father").get(0));

        assertEquals("mother-in-law", getRelationToX("husband's mother").get(0));
        assertEquals("daughter-in-law", getRelationToUser("husband's mother").get(0));

        assertEquals("brother-in-law", getRelationToX("husband's brother").get(0));
        assertEquals("bhabhi", getRelationToUser("husband's brother").get(0));

        assertEquals("sister-in-law", getRelationToX("husband's sister").get(0));
        assertEquals("bhabhi", getRelationToUser("husband's sister").get(0));*/
    }

    @Test
    public void paternalGrandparentsFamily() {
        assertEquals("paternal grandfather", getRelationToX("father's father").get(0));
        assertEquals("pota", getRelationToUser("father's father").get(0));

        assertEquals("paternal grandmother", getRelationToX("father's mother").get(0));
        assertEquals("pota", getRelationToUser("father's mother").get(0));

        assertEquals("bua", getRelationToX("paternal_grandfather's daughter").get(0));
        assertEquals("bhatija", getRelationToUser("paternal_grandfather's daughter").get(0));

        assertEquals("chacha", getRelationToX("paternal_grandfather's son").get(0));
        assertEquals("bhatija", getRelationToUser("paternal_grandfather's son").get(0));
    }

    @Test
    public void usersFamily() {
        assertEquals("son", getRelationToX("daughter's brother").get(0));
        assertEquals("father", getRelationToUser("daughter's brother").get(0));
        assertEquals("mother", getRelationToUser("daughter's brother").get(1));

        assertEquals("daughter", getRelationToX("son's sister").get(0));
        assertEquals("father", getRelationToUser("son's sister").get(0));
        assertEquals("mother", getRelationToUser("son's sister").get(1));

        assertEquals("wife", getRelationToX("son's mother").get(0));
        assertEquals("husband", getRelationToUser("son's mother").get(0));
    }

    @Test
    public void sistersFamily() {
        assertEquals("jija", getRelationToX("sister's husband").get(0));
        assertEquals("brother-in-law", getRelationToUser("sister's husband").get(0));

        assertEquals("bhanja", getRelationToX("jija's son").get(0));
        assertEquals("maama", getRelationToUser("jija's son").get(0));

        assertEquals("bhanji", getRelationToX("bhanja's sister").get(0));
        assertEquals("maama", getRelationToUser("bhanja's sister").get(0));
    }

    @Test
    public void maternalGrandparentsFamily() {
        assertEquals("maternal grandfather", getRelationToX("mother's father").get(0));
        assertEquals("navaasa", getRelationToUser("mother's father").get(0));

        assertEquals("maternal grandmother", getRelationToX("mother's mother").get(0));
        assertEquals("navaasa", getRelationToUser("mother's mother").get(0));

        assertEquals("maama", getRelationToX("mother's brother").get(0));
        assertEquals("bhanja", getRelationToUser("mother's brother").get(0));

        assertEquals("mausii", getRelationToX("mother's sister").get(0));
        assertEquals("bhanja", getRelationToUser("mother's sister").get(0));
    }

    @Test
    public void brothersFamily() {
        assertEquals("bhabhi", getRelationToX("brother's wife").get(0));
        assertEquals("brother-in-law", getRelationToUser("brother's wife").get(0));

        assertEquals("bhatija", getRelationToX("bhabhi's son").get(0));
        assertEquals("chacha", getRelationToUser("bhabhi's son").get(0));

        assertEquals("bhatiji", getRelationToX("bhatija's sister").get(0));
        assertEquals("chacha", getRelationToUser("bhatija's sister").get(0));
    }

    @Test
    public void sonsFamily() {
        assertEquals("pota", getRelationToX("son's son").get(0));
        assertEquals("paternal grandfather", getRelationToUser("son's son").get(0));
        //assertEquals("paternal grandmother", getRelationToUser("son's son").get(1));

        assertEquals("poti", getRelationToX("son's daughter").get(0));
        assertEquals("paternal grandfather", getRelationToUser("son's daughter").get(0));
        //assertEquals("paternal grandmother", getRelationToUser("son's daughter").get(1));

        assertEquals("daughter-in-law", getRelationToX("son's wife").get(0));
        assertEquals("father-in-law", getRelationToUser("son's wife").get(0));
        assertEquals("mother-in-law", getRelationToUser("son's wife").get(1));
    }

    @Test
    public void daughtersFamily() {
        assertEquals("son-in-law", getRelationToX("daughter's husband").get(0));
        assertEquals("father-in-law", getRelationToUser("daughter's husband").get(0));
        //assertEquals("mother-in-law", getRelationToUser("daughter's husband").get(1));

        assertEquals("navaasa", getRelationToX("daughter's son").get(0));
        assertEquals("maternal grandfather", getRelationToUser("daughter's son").get(0));
        //assertEquals("maternal grandmother", getRelationToUser("daughter's son").get(1));

        assertEquals("navaasi", getRelationToX("daughter's daughter").get(0));
        assertEquals("maternal grandfather", getRelationToUser("daughter's daughter").get(0));
        //assertEquals("maternal grandmother", getRelationToUser("daughter's daughter").get(1));
    }

    @Test
    public void buasFamily() {
        assertEquals("phupha", getRelationToX("bua's husband").get(0));
        assertEquals("bhatija", getRelationToUser("bua's husband").get(0));

        assertEquals("cousin", getRelationToX("bua's son").get(0));
        assertEquals("cousin", getRelationToUser("bua's son").get(0));

        assertEquals("cousin", getRelationToX("bua's daughter").get(0));
        assertEquals("cousin", getRelationToUser("bua's daughter").get(0));
    }

    @Test
    public void chachasFamily() {
        assertEquals("chachi", getRelationToX("chacha's wife").get(0));
        assertEquals("bhatija", getRelationToUser("chacha's wife").get(0));

        assertEquals("cousin", getRelationToX("chacha's son").get(0));
        assertEquals("cousin", getRelationToUser("chacha's son").get(0));

        assertEquals("cousin", getRelationToX("chacha's daughter").get(0));
        assertEquals("cousin", getRelationToUser("chacha's daughter").get(0));
    }

    @Test
    public void maasisFamily() {
        assertEquals("mausaa", getRelationToX("mausii's husband").get(0));
        assertEquals("bhanja", getRelationToUser("mausii's husband").get(0));

        assertEquals("cousin", getRelationToX("mausii's son").get(0));
        assertEquals("cousin", getRelationToUser("mausii's son").get(0));

        assertEquals("cousin", getRelationToX("mausii's daughter").get(0));
        assertEquals("cousin", getRelationToUser("mausii's daughter").get(0));
    }

    @Test
    public void maamasFamily() {
        assertEquals("maami", getRelationToX("maama's wife").get(0));
        assertEquals("bhanja", getRelationToUser("maama's wife").get(0));

        assertEquals("cousin", getRelationToX("maama's son").get(0));
        assertEquals("cousin", getRelationToUser("maama's son").get(0));

        assertEquals("cousin", getRelationToX("maama's daughter").get(0));
        assertEquals("cousin", getRelationToUser("maama's daughter").get(0));
    }

    @Test
    public void wifesFamily() {
        assertEquals("father-in-law", getRelationToX("wife's father").get(0));
        assertEquals("son-in-law", getRelationToUser("wife's father").get(0));

        assertEquals("mother-in-law", getRelationToX("wife's mother").get(0));
        assertEquals("son-in-law", getRelationToUser("wife's mother").get(0));

        assertEquals("brother-in-law", getRelationToX("wife's brother").get(0));
        assertEquals("jija", getRelationToUser("wife's brother").get(0));

        assertEquals("sister-in-law", getRelationToX("wife's sister").get(0));
        assertEquals("jija", getRelationToUser("wife's sister").get(0));
    }

    private static List<String> getRelationToX(String query) {
        Object[] data = RelationsHandler.getRelation(query);
        System.out.println(data[2]);
        return (List<String>) data[1];
    }

    private static List<String> getRelationToUser(String query) {
        Object[] data = RelationsHandler.getRelation(query);
        return (List<String>) data[2];
    }
}
