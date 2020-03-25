package cz.chalda.knowledgebase.repository;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public final class RepositoryProvider implements ExecutionProvider {
    private static final RepositoryProvider INSTANCE = new RepositoryProvider();
    // package private constructor
    RepositoryProvider() {}

    public static RepositoryProvider getInstance() {
        return RepositoryProvider.INSTANCE;
    }

    private static final Repository[] implementations =
        new Repository[] {new RepositoryFile(), new RepositoryGit()};

    /**
     * Tries to provide a repository which is a path where knowledgbase notes may reside.
     * TODO:...
     */
    public ExecutionContext provide(final ExecutionContext context) {
        var conf = context.getConfiguration();
        Path pathToNotes = getPath(conf);
        return context.setRepositoryPath(pathToNotes);
    }

    Path getPath(final ExecutionConfiguration conf) {
        return Arrays.stream(implementations)
                .filter(r -> r.mayHandle(conf))
                .findFirst()
                .map(r -> r.obtain(conf))
                .orElse(null);
    }
}
