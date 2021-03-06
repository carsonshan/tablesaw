package tech.tablesaw.plotly;

import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Page;
import tech.tablesaw.plotly.display.Browser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Displays plots in a development setting, by exporting a file containing the HTML and Javascript, and then opening
 * the file in the default browser on the developer's machine.
 */
public class Plot {

    private static final String DEFAULT_DIV_NAME = "target";
    private static final String DEFAULT_OUTPUT_FILE = "output.html";
    private static final String DEFAULT_OUTPUT_FILE_NAME = "output";
    private static final String DEFAULT_OUTPUT_FOLDER = "testoutput";

    public static void show(Figure figure, String divName, File outputFile) {
        Page page = Page.pageBuilder(figure, divName).build();
        String output = page.asJavascript();

        try {
            try (FileWriter fileWriter = new FileWriter(outputFile)) {
                fileWriter.write(output);
            }
            new Browser().browse(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(Figure figure, String divName) {
        show(figure, divName, defaultFile());
    }

    public static void show(Figure figure) {
        show(figure, randomFile());
    }

    public static void show(Figure figure, File outputFile) {
        show(figure, DEFAULT_DIV_NAME, outputFile);
    }

    private static File defaultFile() {
        Path path = Paths.get(DEFAULT_OUTPUT_FOLDER, DEFAULT_OUTPUT_FILE);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e){
            e.printStackTrace();
        }
        return path.toFile();
    }

    private static File randomFile() {
        Path path = Paths.get(DEFAULT_OUTPUT_FOLDER, randomizedFileName());
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e){
            e.printStackTrace();
        }
        return path.toFile();
    }

    private static String randomizedFileName() {
        return DEFAULT_OUTPUT_FILE_NAME + UUID.randomUUID().toString() + ".html";
    }
}
