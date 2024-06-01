package mp.exercise.filesystem.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;

public class FileSystemUtilsTest {

    @Test
    public void testToFile() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemFile file = new FileSystemFile("aFile1.txt");
        dir.add(file);

        assertThat(FileSystemUtils.toFile(dir))
            .isEmpty();
        assertThat(FileSystemUtils.toFile(file))
            .isPresent()
            .containsSame(file);
    }

    @Test
    public void testToDir() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemFile file = new FileSystemFile("aFile1.txt");
        dir.add(file);

        assertThat(FileSystemUtils.toDir(file))
            .isEmpty();
        assertThat(FileSystemUtils.toDir(dir))
            .isPresent()
            .containsSame(dir);
    }

}
