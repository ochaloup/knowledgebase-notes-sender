package cz.chalda.knowledgebase.selector;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SelectorRandomTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private Path tempFolder;

    @AfterEach
    public void cleanUp() throws IOException {
        Utils.rm(tempFolder);
    }

    @Test
    public void noFileInDirectory() throws IOException {
        tempFolder = Files.createTempDirectory("nofile-selector-test-");
        log.atFine().log("Created selector testing folder at %s", tempFolder);
        Assertions.assertNull(new RandomSelector().select(tempFolder),
                "No asciidoc file available at test directory, selector expected to return null");

    }

    @Test
    public void oneFileinDirectory() throws IOException {
        tempFolder = Files.createTempDirectory("onefile-selector-test-");
        log.atFine().log("Created selector testing folder at %s", tempFolder);
        Files.createFile(tempFolder.resolve("test1.jpg"));
        var asciidocPath = tempFolder.resolve("test1.adoc");
        Files.createFile(asciidocPath);
        Assertions.assertEquals(asciidocPath, new RandomSelector().select(tempFolder),
                "Only one asciidoc file available at test directory, it has to be selected");
    }

    @Test
    public void singleFile() throws IOException {
        tempFolder = Files.createTempDirectory("single-file-selector-test-");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new RandomSelector().select(Files.createFile(tempFolder.resolve("test1")));
        });
    }
}
