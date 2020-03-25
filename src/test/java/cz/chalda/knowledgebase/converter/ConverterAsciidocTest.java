package cz.chalda.knowledgebase.converter;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class ConverterAsciidocTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Test
    public void convert() throws IOException {
        var converter = new AsciidocConverter();
        converter.convert(Paths.get("/home/ochaloup/tmp/adoc/test.adoc"));
    }
}
