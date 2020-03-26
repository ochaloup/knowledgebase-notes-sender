package cz.chalda.knowledgebase.selector;

import java.nio.file.Files;
import java.nio.file.Path;

public class SingleFileSelector implements Selector {
    @Override
    public Path select(Path repositoryLocation) {
        if (repositoryLocation == null) throw new  NullPointerException("repositoryLocation");
        if (!Files.isRegularFile(repositoryLocation)) {
            throw new IllegalStateException("The provided path is not an existing file at " + repositoryLocation);
        }
        return repositoryLocation;
    }

    @Override
    public boolean mayHandle(Path repositoryLocation) {
        if(repositoryLocation == null) return false;
        return Files.isRegularFile(repositoryLocation);
    }
}
