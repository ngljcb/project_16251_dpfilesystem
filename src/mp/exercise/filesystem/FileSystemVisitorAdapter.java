package mp.exercise.filesystem;

import java.util.Iterator;

public abstract class FileSystemVisitorAdapter implements FileSystemVisitor {

    /**
     * The default implementation does nothing.
     */
    @Override
    public void visitFile(FileSystemFile file) {
        // does nothing
    }

    /**
     * The default implementation visit all the directory contents recursively.
     */
    @Override
    public void visitDirectory(FileSystemDirectory dir) {
        Iterator<FileSystemResource> iterator = dir.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(this);
        }
    }

}
