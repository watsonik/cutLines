import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Ihar_Sakun
 */
public class FileUtils {
    private static Charset charset = StandardCharsets.UTF_8;

    public static String selectTests(String dataPath, int... numberToSelect) {
        int numberOfTests = numberToSelect.length > 0 ? numberToSelect[0] : 10;
        String selectedTestsPath = dataPath.replace(".", "_res.");

        try (Stream<String> stream = Files.lines(Paths.get(dataPath))) {
            ArrayList<String> initialTestCases = stream.collect(Collectors.toCollection(ArrayList::new));
            String header = initialTestCases.get(0);
            initialTestCases.remove(0);
            List<String> shuffeledTestCases = new ArrayList<>(initialTestCases);
            Collections.shuffle(shuffeledTestCases);
            List<String> selectedTestCases = shuffeledTestCases.subList(0, numberOfTests);
            initialTestCases.removeAll(selectedTestCases);
            selectedTestCases.add(0, header);
            initialTestCases.add(0, header);
            writeFile(selectedTestCases, selectedTestsPath);
            writeFile(initialTestCases, dataPath);
        } catch (NoSuchFileException e) {
            return "file not found";
        } catch (IndexOutOfBoundsException ex) {
            return "Not enough row to select";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedTestsPath;
    }

    private static void writeFile(List<String> data, String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        Files.write(Paths.get(file.getPath()), data, charset);
    }

    public static long countLines(String dataPath) {
        try (Stream<String> stream = Files.lines(Paths.get(dataPath))) {
            return stream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setup(String originPath, String validPath) {
        Path source = Paths.get(originPath);
        Path dest = Paths.get(validPath);
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
