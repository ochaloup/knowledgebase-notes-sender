package cz.chalda.knowledgebase;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.repository.RepositoryType;

public final class ExecutionConfigurationProvider {
    private ExecutionConfigurationProvider() {
        throw new IllegalStateException("no constructor, only an utility class");
    }

    public static ExecutionConfiguration repository(String repository, String ref, RepositoryType type) {
        return new ExecutionConfiguration(repository, ref, type);
    }
}
