package mp.exercise.filesystem.utils;

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
import mp.exercise.filesystem.FileSystemVisitorAdapter;

public class DirectoryLoggerObserver implements FileSystemResourceObserver {

    private static final class FileSystemVisitorAdapterExtension
                  extends FileSystemVisitorAdapter {
        private final Consumer<FileSystemResource> consumer;

        private FileSystemVisitorAdapterExtension
                (Consumer<FileSystemResource> resourceConsumer) {
            consumer = resourceConsumer;
        }

        @Override
        public void visitFile(FileSystemFile file) {
            consumer.accept(file);
        }

        @Override
        public void visitDirectory(FileSystemDirectory dir) {
            consumer.accept(dir);
            super.visitDirectory(dir);
        }
    }

    private FileSystemPrinter printer;

    public DirectoryLoggerObserver(FileSystemDirectory directory, FileSystemPrinter printer) {
        this.printer = printer;
        directory.addObserver(this);
    }

    @Override
    public void notifyChange(FileSystemResourceEvent e) {
        e.accept(new FileSystemResourceEventVisitor() {
            @Override
            public void visitRename(FileSystemResourceRenameEvent e) {
                printer.print("Rename: " + e.getOldName() +
                    " -> " + e.getResource().getName());
            }

            @Override
            public void visitRemoved(FileSystemResourceRemovedEvent e) {
                FileSystemResource resource = e.getResource();
                printer.print("Removed: " + resource.getName() +
                        " from " + e.getDirectory().getName());
                resource.accept
                    (new FileSystemVisitorAdapterExtension
                        (r -> r.removeObserver(DirectoryLoggerObserver.this)));
            }

            @Override
            public void visitAdded(FileSystemResourceAddedEvent e) {
                FileSystemResource resource = e.getResource();
                printer.print("Added: " + resource.getName() +
                        " in " + e.getDirectory().getName());
                resource.accept
                    (new FileSystemVisitorAdapterExtension
                        (r -> r.addObserver(DirectoryLoggerObserver.this)));
            }
        });
    }

}
