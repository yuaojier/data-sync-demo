package delay;

public class MemoryTaskStorageFactory implements TaskStorageFactory {
    @Override
    public TaskStorage getPaddingStorage() {
        return newStorage();
    }

    @Override
    public TaskStorage getRecoverStorage() {
        return newStorage();
    }

    @Override
    public TaskStorage getRemoveStorage() {
        return newStorage();
    }

    private TaskStorage newStorage() {
        return new MemoryTaskStorage();
    }
}
