package cz.chalda.knowledgebase.selector;

import com.google.common.flogger.FluentLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Random;

class RandomSelector implements Selector {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Override
    public Path select(Path repositoryFolder) {
        if (repositoryFolder == null) throw new  NullPointerException("repositoryFolder");
        if (!Files.isDirectory(repositoryFolder)) {
            throw new IllegalStateException("The provided path is not an existing folder at " + repositoryFolder);
        }
        log.atInfo().log("HEEEEY %s", repositoryFolder);
        final Random r = new Random();
        try {
            return Files.walk(repositoryFolder)
                    .filter(path -> Files.isRegularFile(path))
                    .peek(path -> log.atInfo().log("working with: %s", path.getFileName()))
                    .filter(path -> path.getFileName().endsWith(".adoc"))
                    .sorted((f1, f2) -> (r.nextInt(1)) == 0 ? -1 : 1)
                    .findAny()
                    .orElse(null);
        } catch (IOException ioe) {
            log.atSevere().withCause(ioe).log("Error on walking through the folder %s", repositoryFolder, ioe);
            return null;
        }
    }
}
