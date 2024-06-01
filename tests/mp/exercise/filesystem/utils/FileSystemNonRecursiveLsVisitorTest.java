package mp.exercise.filesystem.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;

public class FileSystemNonRecursiveLsVisitorTest {

    private MockFileSystemPrinter printer;
    private FileSystemNonRecursiveLsVisitor visitor;

    @Before
    public void setup() {
        printer = new MockFileSystemPrinter();
        visitor = new FileSystemNonRecursiveLsVisitor(printer);
    }

    @Test
    public void testFileLs() {
        FileSystemResource file = new FileSystemFile("aFile.txt");
        file.accept(visitor);
        assertEquals("File: aFile.txt\n", printer.toString());
    }

    @Test
    public void testEmptyDirectoryLs() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.accept(visitor);
        assertEquals("Directory: aDir\n", printer.toString());
    }

    @Test
    public void testNonEmptyDirectoryLs() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        dir.add(new FileSystemFile("aFile.txt"));
        dir.accept(visitor);
        assertEquals(
                "Directory: aDir\n"+
                "File: aFile.txt\n",
            printer.toString());
    }

    @Test
    public void testRecursiveDirectoryLs() {
        FileSystemDirectory dir = new FileSystemDirectory("aDir");
        FileSystemDirectory nestedDir = new FileSystemDirectory("aNestedDir");
        dir.add(nestedDir);
        nestedDir.add(new FileSystemFile("aFile.txt"));
        dir.accept(visitor);
        assertEquals(
                "Directory: aDir\n"+
                "Directory: aNestedDir\n",
            printer.toString());
    }

}
