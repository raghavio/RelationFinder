package in.relationfinder;

import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Loads relationship data from Yaml file.
 *
 * @author Raghav
 */
@WebListener
public class YamlHandler implements ServletContextListener {

    public static HashMap<String, ArrayList<String>> RELATION_NAMES;

    public static List<String> OTHER_RELATION_NAMES;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InputStream input = RelationsHandler.class.getClassLoader().getResourceAsStream("/relationship_names.yaml");
        Yaml yaml = new Yaml();
        RELATION_NAMES = (HashMap<String, ArrayList<String>>) yaml.load(input);
        List<String> allRelations = new ArrayList<>();
        RELATION_NAMES.values().stream().filter(names -> names != null).forEach(allRelations::addAll);
        OTHER_RELATION_NAMES = new ArrayList<>(allRelations);
        RELATION_NAMES.keySet().forEach(allRelations::add); //Adds all relation names (as defined in prolog) to allRelations
        servletContextEvent.getServletContext().setAttribute("all_relations", allRelations);
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
