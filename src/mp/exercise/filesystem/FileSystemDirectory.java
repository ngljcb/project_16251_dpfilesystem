package mp.exercise.filesystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileSystemDirectory extends FileSystemResource {

    private Collection<FileSystemResource> contents = new ArrayList<>();

    public FileSystemDirectory(String name) {
        super(name);
    }

    /**
     * Just for testing, package-private
     */
    Collection<FileSystemResource> getContents() {
        return contents;
    }

    public int getContentsSize() {
        return contents.size();
    }

    @Override
    public String toString() {
        return super.toString() + " [contents=" + contents + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(contents);
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
        FileSystemDirectory other = (FileSystemDirectory) obj;
        return Objects.equals(contents, other.contents);
    }

    public void add(FileSystemResource resource) {
        contents.add(resource);
        notifyObservers(
            new FileSystemResourceAddedEvent(resource, this)
        );
    }

    public void remove(FileSystemResource resource) {
        contents.remove(resource);
        notifyObservers(
            new FileSystemResourceRemovedEvent(resource, this)
        );
    }

    public Optional<FileSystemResource> findByName(String name) {
        return contents.stream()
            .filter(resource -> resource.getName().equals(name))
            .findFirst();
    }

    @Override
    public FileSystemDirectory createCopy() {
        FileSystemDirectory copy = new FileSystemDirectory(getName());
        copy.getContents().addAll(
            contents.stream()
                .map(FileSystemResource::createCopy)
                .collect(Collectors.toList()));
        return copy;
    }

    public Iterator<FileSystemResource> iterator() {
        return contents.iterator();
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visitDirectory(this);
    }
}
