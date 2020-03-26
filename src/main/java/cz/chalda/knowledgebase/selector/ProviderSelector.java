package cz.chalda.knowledgebase.selector;

import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public final class ProviderSelector implements ExecutionProvider {
    private static final ProviderSelector INSTANCE = new ProviderSelector();
    // package-private constructor
    ProviderSelector() {}
    public static ProviderSelector getInstance() {
        return ProviderSelector.INSTANCE;
    }

    public ExecutionContext provide(final ExecutionContext context) {
        if(context == null) {
            return context.setError("No execution context provided, the context param is null");
        }
        Optional<Selector> selector = Optional.empty();
        var repositoryLocation = context.getRepositoryLocation();
        try {
            selector = getSelector(context.getConfiguration().getSelectorType(), repositoryLocation);
            if (selector.isEmpty()) {
                return context.setError("Cannot find proper selector to handle repository location '%s'", repositoryLocation);
            }
            var pathToKnowledgeBaseNote = selector.get().select(repositoryLocation);
            return context.setKnowledgebaseNotePath(pathToKnowledgeBaseNote);
        } catch (Exception e) {
            return context.setError(e, "Error on processing selector '%s' to handle repository location '%s'",
                    selector.isEmpty() ? "null" : selector.get().toString(), repositoryLocation);
        }
    }

    Optional<Selector> getSelector(final SelectorType selectorType, final Path repositoryPath) {
        if(selectorType != null) {
            switch (selectorType) {
                case RANDOM:
                    return Optional.of(SelectorType.RANDOM.getSelector());
                case SINGLE_FILE:
                    return Optional.of(SelectorType.SINGLE_FILE.getSelector());
            }
        }
        return Arrays.stream(SelectorType.values())
                .filter(st -> st.getSelector().mayHandle(repositoryPath))
                .findFirst()
                .map(st -> Optional.of(st.getSelector()))
                .orElse(Optional.empty());
    }
}
