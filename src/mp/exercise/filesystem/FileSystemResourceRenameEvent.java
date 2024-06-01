package mp.exercise.filesystem;

public class FileSystemResourceRenameEvent extends FileSystemResourceEvent {

    private String oldName;

    public FileSystemResourceRenameEvent(FileSystemResource resource, String oldName) {
        super(resource);
        this.oldName = oldName;
    }

    public String getOldName() {
        return oldName;
    }

    @Override
    public void accept(FileSystemResourceEventVisitor visitor) {
        visitor.visitRename(this);
    }

}
