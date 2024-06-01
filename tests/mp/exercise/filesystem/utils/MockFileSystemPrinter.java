package mp.exercise.filesystem.utils;

public class MockFileSystemPrinter implements FileSystemPrinter {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void print(String message) {
        builder.append(message + "\n");
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
