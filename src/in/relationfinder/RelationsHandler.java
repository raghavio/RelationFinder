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
     * Gets relation by running the prolog query using JPL (SWI-Prolog engine for Java).
     *
     * @param contextPath Path to WEB-INF
     * @param query The query array
     * @return relation(s)
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    public static String getRelation(String contextPath, String[] query) throws MalformedURLException {
        String knowledgeBase = "familyrelationships.pl";
        Query consult = new Query("consult", new Term[] {new Atom(contextPath + '/' + knowledgeBase)});
        if (!consult.hasSolution())
            return null;

        StringBuilder builder = new StringBuilder();
        char alphabet = 'A';
        for (int i = 0; i < query.length; i++) {
            builder.append(
                    "query_family(" + alphabet + ", " +
                            (i == 0 ? "User, " : (char)(alphabet - 1) + ", ")
                            + query[i] + "), "
                            + (i != query.length - 1 ? "" : "query_family(" + alphabet + ", User, Result).")
            );
            alphabet++;
        }
        String prolog_query = builder.toString();
        Map<String, Term> result = Query.oneSolution(prolog_query);
        if (result == null || result.isEmpty())
            return null;
        return result.get("Result").toString();
    }

}
