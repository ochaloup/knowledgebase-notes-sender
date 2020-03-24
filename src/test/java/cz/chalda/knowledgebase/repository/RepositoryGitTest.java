package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import com.jcraft.jsch.IO;
import cz.chalda.knowledgebase.ExecutionConfigurationProvider;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RepositoryGitTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private static Path tempRepoPath, test1, test2;

    @BeforeAll
    public static void setup() throws IOException, GitAPIException {
        tempRepoPath = Files.createTempDirectory("repository-git-test-");
        log.atInfo().log(">>>" + tempRepoPath);
        Git.init().setDirectory(tempRepoPath.toFile()).call();
        Git git = Git.open(tempRepoPath.toFile());

        test1 = tempRepoPath.resolve("test1");
        Files.createFile(test1);
        git.add().addFilepattern(".").call();
        git.commit().setMessage("test1 added").call();

        git.checkout().setCreateBranch(true).setName("test2").call();
        test2 = tempRepoPath.resolve("test2");
        Files.delete(test1);
        git.rm().addFilepattern("test1").call();
        Files.createFile(test2);
        git.add().addFilepattern(".").call();
        git.commit().setMessage("test2 added").call();

        git.checkout().setName("master").call();
    }

    @Test
    public void test() {
        String repoPathName = "file://" + tempRepoPath.toAbsolutePath().toString();
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(repoPathName, null, RepositoryType.GIT);
        RepositoryGit repositoryGit = new RepositoryGit();
        Path repoPath = repositoryGit.obtain(conf);
        log.atInfo().log("cloned git path: " + repoPath);
        Assertions.assertNotNull(repoPath,"Expected the git repo will clone the repository but nothing happpens");
        Assertions.assertTrue(Files.isDirectory(repoPath), "Expected the cloned repository is a directory");
    }
}
