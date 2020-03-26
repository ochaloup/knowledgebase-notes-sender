package cz.chalda.knowledgebase.repository;

public enum RepositoryType {
    DIRECTORY(new RepositoryDirectory()),
    GIT(new RepositoryGit());

    private Repository repository;

    private RepositoryType(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }
}
