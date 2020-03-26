package cz.chalda.knowledgebase.converter;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;

public class ConverterAsciidocTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    // @Test
    public void convert() throws IOException {
        var converter = new AsciidocConverter();
        converter.convert(Paths.get("/home/ochaloup/tmp/adoc/test.adoc"));
    }

    // TODO: is good to be here?
    @Test
    public void getAsciidocFile() throws IOException {
        Path testFile = null;
        try {
            testFile = Files.createTempFile(null, ".asciidoc");
            var newFile = new AsciidocConverter().getWithNewSuffix(testFile,"html");
            Assertions.assertEquals(testFile.getParent(), newFile.getParent());
            log.atInfo().log("NEW FILE: " + newFile);
        } finally {
            Utils.rm(testFile);
        }
    }
}
