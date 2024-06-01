package mp.exercise.filesystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class FileSystemFileTest {

    @Test
    public void testToString() {
        assertEquals("FileSystemFile [name=aFile.txt]",
                new FileSystemFile("aFile.txt").toString());
    }

    @Test
    public void testEquals() {
        FileSystemResource file1 = new FileSystemFile("aFile1");
        FileSystemResource file2 = new FileSystemFile("aFile1");
        FileSystemResource file3 = new FileSystemFile("aFile3");
        assertEquals(file1, file2);
        assertNotEquals(file1, file3);
    }

    @Test
    public void testHashCode() {
        FileSystemResource file1 = new FileSystemFile("aFile1");
        FileSystemResource file2 = new FileSystemFile("aFile1");
        FileSystemResource file3 = new FileSystemFile("aFile3");
        assertEquals(file1.hashCode(), file2.hashCode());
        assertNotEquals(file1.hashCode(), file3.hashCode());
    }

    @Test
    public void testFileCreateCopy() {
        FileSystemFile original = new FileSystemFile("aFile");
        FileSystemResource copy = original.createCopy();

        assertNotSame(original, copy);
        assertEquals(original, copy);
    }

    @Test
    public void testFileCreateCopyWithSize() {
        FileSystemFile original = new FileSystemFile("aFile", 10);
        FileSystemResource copy = original.createCopy();

        assertNotSame(original, copy);
        assertEquals(original, copy);
    }

    @Test
    public void testAddAndRemoveObserver() {
        FileSystemResourceObserver observer = e -> {
            // just for testing
        };
        FileSystemFile file = new FileSystemFile("aFile");
        file.addObserver(observer);
        assertThat(file.getObservers())
            .containsExactly(observer);
        file.removeObserver(observer);
        assertThat(file.getObservers())
            .isEmpty();
    }

    @Test
    public void testRename() {
        FileSystemFile file = new FileSystemFile("aFile");
        file.rename("newName");
        assertEquals("newName", file.getName());
    }

    @Test
    public void testRenameEvent() {
        // setup
        FileSystemFile file = new FileSystemFile("aFile");
        MockFileSystemResourceObserver observer = new MockFileSystemResourceObserver();
        file.addObserver(observer);
        // exercise
        file.rename("newName");
        // verify
        FileSystemResourceRenameEvent event =
            (FileSystemResourceRenameEvent) observer.getEvent();
        assertThat(event.getResource()).isSameAs(file);
        assertThat(event.getOldName()).isEqualTo("aFile");
    }
}
