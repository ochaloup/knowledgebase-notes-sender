package cz.chalda.knowledgebase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public final class Utils {
    private Utils() {
        throw new IllegalStateException("utility class");
    }

    public static void rm(Path toDelete) throws IOException {
        if(toDelete == null) return;
        Files.walk(toDelete)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
