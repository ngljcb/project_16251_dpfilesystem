package mp.exercise.filesystem.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;

public class DirectoryFilesCollectionSupplierTest {

    @Test
    public void testRecursiveDirectoryLs() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemFile file1 = new FileSystemFile("aFile1.txt");
        dir.add(file1);
        FileSystemDirectory nestedDir = new FileSystemDirectory("aNestedDir");
        dir.add(nestedDir);
        nestedDir.add(new FileSystemFile("aFile.txt"));
        FileSystemFile file2 = new FileSystemFile("aFile2.txt");
        dir.add(file2);
        assertThat(new DirectoryFilesCollectionSupplier(dir).get())
            .containsExactlyInAnyOrder(file1, file2);
    }

}
