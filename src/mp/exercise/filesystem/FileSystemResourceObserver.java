package mp.exercise.filesystem;

public interface FileSystemResourceObserver {

    void notifyChange(FileSystemResourceEvent e);

}
