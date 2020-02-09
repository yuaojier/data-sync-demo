package delay;

public interface TaskStorageFactory {
    TaskStorage getPaddingStorage();

    TaskStorage getRecoverStorage();

    TaskStorage getRemoveStorage();
}
