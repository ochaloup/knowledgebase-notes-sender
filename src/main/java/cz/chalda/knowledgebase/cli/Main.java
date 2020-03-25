package cz.chalda.knowledgebase.cli;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.repository.RepositoryProvider;
import cz.chalda.knowledgebase.selector.SelectorProvider;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class Main extends CliArgs implements Callable<Integer> {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    // business logic goes here, main method wrapped by Picocli library
    @Override
    public Integer call() throws Exception {
        ExecutionContext context = ExecutionContext.Builder.instance()
                .repository(this.repository)
                .repositoryRef(this.repositoryReference)
                .repositoryType(this.repositoryType)
                .selectorType(this.selectorType)
                .converterType(this.converterType)
                .build();

        RepositoryProvider.getInstance().provide(context);
        if(context.getRepositoryPath() == null) {
            log.atSevere().log("Cannot find repository handler capable to get data from repository '%s'", this.repository);
            return 1;
        }

        SelectorProvider.getInstance().provide(context);
        if(context.getKnowledgebaseNotePath() == null) {
            log.atSevere().log("Cannot find any valid knowledge base note at path '%s'", context.getKnowledgebaseNotePath());
            return 1;
        }

        log.atInfo().log("MAIN: >>>>> %s", context.getKnowledgebaseNotePath());
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
