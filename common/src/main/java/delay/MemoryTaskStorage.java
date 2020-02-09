package delay;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryTaskStorage implements TaskStorage {
    private Multimap<String, String> map = Multimaps.synchronizedMultimap(ArrayListMultimap.create());

    @Override
    public boolean add(String taskName, String taskKey) {
        return map.put(taskName, taskKey);
    }

    @Override
    public boolean remove(String taskName, String taskKey) {
        return map.remove(taskName, taskKey);
    }

    @Override
    public boolean contains(String taskName, String taskKey) {
        return map.containsEntry(taskName, taskKey);
    }
}
