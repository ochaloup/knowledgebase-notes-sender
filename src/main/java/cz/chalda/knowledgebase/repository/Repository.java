package cz.chalda.knowledgebase.repository;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Path;

public interface Repository {
    /**
     * Get path to the repository which may be used for processing.
     *
     * @param taskExecutionConfiguration configuration for repository being obtained
     * @return path to repository; or empty if there is no path to repo for some reason
     */
    Path obtain(ExecutionConfiguration taskExecutionConfiguration);

    /**
     * <p>
     * Heuristically checks the repository identification and based on it
     * it tries to decide if it's capable to handle the repository.
     * </p>
     * <p>
     * This method does not provide any processing. It only checks the repository configuration
     * data from the {@link ExecutionConfiguration} objec and tries to decide whether the {@link #obtain(ExecutionConfiguration)}
     * method may succeed.
     * </p>
     *
     * @param taskExecutionConfiguration configuration data which are checked to decide if
     *                          the repository is capable to handle
     * @return true if it seems the implementation is capable to handle repository;
     *         false otherwise
     */
    boolean mayHandle(ExecutionConfiguration taskExecutionConfiguration);
}
