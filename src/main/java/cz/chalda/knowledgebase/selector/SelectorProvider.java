package cz.chalda.knowledgebase.selector;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

public final class SelectorProvider {
    private SelectorProvider() {
        throw new IllegalStateException("cannot be instantiated");
    }

    public static Selector provide(ExecutionConfiguration conf) {
        if(conf.getSelectorType() == null || conf.getSelectorType() == SelectorType.RANDOM) {
            return new RandomSelector();
        } else {
            throw new IllegalStateException("Cannot find proper selector instance");
        }
    }
}
