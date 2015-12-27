package in.relationfinder;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gets the search query, finds relation and renders it to user.
 *
 * @author Raghav
 */
@WebServlet(name = "FindRelationServlet", urlPatterns = {"/find"})
public class FindRelationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String raw_query = request.getParameter("query"); //Ex: father's mother's daughter

        String resourcesPath = getClass().getResource("/").getPath();
        List<String> relations = RelationsHandler.getRelation(resourcesPath, raw_query);

        Object[] results = null;

        if (relations != null) {
            results = new Object[relations.size()];
            for (int i = 0; i < results.length; i++) {
                Object[] allNames = RelationsHandler.getOtherNames(relations.get(i));
                results[i] = allNames;
            }
        }

        request.setAttribute("results", results);
        request.setAttribute("query", raw_query);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

}
