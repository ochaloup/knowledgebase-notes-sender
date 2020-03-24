package cz.chalda.knowledgebase.repository;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class RepositoryProcessor {
    private static final Repository[] implementations =
        new Repository[] {new RepositoryFile(), new RepositoryGit()};

    /**
     * Tries to find a repository handler and returns path to the repository.
     *
     * @param conf  configuration for repository obtaining
     * @return path to repository with data
     */
    public static Path get(final ExecutionConfiguration conf) {
        Optional<Repository> repository = Arrays.stream(implementations)
                .filter(r -> r.mayHandle(conf))
                .findFirst();
        if(repository.isPresent()) {
            return repository.get().obtain(conf);
        } else {
            return null;
        }
    }
}
