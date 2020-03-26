package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.ExecutionConfigurationProvider;
import cz.chalda.knowledgebase.Utils;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RepositoryFileSystemTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private static Path tempFolder, test1;

    @BeforeAll
    public static void setup() throws IOException {
        tempFolder = Files.createTempDirectory("repository-file-test-");
        log.atFine().log("Creating testing folder at %s", tempFolder);
        test1 = tempFolder.resolve("test1");
        Files.createFile(test1);
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        Utils.rm(tempFolder);
    }

    @Test
    public void usedProvidedFolder() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(tempFolder.toString(), null, RepositoryType.FILESYSTEM);
        RepositoryFileSystem repositoryFilesystem = new RepositoryFileSystem();
        Path testFilePath = repositoryFilesystem.obtain(conf);

        Assertions.assertEquals(tempFolder, testFilePath,"Expected the testing folder is one returned");
        Assertions.assertTrue(Files.isRegularFile(testFilePath.resolve("test1")), "Expected file " + testFilePath.resolve("test1") + " exists");
    }

    @Test
    public void matchFileType() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(null, null, RepositoryType.FILESYSTEM);
        RepositoryFileSystem repositoryFilesystem = new RepositoryFileSystem();
        Assertions.assertFalse(repositoryFilesystem.mayHandle(conf), "Repository defined as null, it can't be handled");
    }

    @Test
    public void matchFileExistence() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(tempFolder.toString(), null, null);
        RepositoryFileSystem repositoryFilesystem = new RepositoryFileSystem();
        Assertions.assertTrue(repositoryFilesystem.mayHandle(conf), "Repository defined with existing folder, it should be handled");
    }
}
