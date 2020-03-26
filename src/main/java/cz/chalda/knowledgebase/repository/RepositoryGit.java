package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class RepositoryGit implements Repository {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Override
    public Path obtain(ExecutionConfiguration conf) {
        String directoryNamePrefix = conf.getInputLocation().replaceAll("[^\\w\\.]", "-");
        String repositoryName = cleanRepoName(conf.getInputLocation());
        Path outputDirectory = null;
        try {
            outputDirectory = Files.createTempDirectory(directoryNamePrefix + "__");
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(repositoryName)
                    .setDirectory(outputDirectory.toFile());
            if(conf.getRepositoryRef() != null && !conf.getRepositoryRef().isEmpty()) {
                cloneCommand.setBranch(conf.getRepositoryRef());
            }
            cloneCommand.call();
        } catch (IOException ioe) {
            log.atSevere().withCause(ioe).log("Cannot create temporary directory for repository identification %s", repositoryName);
            return null;
        } catch (GitAPIException ge) {
            log.atSevere().withCause(ge).log("Cannot clone Git repository '%s #%s' to directory '%s'",
                    repositoryName, conf.getRepositoryRef(), outputDirectory);
            return null;
        }
        return outputDirectory;
    }

    @Override
    public boolean mayHandle(ExecutionConfiguration conf) {
        if (conf.getInputLocation() == null) {
            log.atFinest().log("repository provided by configuration object is null");
            return false;
        }
        var isGitIdentification = conf.getInputLocation().contains("github.com");
        log.atFinest().log("repositoryIdentification was decided %s git type", isGitIdentification ? "to be" : "not to be");
        return isGitIdentification;
    }

    private String cleanRepoName(String repository) {
        // no protocol seems to be added to the repository name
        if(repository.startsWith("github.com")) {
            return "https://" + repository;
        }
        return repository;
    }
}
