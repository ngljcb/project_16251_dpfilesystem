package mp.exercise.filesystem;

public class FileSystemResourceRemovedEvent extends FileSystemDirectoryResourceEvent {

    public FileSystemResourceRemovedEvent(FileSystemResource resource,
                FileSystemDirectory directory) {
        super(resource, directory);
    }

    @Override
    public void accept(FileSystemResourceEventVisitor visitor) {
        visitor.visitRemoved(this);
    }

}
