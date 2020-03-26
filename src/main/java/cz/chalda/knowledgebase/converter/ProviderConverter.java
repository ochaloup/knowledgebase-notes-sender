package cz.chalda.knowledgebase.converter;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

import java.util.Optional;

public final class ProviderConverter implements ExecutionProvider {
    private static final ProviderConverter INSTANCE = new ProviderConverter();
    // package-private constructor
    ProviderConverter() {}
    public static ProviderConverter getInstance() {
        return ProviderConverter.INSTANCE;
    }

    public ExecutionContext provide(final ExecutionContext context) {
        if(context == null) {
            return context.setError("No execution context provided, the context param is null");
        }

        Optional<Converter> converter = Optional.empty();
        var pathToKnowledgeBaseNoteFile = context.getKnowledgebaseNotePath();
        try {
            converter = getConvertor(context.getConfiguration());
            if (converter.isEmpty()) {
                return context.setError("Cannot find proper converter provider to handle path to the knowledgebase note '%s'",
                        pathToKnowledgeBaseNoteFile);
            }
            // TODO: :-)
            converter.get().convert(pathToKnowledgeBaseNoteFile);
            return context;
        } catch (Exception e) {
            return context.setError(e, "Error on processing conversion via provider '%s' of knowledgebase note '%s'",
                    converter.isEmpty() ? "null" : converter.get().toString(), pathToKnowledgeBaseNoteFile);
        }
    }

    Optional<Converter> getConvertor(ExecutionConfiguration conf) {
        if(conf.getConverterType() == null || conf.getConverterType() == ConverterType.ASCIIDOC) {
            return Optional.of(ConverterType.ASCIIDOC.getConverter());
        }
        return Optional.empty();
    }
}
