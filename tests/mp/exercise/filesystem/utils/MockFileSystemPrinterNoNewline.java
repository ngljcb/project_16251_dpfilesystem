package mp.exercise.filesystem.utils;

public class MockFileSystemPrinterNoNewline implements FileSystemPrinter {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void print(String message) {
        builder.append(message);
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
