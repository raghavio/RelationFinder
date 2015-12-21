package in.relationfinder;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Handles all the stuff related to Relationships. Couldn't think of another name :/.
 * Using JPL to query Prolog.
 *
 * @author Raghav
 */
public class RelationsHandler {

    /**
     * Gets relation by running the Prolog query using JPL (SWI-Prolog engine for Java).
     *
     * @param contextPath Path to WEB-INF
     * @param query The query array
     * @return A string array of relation(s). There can be more than one relation.
     *         Ex. grandparent's son = father and uncle.
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    public static String[] getRelation(String contextPath, String[] query) throws MalformedURLException {
        String knowledgeBase = "familyrelationships.pl";
        Query consult = new Query("consult", new Term[] {new Atom(contextPath + '/' + knowledgeBase)});
        if (!consult.hasSolution())
            return null;

        StringBuilder builder = new StringBuilder();
        char alphabet = 'A';
        for (int i = 0; i < query.length; i++) {
            builder.append(
                    "query_family(" + alphabet + ", " +
                            (i == 0 ? "raghav, " : (char)(alphabet - 1) + ", ")
                            + query[i] + "), "
                            + (i != query.length - 1 ? "" : "query_family(" + alphabet + ", raghav, Result).")
            );
            alphabet++;
        }
        String prolog_query = builder.toString();
        Map<String, Term>[] results = Query.allSolutions(prolog_query);
        if (results == null)
            return null;

        String[] relation = new String[results.length];
        for (int i = 0; i < results.length; i++) {
            relation[i] = results[i].get("Result").name();
        }

        return relation;
    }

}
