package mp.exercise.filesystem.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;
import mp.exercise.filesystem.FileSystemResourceAddedEvent;
import mp.exercise.filesystem.FileSystemResourceEvent;
import mp.exercise.filesystem.FileSystemResourceEventVisitor;
import mp.exercise.filesystem.FileSystemResourceObserver;
import mp.exercise.filesystem.FileSystemResourceRemovedEvent;
import mp.exercise.filesystem.FileSystemResourceRenameEvent;
import mp.exercise.filesystem.FileSystemVisitor;

public class DirectoryFilesCollectorObserver implements FileSystemResourceObserver {

    private static final class FileSystemVisitorAdapterExtension implements FileSystemVisitor {
        private final Consumer<FileSystemResource> consumer;

        private FileSystemVisitorAdapterExtension(Consumer<FileSystemResource> resourceConsumer) {
            consumer = resourceConsumer;
        }

        @Override
        public void visitFile(FileSystemFile file) {
            consumer.accept(file);
        }

        @Override
        public void visitDirectory(FileSystemDirectory dir) {
            // skip directories
        }
    }

    private Set<String> names = new HashSet<>();

    public DirectoryFilesCollectorObserver(FileSystemDirectory directory) {
        directory.addObserver(this);
    }

    public Iterator<String> iterator() {
        return names.iterator();
    }

    @Override
    public void notifyChange(FileSystemResourceEvent e) {
        e.accept(new FileSystemResourceEventVisitor() {
            @Override
            public void visitRename(FileSystemResourceRenameEvent e) {
                e.getResource()
                    .accept(
                    new FileSystemVisitorAdapterExtension
                        (r -> {
                            names.remove(e.getOldName());
                            names.add(e.getResource().getName());
                        }));
            }

            @Override
            public void visitRemoved(FileSystemResourceRemovedEvent e) {
                e.getResource()
                    .accept(
                    new FileSystemVisitorAdapterExtension
                        (r -> {
                            r.removeObserver(DirectoryFilesCollectorObserver.this);
                            names.remove(r.getName());
                        }));
            }

            @Override
            public void visitAdded(FileSystemResourceAddedEvent e) {
                e.getResource()
                    .accept(
                     new FileSystemVisitorAdapterExtension
                        (r -> {
                            r.addObserver(DirectoryFilesCollectorObserver.this);
                            names.add(r.getName());
                        }));
            }
        });
    }

}
