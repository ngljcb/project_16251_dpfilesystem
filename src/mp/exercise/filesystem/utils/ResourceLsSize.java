package mp.exercise.filesystem.utils;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;
import mp.exercise.filesystem.FileSystemVisitor;

public class ResourceLsSize extends ResourceLsDecorator {

    public ResourceLsSize(ResourceLs component) {
        super(component);
    }

    @Override
    public void ls(FileSystemResource resource, FileSystemPrinter printer) {
        super.ls(resource, printer);
        resource.accept(new FileSystemVisitor() {
            @Override
            public void visitFile(FileSystemFile file) {
                printer.print(" - " + file.getSize());
            }

            @Override
            public void visitDirectory(FileSystemDirectory dir) {
                printer.print(" - " + dir.getContentsSize());
            }
        });
    }
}
