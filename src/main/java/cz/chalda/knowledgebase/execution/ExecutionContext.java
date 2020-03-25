package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.converter.ConverterType;
import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.SelectorType;

import java.nio.file.Path;

public class ExecutionContext {
    private final ExecutionConfiguration executionConfiguration;
    private Path repositoryPath, knowledgebaseNotePath;

    private ExecutionContext(ExecutionConfiguration conf) {
        this.executionConfiguration = conf;
    }

    public ExecutionConfiguration getConfiguration() {
        return this.executionConfiguration;
    }

    public ExecutionContext setRepositoryPath(Path repositoryPath) {
        this.repositoryPath = repositoryPath;
        return this;
    }
    public Path getRepositoryPath() {
        return this.repositoryPath;
    }


    public ExecutionContext setKnowledgebaseNotePath(Path knowledgebaseNotePath) {
        this.knowledgebaseNotePath = knowledgebaseNotePath;
        return this;
    }
    public Path getKnowledgebaseNotePath() {
        return this.knowledgebaseNotePath;
    }


    public static class Builder {
        private String repository, repositoryRef;
        private RepositoryType repositoryType;
        private SelectorType selectorType;
        private ConverterType converterType;

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
        public Builder selectorType(SelectorType selectorType) {
            this.selectorType = selectorType;
            return this;
        }
        public Builder converterType(ConverterType converterType) {
            this.converterType = converterType;
            return this;
        }

        public ExecutionContext build() {
            ExecutionConfiguration conf = new ExecutionConfiguration(
                    this.repository,
                    this.repositoryRef,
                    this.repositoryType,
                    this.selectorType,
                    this.converterType
            );
            return new ExecutionContext(conf);
        }
    }
}
