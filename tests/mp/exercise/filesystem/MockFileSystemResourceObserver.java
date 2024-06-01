package mp.exercise.filesystem;

public class MockFileSystemResourceObserver implements FileSystemResourceObserver {

    private FileSystemResourceEvent event;

    @Override
    public void notifyChange(FileSystemResourceEvent e) {
        event = e;
    }

    public FileSystemResourceEvent getEvent() {
        return event;
    }
}
