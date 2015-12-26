package in.relationfinder;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;

import java.net.MalformedURLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Handles all the stuff related to Relationships. Couldn't think of another name :/.
 * Using JIProlog to query Prolog.
 *
 * @author Raghav
 */
public class RelationsHandler {

    /**
     * Gets relation by running the Prolog query using JIProlog.
     *
     * @param contextPath Path to WEB-INF
     * @param raw_query   The query input by user as it is. Ex. father's mother's sister
     * @return A list of relation(s). There can be more than one relation sometimes.
     * Ex. grandparent's son = father and uncle.
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    public static List<String> getRelation(String contextPath, String raw_query) throws MalformedURLException {
        String knowledgeBase = "familyrelationships.pl";
        String[] relations = processQuery(raw_query);
        String prolog_query = generatePrologQuery(relations);
        List<String> results = null;

        JIPEngine jipEngine = new JIPEngine();
        jipEngine.setSearchPath(contextPath);
        try {
            jipEngine.consultFile(knowledgeBase);
            JIPQuery jipQuery = jipEngine.openSynchronousQuery(prolog_query);
            if (jipQuery.hasMoreChoicePoints())
                results = new ArrayList<>();
            while (jipQuery.hasMoreChoicePoints()) {
                JIPTerm jipTerm = jipQuery.nextSolution();
                if (jipTerm == null)
                    break;
                Hashtable hashtable = jipTerm.getVariablesTable();
                if (!hashtable.containsKey("Result"))
                    break;
                String result = hashtable.get("Result").toString();
                results.add(result);
            }
        } catch (JIPSyntaxErrorException e) {
            //TODO Logging
            return null;
        }

        return results;
    }

    /**
     * Processes user query.
     * Removes redundant characters and changes other relation names to actual name used in Prolog facts.
     * For ex, if there's daddy in query it gets changed to father, because that's what we used in our Prolog knowledge base.
     *
     * @param raw_query The query input by user as it is.
     * @return String array of relations. Basically query.split(" ").
     */
    private static String[] processQuery(String raw_query) {
        String query;
        query = raw_query.replaceAll("'s", ""); //Replace all apostrophe s
        String[] relations = query.split(" ");

        for(int i = 0; i < relations.length; i++) //Whoa
            if (YamlHandler.OTHER_RELATION_NAMES.contains(relations[i]))
                for (Entry<String, ArrayList<String>> entry : YamlHandler.RELATION_NAMES_MAP.entrySet())
                    for (String otherName : entry.getValue())
                        if (Objects.equals(relations[i], otherName))
                            relations[i] = entry.getKey();
        return relations;
    }

    /**
     * TODO Document this in detail
     * Generates a Prolog query string to query family relations in Prolog using JIProlog.
     * @param relations A String array of relations on the basis of which we return the query.
     *                  Ex. ['father', 'mother'] means relation to my father's mother.
     * @return Prolog query string
     */
    private static String generatePrologQuery(String[] relations) {
        StringBuilder builder = new StringBuilder();

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
        return builder.toString();
    }

    /**
     * Returns other names of a relation.
     *
     * @param relation Relation
     */
    public static Object[] getOtherNames(String relation) {
        if (YamlHandler.RELATION_NAMES_MAP.containsKey(relation)) {
            ArrayList<String> otherNames = new ArrayList<>(YamlHandler.RELATION_NAMES_MAP.get(relation));
            otherNames.add(0, relation);
            return otherNames.toArray();
        } else
            return new String[] {relation};
    }

}
