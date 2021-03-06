package cz.chalda.knowledgebase.converter;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;

import java.nio.file.Path;

/**
 * Converting the asciidoc file to html.
 * The output is the same place as the original file was placed
 * but with suffix <code>html</code>.
 */
class AsciidocConverter implements Converter {
    @Override
    public Path convert(Path filePath) {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.convertFile(filePath.toFile(), new Options());
        return getFilePathWithNewSuffix(filePath, "html");
    }

    Path getFilePathWithNewSuffix(Path originalFilePath, String newSuffix) {
        String filename = originalFilePath.getFileName().toString();
        filename = filename.substring(0, filename.lastIndexOf("."));
        return originalFilePath.getParent().resolve(filename + "." + newSuffix);
    }
}
