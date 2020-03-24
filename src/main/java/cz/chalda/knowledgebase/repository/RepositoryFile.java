package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;

class RepositoryFile implements Repository {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Override
    public Path obtain(ExecutionConfiguration conf) {
        var repositoryPath = Path.of(conf.getRepository());
        if(!Files.isDirectory(repositoryPath)) {
            log.atFinest().log("repository path %s is not an existing directory", repositoryPath);
            return null;
        }
        return repositoryPath;
    }

    @Override
    public boolean mayHandle(ExecutionConfiguration conf) {
        if(RepositoryType.FILE == conf.getRepositoryType()) {
            log.atFinest().log("repository type defines this is identification of file");
            return true;
        }
        if (conf.getRepository() == null) {
            log.atFinest().log("repository provided by configuration object is null");
            return false;
        }
        Path repositoryProposal = Path.of(conf.getRepository());
        boolean isFileIdentification = Files.isDirectory(repositoryProposal);
        log.atFinest().log("repositoryIdentification was decided %s file type", isFileIdentification ? "to be" : "not to be");
        return isFileIdentification;
    }
}
