package cz.chalda.knowledgebase.converter;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;

import java.nio.file.Path;

class AsciidocConverter implements Converter {
    @Override
    public void convert(Path filePath) {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.convertFile(filePath.toFile(), new Options());
    }
}
