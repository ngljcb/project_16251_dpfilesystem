package mp.exercise.filesystem.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;

public class ResourceLsTest {

    private MockFileSystemPrinterNoNewline printer;

    @Before
    public void init() {
        printer = new MockFileSystemPrinterNoNewline();
    }

    @Test
    public void testFileLsName() {
        ResourceLsName ls = new ResourceLsName();
        ls.ls(new FileSystemFile("aFile.txt"), printer);
        assertEquals("aFile.txt", printer.toString());
    }

    @Test
    public void testDirectoryLsName() {
        ResourceLsName ls = new ResourceLsName();
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.add(new FileSystemFile("should not be printed"));
        ls.ls(dir, printer);
        assertEquals("aDir", printer.toString());
    }

    @Test
    public void testFileLsTypeAndName() {
        ResourceLs ls = new ResourceLsType(new ResourceLsName());
        ls.ls(new FileSystemFile("aFile.txt"), printer);
        assertEquals("File: aFile.txt", printer.toString());
    }

    @Test
    public void testDirectoryLsTypeAndName() {
        ResourceLs ls = new ResourceLsType(new ResourceLsName());
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.add(new FileSystemFile("should not be printed"));
        ls.ls(dir, printer);
        assertEquals("Directory: aDir", printer.toString());
    }

    @Test
    public void testFileLsTypeAndNameAndSize() {
        ResourceLs ls =
            new ResourceLsSize(new ResourceLsType(new ResourceLsName()));
        ls.ls(new FileSystemFile("aFile.txt", 10), printer);
        assertEquals("File: aFile.txt - 10", printer.toString());
    }

    @Test
    public void testDirectoryLsTypeAndNameAndSize() {
        ResourceLs ls =
            new ResourceLsType(new ResourceLsSize(new ResourceLsName()));
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.add(new FileSystemFile("should not be printed"));
        dir.add(new FileSystemFile("should not be printed"));
        ls.ls(dir, printer);
        assertEquals("Directory: aDir - 2", printer.toString());
    }

}
