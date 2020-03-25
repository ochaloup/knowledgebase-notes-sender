package cz.chalda.knowledgebase.converter;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

public final class ConverterProvider implements ExecutionProvider {
    private static final ConverterProvider INSTANCE = new ConverterProvider();
    // package-private constructor
    ConverterProvider() {}
    public static ConverterProvider getInstance() {
        return ConverterProvider.INSTANCE;
    }

    public ExecutionContext provide(final ExecutionContext context) {
        var pathToKnowledgeBaseNoteFile = context.getKnowledgebaseNotePath();
        var converter = getConvertor(context.getConfiguration());
        converter.convert(pathToKnowledgeBaseNoteFile);
        return context;
    }

    Converter getConvertor(ExecutionConfiguration conf) {
        if(conf.getConverterType() == null || conf.getConverterType() == ConverterType.ASCIIDOC) {
            return new AsciidocConverter();
        } else {
            throw new IllegalStateException("Cannot find proper convertor instance");
        }
    }
}
