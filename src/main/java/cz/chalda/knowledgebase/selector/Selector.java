package cz.chalda.knowledgebase.selector;

import cz.chalda.knowledgebase.execution.ExecutionConfiguration;

import java.nio.file.Path;

public interface Selector {
    /**
     * Takes a path to a folder where files with notes resides and choose one of them.
     *
     * @param repositoryFolder  a folder with notes
     * @return chosen file with knowledge notes
     */
    Path select(Path repositoryFolder);
}
