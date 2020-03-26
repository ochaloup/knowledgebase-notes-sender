package cz.chalda.knowledgebase.repository;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import cz.chalda.knowledgebase.execution.ExecutionContext;
import cz.chalda.knowledgebase.execution.ExecutionProvider;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public final class ProviderRepository implements ExecutionProvider {
    private static final ProviderRepository INSTANCE = new ProviderRepository();
    // package private constructor
    ProviderRepository() {}
    public static ProviderRepository getInstance() {
        return ProviderRepository.INSTANCE;
    }

    /**
     * Tries to provide a repository which is a path where knowledgbase notes may reside.
     * TODO:...
     */
    public ExecutionContext provide(final ExecutionContext context) {
        if(context == null) {
            return context.setError("No execution context provided, the context param is null");
        }

        Optional<Repository> repository = Optional.empty();
        var conf = context.getConfiguration();
        try {
            repository = getRepository(context.getConfiguration().getRepositoryType(), conf);
            if (repository.isEmpty()) {
                return context.setError("Cannot find proper repository provider to handle input repository '%s' of reference '%s'",
                        conf.getInputLocation(), conf.getRepositoryRef());
            }
            var repositoryLocation = repository.get().obtain(conf);
            return context.setRepositoryLocation(repositoryLocation);
        } catch(Exception e) {
            return context.setError(e, "Error on processing repository '%s' to handle input location '%s' of reference '%s'",
                    repository.isEmpty() ? "null" : repository.get().toString(), conf.getInputLocation(), conf.getRepositoryRef());
        }
    }

    Optional<Repository> getRepository(final RepositoryType repositoryType, final ExecutionConfiguration conf) {
        if(repositoryType != null) {
            switch (repositoryType) {
                case DIRECTORY:
                    return Optional.of(RepositoryType.DIRECTORY.getRepository());
                case GIT:
                    return Optional.of(RepositoryType.GIT.getRepository());
            }
        }
        return Arrays.stream(RepositoryType.values())
                .filter(st -> st.getRepository().mayHandle(conf))
                .findFirst()
                .map(st -> Optional.of(st.getRepository()))
                .orElse(Optional.empty());
    }
}
