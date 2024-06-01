package mp.exercise.filesystem;

import java.util.Objects;

public class FileSystemFile extends FileSystemResource {

    private int size = 0;

    public FileSystemFile(String name) {
        super(name);
    }

    public FileSystemFile(String name, int size) {
        super(name);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(size);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileSystemFile other = (FileSystemFile) obj;
        return size == other.size;
    }

    @Override
    public FileSystemFile createCopy() {
        return new FileSystemFile(getName(), getSize());
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visitFile(this);
    }
}
