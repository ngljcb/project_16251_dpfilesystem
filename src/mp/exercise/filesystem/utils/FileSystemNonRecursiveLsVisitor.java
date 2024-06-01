package mp.exercise.filesystem.utils;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemVisitorAdapter;

public class FileSystemNonRecursiveLsVisitor extends FileSystemVisitorAdapter {

    private FileSystemPrinter printer;
    private boolean alreadyVisitedDirectory = false;

    public FileSystemNonRecursiveLsVisitor(FileSystemPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void visitFile(FileSystemFile file) {
        printer.print("File: " + file.getName());
    }

    @Override
    public void visitDirectory(FileSystemDirectory dir) {
        printer.print("Directory: " + dir.getName());
        if (alreadyVisitedDirectory)
            return;
        alreadyVisitedDirectory = true;
        super.visitDirectory(dir);
    }

}
