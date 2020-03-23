package cz.chalda.knowledgebase;

import cz.chalda.knowledgebase.cli.CliArgs;
import picocli.CommandLine;

import java.util.concurrent.Callable;


public class Main extends CliArgs implements Callable<Integer> {

    // business logic goes here, wrapped by Picocli library
    @Override
    public Integer call() throws Exception {
        System.out.println(gitRepository);
        return 0;
    }

    // Running of the code is done via Picocli library which wraps the CLI processing and execute the business logic
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
