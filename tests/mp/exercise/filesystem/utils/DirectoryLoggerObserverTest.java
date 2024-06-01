package mp.exercise.filesystem.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;

public class DirectoryLoggerObserverTest {

    private MockFileSystemPrinter printer;
    private FileSystemDirectory directory;
    private FileSystemFile existing;

    @Before
    public void init() {
        printer = new MockFileSystemPrinter();
        directory = new FileSystemDirectory("aDir");
        existing = new FileSystemFile("existingFile");
        directory.add(existing);
        new DirectoryLoggerObserver(directory, printer);
    }

    @Test
    public void testDirectoryRenamed() {
        directory.rename("newName");
        assertEquals("Rename: aDir -> newName\n",
            printer.toString());
    }

    @Test
    public void testFileAdded() {
        directory.add(new FileSystemFile("aFile"));
        assertEquals("Added: aFile in aDir\n",
            printer.toString());
    }

    @Test
    public void testFileRemoved() {
        directory.remove(existing);
        assertEquals("Removed: existingFile from aDir\n",
            printer.toString());
    }

    @Test
    public void testRenameUnknownFile() {
        existing.rename("newName");
        assertEquals("", printer.toString());
    }

    @Test
    public void testRenameKnownFile() {
        FileSystemFile knownFile = new FileSystemFile("anotherFile");
        directory.add(knownFile);
        knownFile.rename("newName");
        assertThat(printer.toString())
            .contains("Rename: anotherFile -> newName");
    }

    @Test
    public void testRenameRemovedFile() {
        FileSystemFile knownFile = new FileSystemFile("anotherFile");
        directory.add(knownFile); // now it's observed
        directory.remove(knownFile); // now it should not be observed anymore
        knownFile.rename("newName");
        assertThat(printer.toString())
            .doesNotContain("Rename: anotherFile -> newName");
    }

    @Test
    public void testRenameFileInNestedDir() {
        FileSystemDirectory nestedDir = new FileSystemDirectory("nesteDir");
        FileSystemFile fileInNestedDir = new FileSystemFile("nestedDirFile");
        nestedDir.add(fileInNestedDir);
        // observer still doesn't know about nestedDir
        // observer still doesn't know about fileNestedDir
        directory.add(nestedDir);
        // observer should now observe nestedDir
        // observer should now observe fileNestedDir
        fileInNestedDir.rename("newName");
        assertThat(printer.toString())
            .contains("Rename: nestedDirFile -> newName");
        directory.remove(nestedDir);
        // observer shouldn't observe nestedDir anymore
        // observer shouldn't about fileNestedDir anymore
        fileInNestedDir.rename("anotherNewName");
        assertThat(printer.toString())
            .doesNotContain("anotherNewName");
    }
}
