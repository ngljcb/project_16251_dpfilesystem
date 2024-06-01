package mp.exercise.filesystem.utils;

import mp.exercise.filesystem.FileSystemResource;

public class ResourceLsName implements ResourceLs {

    @Override
    public void ls(FileSystemResource resource, FileSystemPrinter printer) {
        printer.print(resource.getName());
    }

}
