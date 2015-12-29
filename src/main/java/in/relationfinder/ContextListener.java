package in.relationfinder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads relationship data from Yaml file.
 *
 * @author Raghav
 */
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // We add keys (original relation names) and values (different names of relation) to one list.
        // This list is for jquery autocomplete.
        List<String> allRelations = new ArrayList<>();
        allRelations.addAll(Constants.OTHER_RELATION_NAMES);
        allRelations.addAll(Constants.RELATION_NAMES);
        servletContextEvent.getServletContext().setAttribute("all_relations", allRelations);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
