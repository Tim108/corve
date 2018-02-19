package corve.util;

import java.util.HashMap;
import java.util.Map;

public class EmailTemplates {
    private static Map<String, String> TEMPLATES = new HashMap<>();

    public static void addTemplate(String choreName, String template) {
        TEMPLATES.put(choreName, template);
    }

    public static String getTemplate(String choreName, String roomName, String endDate, String code) {
        return TEMPLATES.get(choreName).replace("##chore##", choreName).replace("##name##", roomName).replace("##end_date##", endDate).replace("##code##", code);
    }
}
