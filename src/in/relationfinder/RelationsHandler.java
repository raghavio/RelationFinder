package in.relationfinder;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
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
     * @param query       The query array
     * @return A string array of relation(s). There can be more than one relation.
     * Ex. grandparent's son = father and uncle.
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    public static String[] getRelation(String contextPath, String[] query) throws MalformedURLException {
        String knowledgeBase = "familyrelationships.pl";
        Query consult = new Query("consult", new Term[]{new Atom(contextPath + '/' + knowledgeBase)});
        if (!consult.hasSolution())
            return null;

        StringBuilder builder = new StringBuilder();
        char alphabet = 'A';
        for (int i = 0; i < query.length; i++) {
            builder.append("query_family(").append(alphabet).append(", ");
            if (i == 0)
                builder.append("raghav, ");
            else
                builder.append((char) (alphabet - 1)).append(", ");
            builder.append(query[i]).append("), ");
            if (i == query.length - 1)
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
