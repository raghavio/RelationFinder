package in.relationfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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
        raw_query = raw_query.toLowerCase();
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

        request.setAttribute("results", results);
        request.setAttribute("relationToUser", data[2]);
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

}
