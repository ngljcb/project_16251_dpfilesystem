package mp.exercise.filesystem;

public class FileSystemResourceAddedEvent extends FileSystemDirectoryResourceEvent {

    public FileSystemResourceAddedEvent(FileSystemResource resource,
                FileSystemDirectory directory) {
        super(resource, directory);
    }

    @Override
    public void accept(FileSystemResourceEventVisitor visitor) {
        visitor.visitAdded(this);
    }
}
