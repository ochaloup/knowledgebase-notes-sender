package cz.chalda.knowledgebase.converter;

public enum ConverterType {
    ASCIIDOC(new AsciidocConverter());

    private Converter converter;

    private ConverterType(Converter converter) {
        this.converter = converter;
    }

    Converter getConverter() {
        return this.converter;
    }
}
