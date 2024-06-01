package mp.exercise.filesystem.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;

public class DirectoryFilesCollectionSupplier implements Supplier<Collection<FileSystemFile>> {

    private FileSystemDirectory dir;

    public DirectoryFilesCollectionSupplier(FileSystemDirectory dir) {
        this.dir = dir;
    }

    @Override
    public Collection<FileSystemFile> get() {
        Iterator<FileSystemResource> iterator = dir.iterator();
        ArrayList<FileSystemFile> files = new ArrayList<>();
        while (iterator.hasNext()) {
            FileSystemUtils.toFile(iterator.next())
                .ifPresent(files::add);
        }
        return files;
    }

}
