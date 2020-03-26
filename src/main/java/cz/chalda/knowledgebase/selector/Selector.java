package cz.chalda.knowledgebase.selector;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Path;

public interface Selector {
    /**
     * Takes a path to a repository location where files with notes resides and choose one of them.
     *
     * @param repositoryLocation  a repository location with notes
     * @return chosen file with knowledge notes
     */
    Path select(Path repositoryLocation);


    /**
     * Declcares if the selector type is capable to handle the repository location.
     *
     * @param repositoryLocation a repository location to verify if the selector is capable to handle
     * @return true if selector is capable to manage the location; false otherwise
     */
    boolean mayHandle(Path repositoryLocation);
}
