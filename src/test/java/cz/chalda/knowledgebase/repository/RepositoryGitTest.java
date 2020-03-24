package cz.chalda.knowledgebase.repository;

import com.google.common.flogger.FluentLogger;
import cz.chalda.knowledgebase.ExecutionConfigurationProvider;
import cz.chalda.knowledgebase.Utils;
import cz.chalda.knowledgebase.execution.ExecutionConfiguration;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class RepositoryGitTest {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private static Path tempRepoPath, test1, test2;
    private Path repoPath;

    @BeforeAll
    public static void setup() throws IOException, GitAPIException {
        tempRepoPath = Files.createTempDirectory("repository-git-test-");
        log.atFine().log("Creating testing git repository at %s", tempRepoPath);
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

    @AfterAll
    public static void cleanUp() throws IOException {
        Utils.rm(tempRepoPath);
    }

    @AfterEach
    public void cleanUpTest() throws IOException {
        Utils.rm(repoPath);
        repoPath = null;
    }

    @Test
    public void cloneBasic() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(getRepoPathName(), null, RepositoryType.GIT);
        RepositoryGit repositoryGit = new RepositoryGit();
        repoPath = repositoryGit.obtain(conf);

        Assertions.assertNotNull(repoPath,"Expected the git repo will clone to the repository but it did not happen");
        Assertions.assertTrue(Files.isDirectory(repoPath), "Expected the cloned repository is a directory");
        Assertions.assertTrue(Files.isRegularFile(repoPath.resolve("test1")), "Expected file " + repoPath.resolve("test1") + " exists");
        Assertions.assertFalse(Files.isRegularFile(repoPath.resolve("test2")), "Expected file " + repoPath.resolve("test2") + " does not exist");
    }

    @Test
    public void cloneWithBranch() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(getRepoPathName(), "test2", RepositoryType.GIT);
        RepositoryGit repositoryGit = new RepositoryGit();
        repoPath = repositoryGit.obtain(conf);

        Assertions.assertNotNull(repoPath,"Expected the git repo will clone to the repository but it did not happen");
        Assertions.assertTrue(Files.isDirectory(repoPath), "Expected the cloned repository is a directory");
        Assertions.assertTrue(Files.isRegularFile(repoPath.resolve("test2")), "Expected file " + repoPath.resolve("test2") + " exists");
        Assertions.assertFalse(Files.isRegularFile(repoPath.resolve("test1")), "Expected file " + repoPath.resolve("test1") + " does not exist");
    }

    private String getRepoPathName() {
        return "file://" + tempRepoPath.toAbsolutePath().toString();
    }

    @Test
    public void matchGitType() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository(null, null, RepositoryType.GIT);
        RepositoryGit repositoryGit = new RepositoryGit();
        Assertions.assertTrue(repositoryGit.mayHandle(conf), "Repository defined as null but is type of GIT, it should be handled");
    }

    @Test
    public void matchGithubReponame() {
        ExecutionConfiguration conf = ExecutionConfigurationProvider.repository("github.com/ochaloup/test", null, null);
        RepositoryGit repositoryGit = new RepositoryGit();
        Assertions.assertTrue(repositoryGit.mayHandle(conf), "Repository has no type but name contains 'github.com', it should be handled");
    }
}
