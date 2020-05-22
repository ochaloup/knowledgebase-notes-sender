package cz.chalda.knowledgebase.converter;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.Utils;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConverterAsciidocTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private Path tempFolder;

    @BeforeEach
    public void setUp() throws IOException {
        tempFolder = Files.createTempDirectory("asciidoc-conversion-");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Utils.rm(tempFolder);
    }

    @Test
    public void isSuffixCorrect() throws IOException {
        Path testFile = Files.createFile(tempFolder.resolve("suffix-test.asciidoc"));
        final String newSuffix = "misc";

        var newFile = new AsciidocConverter().getFilePathWithNewSuffix(testFile, newSuffix);
        Assertions.assertTrue(newFile.getFileName().toString().endsWith(newSuffix),
            "File '" + newFile + "' does not end with suffix " + newSuffix);
        Assertions.assertEquals(testFile.getParent(), newFile.getParent(),
            "The newly defined file " + newFile + " with suffix " + newSuffix + " is at different place than"
                + " the original file " + testFile);
    }

    @Test
    public void asciidocConversion() throws IOException {
        Path originalFile = Paths.get("src", "test", "resources", ConverterAsciidocTest.class.getSimpleName() + ".adoc");
        Assertions.assertTrue(originalFile.toFile().exists(), "The input file '" + originalFile + "' does not exit");

        Path testFile = tempFolder.resolve("asciidoc-conversion.adoc");
        Files.copy(originalFile, testFile);
        Assertions.assertTrue(testFile.toFile().exists(), "The test input file '" + testFile + "' does not exit");

        Path convertedFile = new AsciidocConverter().convert(testFile);
        Assertions.assertTrue(convertedFile.toFile().exists(), "The converted file '" + convertedFile + "' does not exit");
        Assertions.assertTrue(convertedFile.getFileName().toString().endsWith("html"),
                "File '" + convertedFile.getFileName().toString() + "' does not end with suffix 'html'");

        var convertedFileLines = Files.readAllLines(convertedFile);
        Assertions.assertFalse(convertedFileLines.stream().filter(s -> s.contains("<a")).findAny().isEmpty(),
                "The converted file does not contain any link with string '<a'");
        Assertions.assertFalse(convertedFileLines.stream().filter(s -> s.contains("chalda.cz")).findAny().isEmpty(),
                "The converted file does not contain string 'chalda.cz'");
        Assertions.assertFalse(convertedFileLines.stream().filter(s -> s.contains("<ul>")).findAny().isEmpty(),
                "The converted file does not contain any list element '<ul>'");
        Assertions.assertFalse(convertedFileLines.stream().filter(s -> s.contains("<h1")).findAny().isEmpty(),
                "The converted file does not contain any header element '<h1'");
        Assertions.assertFalse(convertedFileLines.stream().filter(s -> s.contains("<h2")).findAny().isEmpty(),
                "The converted file does not contain any header element '<h2'");
    }
}
