package mp.exercise.filesystem;

public abstract class FileSystemResourceEvent {

    private FileSystemResource resource;

    public FileSystemResourceEvent(FileSystemResource resource) {
        this.resource = resource;
    }

    public FileSystemResource getResource() {
        return resource;
    }

    public abstract void accept(FileSystemResourceEventVisitor visitor);
}
