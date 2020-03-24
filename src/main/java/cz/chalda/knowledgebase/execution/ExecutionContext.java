package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.repository.RepositoryType;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

public class ExecutionContext {
    private final ExecutionConfiguration executionConfiguration;
    private Path repositoryPath;

    private ExecutionContext(ExecutionConfiguration conf) {
        this.executionConfiguration = conf;
    }

    public ExecutionConfiguration getConfiguration() {
        return this.executionConfiguration;
    }

    public void setRepositoryPath(Path repositoryPath) {
        this.repositoryPath = repositoryPath;
    }
    public Path getRepositoryPath() {
        return this.repositoryPath;
    }

    public static class Builder {
        private String repository, repositoryRef;
        private RepositoryType repositoryType;

        // private constructor
        private Builder() {}
        public static ExecutionContext.Builder instance() {
            return new ExecutionContext.Builder();
        }

        public Builder repository(String repository) {
            this.repository = repository;
            return this;
        }
        public Builder repositoryRef(String repositoryRef) {
            this.repositoryRef = repositoryRef;
            return this;
        }
        public Builder repositoryType(RepositoryType repositoryType) {
            this.repositoryType = repositoryType;
            return this;
        }
        public ExecutionContext build() {
            ExecutionConfiguration conf = new ExecutionConfiguration(
                    this.repository,
                    this.repositoryRef,
                    this.repositoryType
            );
            return new ExecutionContext(conf);
        }
    }
}
