package cz.chalda.knowledgebase.converter;

import cz.chalda.knowledgebase.execution.ExecutionContext;

import java.nio.file.Path;

public interface Converter {
    /**
     * Takes a path to asciidoc file and convert it to html file.
     *
     * @param filePath  a file to convert
     * @return TODO...
     */
    Path convert(Path filePath);
}
