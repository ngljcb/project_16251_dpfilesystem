package mp.exercise.filesystem;

public interface FileSystemVisitor {

    void visitFile(FileSystemFile file);

    void visitDirectory(FileSystemDirectory dir);

}
