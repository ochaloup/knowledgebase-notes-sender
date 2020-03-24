package cz.chalda.knowledgebase.cli;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.repository.RepositoryProcessor;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

public class Main extends CliArgs implements Callable<Integer> {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    // business logic goes here, arguments parsing wrapped here by Picocli library
    @Override
    public Integer call() throws Exception {
        ExecutionContext context = ExecutionContext.Builder.instance()
                .repository(this.repository)
                .repositoryRef(this.repositoryReference)
                .repositoryType(this.repositoryType)
                .build();

        Path repositoryPath = RepositoryProcessor.get(context.getConfiguration());
        if(repositoryPath == null) {
            log.atSevere().log("Cannot find repository handler capable to get data from repository '%s'", this.repository);
        }
        log.atInfo().log(">>>>> %s", repositoryPath);
        return 0;
    }

    // Running of the code is done via Picocli library
    // main method only let the library to parse the cli arguments and then executes via Callable.call
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitCode);
    }
}
