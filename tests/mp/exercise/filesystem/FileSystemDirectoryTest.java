package mp.exercise.filesystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class FileSystemDirectoryTest {

    @Test
    public void testDirectoryAdd() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");

        FileSystemResource res = new FileSystemFile("a file");
        dir.add(res);

        Collection<FileSystemResource> contents = dir.getContents();
        assertEquals(1, contents.size());
        assertTrue(contents.contains(res));
    }

    @Test
    public void testDirectoryRemove() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");

        FileSystemResource notToRemove = new FileSystemFile("file1");
        FileSystemResource toRemove = new FileSystemFile("file2");

        Collection<FileSystemResource> contents = dir.getContents();
        contents.addAll(Arrays.asList(notToRemove, toRemove));

        dir.remove(toRemove);
        assertEquals(1, contents.size());
        assertTrue(contents.contains(notToRemove));
    }

    @Test
    public void testDirectoryFindByName() {
        FileSystemDirectory dir = new FileSystemDirectory("a dir");

        Collection<FileSystemResource> contents = dir.getContents();
        FileSystemResource res1 = new FileSystemFile("file1");
        FileSystemResource res2 = new FileSystemFile("file2");
        contents.addAll(Arrays.asList(res1, res2));

        assertEquals(res2, dir.findByName("file2").get());
        assertFalse(dir.findByName("file3").isPresent());
    }

    @Test
    public void testToString() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemDirectory nestedDir = new FileSystemDirectory("aNestedDir");
        dir.getContents().add(nestedDir);
        nestedDir.getContents().add(new FileSystemFile("aFile.txt"));
        assertEquals(
            "FileSystemDirectory [name=aDir] "
                + "[contents=[FileSystemDirectory "
                    + "[name=aNestedDir] "
                    + "[contents=[FileSystemFile [name=aFile.txt]]]]]",
                dir.toString());
    }

    @Test
    public void testEquals() {
        FileSystemDirectory dir1 = new FileSystemDirectory("aDir");
        FileSystemDirectory dir2 = new FileSystemDirectory("aDir");
        dir1.getContents().add(new FileSystemFile("aFile.txt"));
        dir2.getContents().add(new FileSystemFile("aFile.txt"));
        assertEquals(dir1, dir2);
        dir2.getContents().add(new FileSystemFile("anotherFile.txt"));
        assertNotEquals(dir1, dir2);
    }

    @Test
    public void testHashCode() {
        FileSystemDirectory dir1 = new FileSystemDirectory("aDir");
        FileSystemDirectory dir2 = new FileSystemDirectory("aDir");
        dir1.getContents().add(new FileSystemFile("aFile.txt"));
        dir2.getContents().add(new FileSystemFile("aFile.txt"));
        assertEquals(dir1.hashCode(), dir2.hashCode());
        dir2.getContents().add(new FileSystemFile("anotherFile.txt"));
        assertNotEquals(dir1.hashCode(), dir2.hashCode());
    }

    @Test
    public void testDirectoryCopy() {
        FileSystemDirectory original = new FileSystemDirectory("a dir");
        FileSystemDirectory nested = new FileSystemDirectory("a nested dir");
        nested.getContents().add(new FileSystemFile("second file"));
        original.getContents()
            .addAll(Arrays.asList(new FileSystemFile("first file"), nested));

        FileSystemDirectory copy = original.createCopy();

        assertEquals(original, copy);
        assertEffectiveCopyDirectory(original, copy);
    }

    private void assertEffectiveCopyDirectory(FileSystemDirectory dir1, FileSystemDirectory dir2) {
        assertNotSame(dir1, dir2);
        Iterator<FileSystemResource> contents1 = dir1.getContents().iterator();
        Iterator<FileSystemResource> contents2 = dir2.getContents().iterator();
        // si assume che le due collezioni abbiano la stessa lunghezza,
        // e che a un elemento della prima corrisponda un elemento della
        // seconda dello stesso tipo.
        // Questo e' vero se e' gia' stato usato assertEquals
        while (contents1.hasNext()) {
            FileSystemResource res1 = contents1.next();
            FileSystemResource res2 = contents2.next();
            if (res1 instanceof FileSystemDirectory) {
                assertEffectiveCopyDirectory
                    ((FileSystemDirectory) res1, (FileSystemDirectory) res2);
            } else {
                assertNotSame(res1, res2);
            }
        }
    }

    @Test
    public void testRecursiveDirectoryIterator() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemDirectory nestedDir = new FileSystemDirectory("aNestedDir");
        FileSystemFile file1 = new FileSystemFile("aFile.txt");
        dir.getContents().add(file1);
        dir.getContents().add(nestedDir);
        nestedDir.getContents().add(new FileSystemFile("aNotherFile.txt"));
        assertThat(dir.iterator())
            .toIterable()
            .containsExactlyInAnyOrder(file1, nestedDir);
    }

    @Test
    public void testRename() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.rename("newName");
        assertEquals("newName", dir.getName());
    }

    @Test
    public void testRenameEvent() {
        // setup
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        MockFileSystemResourceObserver observer = new MockFileSystemResourceObserver();
        dir.addObserver(observer);
        // exercise
        dir.rename("newName");
        // verify
        FileSystemResourceRenameEvent event =
            (FileSystemResourceRenameEvent) observer.getEvent();
        assertThat(event.getResource()).isSameAs(dir);
        assertThat(event.getOldName()).isEqualTo("aDir");
    }

    @Test
    public void testAddedEvent() {
        // setup
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        MockFileSystemResourceObserver observer =
            new MockFileSystemResourceObserver();
        dir.addObserver(observer);
        // exercise
        FileSystemFile file = new FileSystemFile("aFile");
        dir.add(file);
        // verify
        FileSystemResourceAddedEvent event =
            (FileSystemResourceAddedEvent) observer.getEvent();
        assertThat(event.getResource()).isSameAs(file);
        assertThat(event.getDirectory()).isSameAs(dir);
    }

    @Test
    public void testRemovedEvent() {
        // setup
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemFile file = new FileSystemFile("aFile");
        dir.getContents().add(file);
        MockFileSystemResourceObserver observer =
            new MockFileSystemResourceObserver();
        dir.addObserver(observer);
        // exercise
        dir.remove(file);
        // verify
        FileSystemResourceRemovedEvent event =
            (FileSystemResourceRemovedEvent) observer.getEvent();
        assertThat(event.getResource()).isSameAs(file);
        assertThat(event.getDirectory()).isSameAs(dir);
    }
}
