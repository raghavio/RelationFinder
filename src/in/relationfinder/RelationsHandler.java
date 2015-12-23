package in.relationfinder;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import java.net.MalformedURLException;
import java.util.ArrayList;
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
     * @param raw_query   The query input by user as it is. Ex. father's mother's sister
     * @return A string array of relation(s). There can be more than one relation.
     * Ex. grandparent's son = father and uncle.
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    public static String[] getRelation(String contextPath, String raw_query) throws MalformedURLException {
        String knowledgeBase = "familyrelationships.pl";
        Query consult = new Query("consult", new Term[]{new Atom(contextPath + '/' + knowledgeBase)});
        if (!consult.hasSolution())
            return null;

        StringBuilder builder = new StringBuilder();
        String query = processQuery(raw_query);
        String[] relations = query.split(" ");
        char alphabet = 'A';
        for (int i = 0; i < relations.length; i++) {
            builder.append("query_family(").append(alphabet).append(", ");
            if (i == 0)
                builder.append("raghav, ");
            else
                builder.append((char) (alphabet - 1)).append(", ");
            builder.append(relations[i]).append("), ");
            if (i == relations.length - 1)
                builder.append("query_family(").append(alphabet).append(", raghav, Result).");
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

    /**
     * Processes user query. Removes redundant characters.
     * @param raw_query The query input by user as it is.
     * @return processed query.
     */
    private static String processQuery(String raw_query) {
        String query;
        query = raw_query.replaceAll("'s", ""); //Replace all apostrophe s
        return query;
    }

    /**
     * Returns other names of a relation.
     *
     * @param relation Relation
     */
    public static Object[] getOtherNames(String relation) {
        if (YamlHandler.RELATION_NAMES.containsKey(relation)) {
            ArrayList<String> otherNames = new ArrayList<>(YamlHandler.RELATION_NAMES.get(relation));
            otherNames.add(0, relation);
            return otherNames.toArray();
        } else
            return new String[] {relation};
    }

}
