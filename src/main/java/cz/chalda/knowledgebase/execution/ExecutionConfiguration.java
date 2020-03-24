package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.repository.RepositoryType;

import java.util.Objects;

public class ExecutionConfiguration {
    private final String repository, repositoryRef;
    private final RepositoryType repositoryType;

    public ExecutionConfiguration(String repository,
                                   String repositoryRef,
                                   RepositoryType repositoryType) {
        this.repository = repository;
        this.repositoryRef = repositoryRef;
        this.repositoryType = repositoryType;
    }

    public String getRepository() {
        return repository;
    }

    public String getRepositoryRef() {
        return repositoryRef;
    }

    public RepositoryType getRepositoryType() {
        return repositoryType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutionConfiguration)) return false;
        ExecutionConfiguration taskData = (ExecutionConfiguration) o;
        return Objects.equals(repository, taskData.repository) &&
                Objects.equals(repositoryRef, taskData.repositoryRef) &&
                repositoryType == taskData.repositoryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository, repositoryRef, repositoryType);
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "repository='" + repository + '\'' +
                ", repositoryRef='" + repositoryRef + '\'' +
                ", repositoryType=" + repositoryType +
                '}';
    }
}
