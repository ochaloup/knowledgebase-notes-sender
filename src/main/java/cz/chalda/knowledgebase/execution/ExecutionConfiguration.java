package cz.chalda.knowledgebase.execution;

import cz.chalda.knowledgebase.converter.ConverterType;
import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.SelectorType;

import java.util.Objects;

public class ExecutionConfiguration {
    private final String inputLocation, repositoryRef;
    private final RepositoryType repositoryType;
    private final SelectorType selectorType;
    private final ConverterType converterType;

    public ExecutionConfiguration(String inputLocation,
                                  String repositoryRef,
                                  RepositoryType repositoryType,
                                  SelectorType selectorType,
                                  ConverterType converterType) {
        this.inputLocation = inputLocation;
        this.repositoryRef = repositoryRef;
        this.repositoryType = repositoryType;
        this.selectorType = selectorType;
        this.converterType = converterType;
    }

    public String getInputLocation() {
        return this.inputLocation;
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

    public ConverterType getConverterType() {
        return this.converterType;
    }

    @Override
    public String toString() {
        return "ExecutionConfiguration{" +
                "inputLocation='" + inputLocation + '\'' +
                ", repositoryRef='" + repositoryRef + '\'' +
                ", repositoryType=" + repositoryType +
                ", selectorType=" + selectorType +
                ", converterType=" + converterType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutionConfiguration)) return false;
        ExecutionConfiguration that = (ExecutionConfiguration) o;
        return Objects.equals(getInputLocation(), that.getInputLocation()) &&
                Objects.equals(getRepositoryRef(), that.getRepositoryRef()) &&
                getRepositoryType() == that.getRepositoryType() &&
                getSelectorType() == that.getSelectorType() &&
                getConverterType() == that.getConverterType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInputLocation(), getRepositoryRef(), getRepositoryType(), getSelectorType(), getConverterType());
    }
}
