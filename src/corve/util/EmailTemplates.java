package corve.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 19/10/2017.
 */
public class EmailTemplates {
    private static Map<String, String> TEMPLATES = new HashMap<>();

    public static void addTemplate(String choreName, String template) {
        TEMPLATES.put(choreName, template);
    }

    public static String getTemplate(String choreName, String endDate, String code) {
        return TEMPLATES.get(choreName).replace("##chore##", choreName).replace("##end_date##", endDate).replace("##code##", code);
    }
}
