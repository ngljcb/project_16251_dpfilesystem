package mp.exercise.filesystem.utils;

import mp.exercise.filesystem.FileSystemResource;

public abstract class ResourceLsDecorator implements ResourceLs {

    private ResourceLs component;

    public ResourceLsDecorator(ResourceLs component) {
        this.component = component;
    }

    @Override
    public void ls(FileSystemResource resource, FileSystemPrinter printer) {
        component.ls(resource, printer);
    }

}
