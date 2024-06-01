package mp.exercise.filesystem.utils;

import mp.exercise.filesystem.FileSystemDirectory;
import mp.exercise.filesystem.FileSystemFile;
import mp.exercise.filesystem.FileSystemResource;
import mp.exercise.filesystem.FileSystemVisitor;

public class ResourceLsType extends ResourceLsDecorator {

    public ResourceLsType(ResourceLs component) {
        super(component);
    }

    @Override
    public void ls(FileSystemResource resource, FileSystemPrinter printer) {
        resource.accept(new FileSystemVisitor() {
            @Override
            public void visitFile(FileSystemFile file) {
                printer.print("File: ");
            }

            @Override
            public void visitDirectory(FileSystemDirectory dir) {
                printer.print("Directory: ");
            }
        });
        super.ls(resource, printer);
    }
}
