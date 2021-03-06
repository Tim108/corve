package corve.setup;

import corve.util.Chore;
import corve.util.EmailTemplates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class EmailTemplateReader {
    private String mapPath;

    public EmailTemplateReader(String mapPath, List<Chore> chores) {
        System.out.println("Creating email template reader..");
        this.mapPath = mapPath;
        File d = new File(mapPath);
        if (!d.exists() || !d.isDirectory()) //noinspection ResultOfMethodCallIgnored
            d.mkdir();

        boolean restart = false;
        for (Chore c : chores) {
            String filepath = mapPath + "/" + c.getName().replace(' ', '_');
            File f = new File(filepath);
            if (!f.exists() || f.isDirectory()) {
                restart = true;
                makeTemplate(filepath);
            }
        }

        if (restart) {
            System.out.println(mapPath + " and/or template files not present!");
            System.out.println("folder and files created, please fill in the templates");
            System.exit(1);
        }
        System.out.println("Creating email template reader done!");
    }

    public void readTemplates() {
        System.out.println("Reading email templates..");
        File d = new File(mapPath);
        File[] templates = d.listFiles();
        if (templates == null) return;
        for (File f : templates) {
            try {
                byte[] encoded = Files.readAllBytes(f.toPath());
                String template = new String(encoded, "UTF-8");
                EmailTemplates.addTemplate(f.getName().replace("_", " "), template);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Reading email templates done!");
    }

    private void makeTemplate(String path) {
        File f = new File(path);
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(getEmptyTemplate());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEmptyTemplate() {
        return "Please write template for the email that should be sent here. \n" +
                "In the name of the file you can see which chore this email will be about.\n" +
                "Use ##name## to refer to the room owners' name, ##chore## to refer to the chore, \n" +
                "##end_date## to refer to the deadline of the chore and ##code## to refer to \n" +
                "the code which is needed to complete the chore for the system.";
    }
}
