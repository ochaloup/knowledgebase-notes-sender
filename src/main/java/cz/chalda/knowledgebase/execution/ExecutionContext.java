package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.converter.ConverterType;
import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.SelectorType;
import cz.chalda.knowledgebase.utils.StackTrace;

import java.nio.file.Path;

public class ExecutionContext {
    private final ExecutionConfiguration executionConfiguration;
    private Path repositoryLocation, knowledgebaseNotePath;

    private volatile boolean isError = false;
    private String errorMessage, stackTrace;

    private ExecutionContext(ExecutionConfiguration conf) {
        this.executionConfiguration = conf;
    }

    public ExecutionConfiguration getConfiguration() {
        return this.executionConfiguration;
    }

    public ExecutionContext setRepositoryLocation(Path repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
        return this;
    }
    public Path getRepositoryLocation() {
        return this.repositoryLocation;
    }


    public ExecutionContext setKnowledgebaseNotePath(Path knowledgebaseNotePath) {
        this.knowledgebaseNotePath = knowledgebaseNotePath;
        return this;
    }
    public Path getKnowledgebaseNotePath() {
        return this.knowledgebaseNotePath;
    }

    /* --------- ERROR INFO variables ------------ */
    public boolean isError() {
        return this.isError;
    }

    public String getErrorMessage() {
        return String.format("%s%n%s", this.errorMessage, this.stackTrace);
    }

    public ExecutionContext setError(String message, Object... formatArgs) {
        return setError(StackTrace.getStackTrace(), message, formatArgs);
    }

    public ExecutionContext setError(Exception e, String message, Object... formatArgs) {
        return setError(StackTrace.exceptionToStackTrace(e), message, formatArgs);
    }

    private ExecutionContext setError(String stackTrace, String message, Object... formatArgs) {
        this.errorMessage = String.format(message, formatArgs);
        this.stackTrace = stackTrace;
        this.isError = true;
        return this;
    }

    /* --------- Execution Context Builder ------------ */
    public static class Builder {
        private String inputLocation, repositoryRef;
        private RepositoryType repositoryType;
        private SelectorType selectorType;
        private ConverterType converterType;

        // private constructor
        private Builder() {}
        public static ExecutionContext.Builder instance() {
            return new ExecutionContext.Builder();
        }

        public Builder inputLocation(String inputLocation) {
            this.inputLocation = inputLocation;
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
                    this.inputLocation,
                    this.repositoryRef,
                    this.repositoryType,
                    this.selectorType,
                    this.converterType
            );
            return new ExecutionContext(conf);
        }
    }
}
