package cz.chalda.knowledgebase.cli;

import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.SelectorType;
import picocli.CommandLine;

@CommandLine.Command(name = "knowledgebase notes sender", mixinStandardHelpOptions = true, version = "1.0.0.Alpha")
public class CliArgs {
    @CommandLine.Option(names = {"-r", "--repository"}, description = "repository to use", required = true)
    protected String repository;

    @CommandLine.Option(names = {"-rr", "--ref"}, description = "repository reference (like brach or tag of git repository)")
    protected String repositoryReference;

    @CommandLine.Option(names = {"-rt", "--repository-type"}, description = "repository type")
    protected RepositoryType repositoryType;

    @CommandLine.Option(names = {"-s", "--selector-type"}, description = "selector type")
    protected SelectorType selectorType;
}