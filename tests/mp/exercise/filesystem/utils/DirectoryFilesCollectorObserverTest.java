package mp.exercise.filesystem.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;

public class DirectoryFilesCollectorObserverTest {

    private FileSystemDirectory directory;
    private FileSystemFile existing;
    private DirectoryFilesCollectorObserver observer;

    @Before
    public void init() {
        directory = new FileSystemDirectory("aDir");
        existing = new FileSystemFile("existingFile");
        directory.add(existing);
        observer = new DirectoryFilesCollectorObserver(directory);
    }

    @Test
    public void testDirectoryRenamed() {
        directory.rename("newName");
        assertThat(observer.iterator())
            .toIterable().isEmpty();
    }

    @Test
    public void testFilesAdded() {
        directory.add(new FileSystemFile("aFile"));
        directory.add(new FileSystemFile("anotherFile"));
        assertThat(observer.iterator())
            .toIterable()
            .containsExactlyInAnyOrder("aFile", "anotherFile");
    }

    @Test
    public void testDirectoryAdded() {
        FileSystemFile nestedFile = new FileSystemFile("aFile");
        FileSystemDirectory nestedDir = new FileSystemDirectory("aDir");
        directory.add(nestedDir);
        nestedDir.add(nestedFile);
        assertThat(observer.iterator())
            .toIterable()
            .isEmpty();
    }

    @Test
    public void testFileRemoved() {
        directory.remove(existing);
        assertThat(observer.iterator())
            .toIterable().isEmpty();
    }

    @Test
    public void testRenameUnknownFile() {
        existing.rename("newName");
        assertThat(observer.iterator())
            .toIterable().isEmpty();
    }

    @Test
    public void testRenameKnownFile() {
        FileSystemFile knownFile = new FileSystemFile("anotherFile");
        directory.add(knownFile);
        knownFile.rename("newName");
        assertThat(observer.iterator())
            .toIterable()
            .containsExactly("newName");
    }

    @Test
    public void testRenameRemovedFile() {
        FileSystemFile knownFile = new FileSystemFile("anotherFile");
        directory.add(knownFile); // now it's observed
        directory.remove(knownFile); // now it should not be observed anymore
        knownFile.rename("newName");
        assertThat(observer.iterator())
            .toIterable().isEmpty();
    }

}
