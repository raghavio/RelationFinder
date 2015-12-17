package in.relationfinder;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Finds the relation by running a Prolog query.
 *
 * @author Raghav
 */
@WebServlet(name = "FindRelationServlet", urlPatterns = {"/find"})
public class FindRelationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String raw_query = request.getParameter("query"); //Ex: father's mother's daughter
        String query_without_punctuation = raw_query.replaceAll("'s", "");

        String[] query = query_without_punctuation.split(" ");

        String relation = getRelation(query);

        request.setAttribute("results", relation);
        request.setAttribute("query", raw_query);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Gets relation by running the prolog query using JPL (SWI-Prolog engine for Java).
     * @param query The query array
     * @return relation(s)
     * @throws MalformedURLException Throws if couldn't find servlet context pa
     */
    private String getRelation(String[] query) throws MalformedURLException {
        String path = getServletContext().getResource("/WEB-INF").getPath();
        Query consult = new Query("consult", new Term[] {new Atom(path + "/familyrelationships.pl")});
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
        String relation = result.get("Result").toString();
        return relation;
    }
}
