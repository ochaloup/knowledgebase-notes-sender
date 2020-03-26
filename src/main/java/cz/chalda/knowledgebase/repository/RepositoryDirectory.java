package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;

class RepositoryDirectory implements Repository {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Override
    public Path obtain(ExecutionConfiguration conf) {
        var repositoryPath = Path.of(conf.getInputLocation());
        if(!Files.isDirectory(repositoryPath)) {
            log.atFinest().log("repository path %s is not an existing directory", repositoryPath);
            return null;
        }
        return repositoryPath;
    }

    @Override
    public boolean mayHandle(ExecutionConfiguration conf) {
         if (conf.getInputLocation() == null) {
            log.atFinest().log("repository provided by configuration object is null");
            return false;
        }
        Path repositoryProposal = Path.of(conf.getInputLocation());
        boolean isDirectoryIdentification = Files.isDirectory(repositoryProposal);
        log.atFinest().log("repositoryIdentification was decided %s %s type",
                isDirectoryIdentification ? "to be" : "not to be", RepositoryType.DIRECTORY.name());
        return isDirectoryIdentification;
    }
}
