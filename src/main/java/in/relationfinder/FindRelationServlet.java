package in.relationfinder;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gets the search query, finds relation and dispatches request to jsp.
 *
 * @author Raghav
 */
public class FindRelationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String raw_query = request.getParameter("query"); //Ex: father's mother's daughter
        if (raw_query == null || raw_query.isEmpty())
            raw_query = getRandomQuery(getServletConfig().getInitParameter("query"));
        Object[] data = RelationsHandler.getRelation(raw_query); // new Object[] {gender, results}
        String gender = (String) data[0];
        List<String> relations = (List<String>) data[1];

        HashMap<String, ArrayList<String>> results = null;
        if (relations != null) {
            results = new HashMap<>(relations.size());
            for (String relation : relations) {
                ArrayList<String> otherNames = RelationsHandler.getOtherNames(relation);
                results.put(relation, otherNames);
            }
        }

        String array = getOrgChatArray((String[]) data[2]);
        request.setAttribute("chart_array", array);
        request.setAttribute("results", results);
        request.setAttribute("gender", gender);
        request.setAttribute("query", raw_query);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("_index.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Gets random query from Init Parameter. You can only store one value per parameter.
     * So we seprate our queries by newline and store it all in one string. We then split it and get any random query.
     *
     * @param initParameter Value of the Init parameter.
     * @return Any random query.
     */
    private String getRandomQuery(String initParameter) {
        String[] queries = initParameter.split("\n");
        String randomQuery = queries[new Random().nextInt(queries.length)];
        randomQuery = randomQuery.trim();
        return randomQuery;

    }

    private String getOrgChatArray(String[] relations) {
        TreeMap<Integer, List<String>> result = new TreeMap<>(Collections.reverseOrder());
        String[] relation_with_self = new String[relations.length + 1];
        relation_with_self[0] = "self";
        System.arraycopy(relations, 0, relation_with_self, 1, relation_with_self.length - 1);

        int sum = 0;
        for (String relation : relation_with_self) {
            int level = 0;
            if (relation.equals("father") || relation.equals("mother"))
                level = 1;
            else if (relation.equals("son") || relation.equals("daughter"))
                level = -1;

            sum += level;
            if (result.get(sum) == null) {
                List<String> relations_on_level = new ArrayList<>();
                relations_on_level.add(relation);
                result.put(sum, relations_on_level);
            } else {
                result.get(sum).add(relation);
            }
        }

        StringBuilder array = new StringBuilder();
        for (Integer key : result.keySet()) {
            for (String values : result.get(key)) {
                String name = key + " " + values;
                List<String> parents = result.get(key + 1);
                if (parents == null) {
                    array.append("['").append(name).append("', ''],");
                } else {
                    String parent = key+1 + " " + parents.get(0);
                    array.append("['").append(name).append("', '").append(parent).append("'],");
                }
            }
        }
        return array.toString();
    }

}
