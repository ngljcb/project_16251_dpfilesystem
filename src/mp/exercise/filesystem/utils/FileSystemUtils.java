package mp.exercise.filesystem.utils;

import java.util.Optional;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;
import mp.exercise.filesystem.FileSystemVisitor;
import mp.exercise.filesystem.FileSystemVisitorAdapter;

public class FileSystemUtils {

    private FileSystemUtils() {
        // Utility classes, which are collections of static members, are not meant to be
        // instantiated. They should not have public constructors.
    }

    private static class FileFilter implements FileSystemVisitor {
        FileSystemFile file = null;

        @Override
        public void visitFile(FileSystemFile file) {
            this.file = file;
        }

        @Override
        public void visitDirectory(FileSystemDirectory dir) {
            // skip directories, only files
        }
    }

    public static Optional<FileSystemFile> toFile(FileSystemResource res) {
        final FileFilter filter = new FileFilter();
        res.accept(filter);
        return Optional.ofNullable(filter.file);
    }

    private static class DirectoryFilter extends FileSystemVisitorAdapter {
        FileSystemDirectory dir = null;

        @Override
        public void visitDirectory(FileSystemDirectory dir) {
            this.dir = dir;
        }
    }

    public static Optional<FileSystemDirectory> toDir(FileSystemResource res) {
        final DirectoryFilter filter = new DirectoryFilter();
        res.accept(filter);
        return Optional.ofNullable(filter.dir);
    }
}
