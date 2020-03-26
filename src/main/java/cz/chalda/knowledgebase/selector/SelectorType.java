package cz.chalda.knowledgebase.selector;

public enum SelectorType {
    SINGLE_FILE(new SingleFileSelector()),
    RANDOM(new RandomSelector());

    private Selector selector;

    private SelectorType(Selector selector) {
        this.selector = selector;
    }

    Selector getSelector() {
        return this.selector;
    }
}
