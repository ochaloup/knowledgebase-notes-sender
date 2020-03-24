package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.Selector;
import cz.chalda.knowledgebase.selector.SelectorType;

import java.util.Objects;

public class ExecutionConfiguration {
    private final String repository, repositoryRef;
    private final RepositoryType repositoryType;
    private final SelectorType selectorType;

    public ExecutionConfiguration(String repository,
                                  String repositoryRef,
                                  RepositoryType repositoryType,
                                  SelectorType selectorType) {
        this.repository = repository;
        this.repositoryRef = repositoryRef;
        this.repositoryType = repositoryType;
        this.selectorType = selectorType;
    }

    public String getRepository() {
        return this.repository;
    }

    public String getRepositoryRef() {
        return this.repositoryRef;
    }

    public RepositoryType getRepositoryType() {
        return this.repositoryType;
    }

    public SelectorType getSelectorType() {
        return this.selectorType;
    }

    @Override
    public String toString() {
        return "ExecutionConfiguration{" +
                "repository='" + repository + '\'' +
                ", repositoryRef='" + repositoryRef + '\'' +
                ", repositoryType=" + repositoryType +
                ", selectorType=" + selectorType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutionConfiguration)) return false;
        ExecutionConfiguration that = (ExecutionConfiguration) o;
        return Objects.equals(getRepository(), that.getRepository()) &&
                Objects.equals(getRepositoryRef(), that.getRepositoryRef()) &&
                getRepositoryType() == that.getRepositoryType() &&
                getSelectorType() == that.getSelectorType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRepository(), getRepositoryRef(), getRepositoryType(), getSelectorType());
    }

}
