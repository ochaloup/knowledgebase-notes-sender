package cz.chalda.knowledgebase.selector;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

public final class SelectorProvider implements ExecutionProvider {
    private static final SelectorProvider INSTANCE = new SelectorProvider();
    // package-private constructor
    SelectorProvider() {}
    public static SelectorProvider getInstance() {
        return SelectorProvider.INSTANCE;
    }

    public ExecutionContext provide(final ExecutionContext context) {
        var selector = getSelector(context.getConfiguration());
        var pathToKnowledgeBaseNote = selector.select(context.getRepositoryPath());
        return context.setKnowledgebaseNotePath(pathToKnowledgeBaseNote);
    }

    Selector getSelector(ExecutionConfiguration conf) {
        if(conf.getSelectorType() == null || conf.getSelectorType() == SelectorType.RANDOM) {
            return new RandomSelector();
        } else {
            throw new IllegalStateException("Cannot find proper selector instance");
        }
    }
}
