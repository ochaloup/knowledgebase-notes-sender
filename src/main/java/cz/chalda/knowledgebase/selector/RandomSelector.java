package cz.chalda.knowledgebase.selector;

import com.google.common.flogger.FluentLogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

class RandomSelector implements Selector {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    @Override
    public Path select(final Path repositoryFolder) {
        if (repositoryFolder == null) throw new  NullPointerException("repositoryFolder");
        if (!Files.isDirectory(repositoryFolder)) {
            throw new IllegalStateException("The provided path is not an existing folder at " + repositoryFolder);
        }

        Supplier<Stream<Path>> fileWalkStream = () -> {
            try {
                return Files.walk(repositoryFolder)
                        .filter(path -> Files.isRegularFile(path))
                        .filter(path -> path.getFileName().toString().endsWith(".adoc"));
            } catch (Exception e) {
                return Stream.empty();
            }
        };

        long fileNumber = fileWalkStream.get().count();
        long randomSkipNumber = new Random().nextLong();
        if (randomSkipNumber < 0) randomSkipNumber *= -1;
        randomSkipNumber = fileNumber <= 0 ? 0 : randomSkipNumber % fileNumber;
        return fileWalkStream.get()
                .skip(randomSkipNumber)
                .findAny()
                .orElse(null);
    }
}
