package mp.exercise.filesystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public abstract class FileSystemResource {

    private String name;
    private Collection<FileSystemResourceObserver> observers = new ArrayList<>();

    public FileSystemResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileSystemResource other = (FileSystemResource) obj;
        return Objects.equals(name, other.name);
    }

    public abstract FileSystemResource createCopy();

    public abstract void accept(FileSystemVisitor visitor);

    /**
     * Just for testing, package-private
     */
    protected Collection<FileSystemResourceObserver> getObservers() {
        return observers;
    }

    public void addObserver(FileSystemResourceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FileSystemResourceObserver observer) {
        observers.remove(observer);
    }

    public void rename(String newName) {
        FileSystemResourceEvent event =
            new FileSystemResourceRenameEvent(this, getName());
        setName(newName);
        notifyObservers(event);
    }

    protected void notifyObservers(FileSystemResourceEvent event) {
        observers.stream()
            .forEach(observer -> observer.notifyChange(event));
    }
}