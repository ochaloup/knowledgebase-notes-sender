package cz.chalda.knowledgebase.cli;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.converter.ProviderConverter;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.repository.ProviderRepository;
import cz.chalda.knowledgebase.selector.ProviderSelector;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class Main extends CliArgs implements Callable<Integer> {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    // business logic goes here, main method wrapped by Picocli library
    @Override
    public Integer call() throws Exception {
        ExecutionContext context = ExecutionContext.Builder.instance()
                .inputLocation(this.inputLocation)
                .repositoryRef(this.repositoryReference)
                .repositoryType(this.repositoryType)
                .selectorType(this.selectorType)
                .converterType(this.converterType)
                .build();

        if(ProviderRepository.getInstance().provide(context).isError()) {
            log.atSevere().log(context.getErrorMessage());
            return 1;
        }

        if(ProviderSelector.getInstance().provide(context).isError()) {
            log.atSevere().log(context.getErrorMessage());
            return 2;
        }

        if(ProviderConverter.getInstance().provide(context).isError()) {
            log.atSevere().log(context.getErrorMessage());
            return 3;
        }

        log.atInfo().log("MAIN: >>>>> %s", context.getKnowledgebaseNotePath());
        return 0;
    }

    // Running of the code is done via Picocli library
    // main method runs cli parsing and executes the real code via Callable.call
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitCode);
    }
}
