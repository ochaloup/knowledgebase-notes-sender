package cz.chalda.knowledgebase.execution;

public interface ExecutionProvider {
    ExecutionContext provide(ExecutionContext context);
}
