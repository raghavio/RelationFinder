package in.relationfinder;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Raghav
 */
public class Constants {

    /**
     * Key values pair of relation and its other names.
     */
    public static final HashMap<String, ArrayList<String>> RELATION_NAMES_MAP;

    /**
     * Values of RELATION_NAMES_MAP.
     * All other names of every relation defined in YAML file.
     */
    public static final List<String> OTHER_RELATION_NAMES;

    /**
     * Key values of RELATION_NAMES_MAP.
     * Contains relation names defined as Prolog rules in Prolog knowledgebase.
     */
    public static final List<String> RELATION_NAMES;

    static {
        InputStream input = ContextListener.class.getClassLoader().getResourceAsStream("/relationship_names.yaml");
        Yaml yaml = new Yaml();
        RELATION_NAMES_MAP = (HashMap<String, ArrayList<String>>) yaml.load(input);

        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<ArrayList<String>> relationNamesMap = RELATION_NAMES_MAP.values();
        OTHER_RELATION_NAMES = new ArrayList<>(relationNamesMap.size());
        relationNamesMap.stream().filter(otherNames -> otherNames != null).forEach(OTHER_RELATION_NAMES::addAll);

        Set<String> relationNamesKey = RELATION_NAMES_MAP.keySet();
        RELATION_NAMES = new ArrayList<>(relationNamesKey.size());
        RELATION_NAMES.addAll(relationNamesKey.stream().collect(Collectors.toList()));
    }

}
