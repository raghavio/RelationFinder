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
     * All other names of every relation mentioned in YAML file.
     */
    public static final List<String> OTHER_RELATION_NAMES;

    /**
     * Contains relation names defined as Prolog rules in Prolog knowledgebase.
     */
    public static final Set<String> RELATION_NAMES;

    /**
     * Key value pairs of relation and its gender.
     */
    public static final HashMap<String, String> GENDER;

    /**
     * Path to resources folder
     */
    public static final String RESOURCES_PATH = Constants.class.getResource("/").getPath();

    static {
        InputStream input = Constants.class.getResourceAsStream("/relationship_names.yml");
        Yaml yaml = new Yaml();
        HashMap<String, HashMap<String, Object>> yamlData = (HashMap<String, HashMap<String, Object>>) yaml.load(input);
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GENDER = new HashMap<>(yamlData.size());
        OTHER_RELATION_NAMES = new ArrayList<>();
        RELATION_NAMES_MAP = new HashMap<>(yamlData.size());
        RELATION_NAMES = yamlData.keySet();

        for (String key : RELATION_NAMES) {
            HashMap<String, Object> data = yamlData.get(key);
            GENDER.put(key, (String) data.get("gender"));
            ArrayList<String> otherNames = (ArrayList<String>) data.get("other_names");
            if (otherNames == null)
                continue;
            RELATION_NAMES_MAP.put(key, otherNames);
            OTHER_RELATION_NAMES.addAll((otherNames).stream().collect(Collectors.toList()));
        }
    }

}
