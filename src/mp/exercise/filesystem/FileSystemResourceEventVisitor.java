package mp.exercise.filesystem;

public interface FileSystemResourceEventVisitor {

    void visitRename(FileSystemResourceRenameEvent e);

    void visitAdded(FileSystemResourceAddedEvent e);

    void visitRemoved(FileSystemResourceRemovedEvent e);
}
