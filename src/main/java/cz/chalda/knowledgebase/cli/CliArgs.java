package cz.chalda.knowledgebase.cli;

import cz.chalda.knowledgebase.converter.ConverterType;
import cz.chalda.knowledgebase.output.OutputType;
import cz.chalda.knowledgebase.repository.RepositoryType;
import cz.chalda.knowledgebase.selector.SelectorType;
import picocli.CommandLine;

@CommandLine.Command(name = "knowledgebase notes sender", mixinStandardHelpOptions = true, version = "1.0.0.Alpha")
public class CliArgs {
    @CommandLine.Option(names = {"-f", "--configfile"}, description = "configuration file to load properties from (default location to search is ~/.knowledgebase-notes)")
    protected String configFile;

    @CommandLine.Option(names = {"-i", "--input"}, description = "input location (e.g. git repository url) to use")
    protected String inputLocation;

    @CommandLine.Option(names = {"-r", "--ref"}, description = "repository reference (ie. brach or tag of git repository)")
    protected String repositoryReference;

    @CommandLine.Option(names = {"-rt", "--repository"}, description = "repository type")
    protected RepositoryType repositoryType;

    @CommandLine.Option(names = {"-s", "--selector"}, description = "selector type")
    protected SelectorType selectorType;

    @CommandLine.Option(names = {"-c", "--converter"}, description = "converter type")
    protected ConverterType converterType;

    @CommandLine.Option(names = {"-ot", "--output-type"}, description = "converter type")
    protected OutputType outputType;

    @CommandLine.Option(names = {"-o", "--output"}, description = "location for output")
    protected String output;
}