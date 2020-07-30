import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ihar_Sakun
 */
public class FileUtilsTest {

    private static final String INVALID_PATH = "src/main/resources/nciowdu.txt";
    private static final String VALID_PATH = "src/main/resources/testsTable.txt";
    private static final String RESULT_PATH = "src/main/resources/testsTable_res.txt";
    private static final String ORIGIN_PATH = "src/main/resources/testsTableOriginal.txt";

    @BeforeEach
    public void setUp() {
        FileUtils.setup(ORIGIN_PATH, VALID_PATH);
    }

    @Test
    public void invalidPathTest() {
        String result = FileUtils.selectTests(INVALID_PATH);
        assertEquals("file not found", result);
    }

    @Test
    public void verifyResultFileExistsTest() {
        FileUtils.selectTests(VALID_PATH);
        assertTrue(new File(RESULT_PATH).exists());
    }

    @Test
    public void validateNumberSelectedTest() {
        int numberToSelect = 3;
        FileUtils.selectTests(VALID_PATH, numberToSelect);
        long numberOfLines = FileUtils.countLines(RESULT_PATH);
        assertEquals(numberToSelect + 1, numberOfLines, "Wrong number of lines selected");
    }

    @Test
    public void validateNumberLeftOriginalTest() {
        int numberToSelect = 3;
        long numberOfOriginalLines = FileUtils.countLines(VALID_PATH);
        FileUtils.selectTests(VALID_PATH, numberToSelect);
        long numberOfOriginalLinesLeft = FileUtils.countLines(VALID_PATH);
        assertEquals(numberOfOriginalLines - numberToSelect, numberOfOriginalLinesLeft, "Wrong number of lines cut");
    }

}
