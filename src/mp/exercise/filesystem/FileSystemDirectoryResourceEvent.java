package mp.exercise.filesystem;

public abstract class FileSystemDirectoryResourceEvent extends FileSystemResourceEvent {

    private FileSystemDirectory directory;

    public FileSystemDirectoryResourceEvent(FileSystemResource resource,
                FileSystemDirectory directory) {
        super(resource);
        this.directory = directory;
    }

    public FileSystemDirectory getDirectory() {
        return directory;
    }

}