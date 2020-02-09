package thread;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public abstract class MenuRunnableApi implements Runnable {

    protected ChildThreadException childThreadException;

    protected Map resultTypeObjectMap;

    public MenuRunnableApi(Map resultTypeObjectMap) {
        this.resultTypeObjectMap = resultTypeObjectMap;
    }

    public MenuRunnableApi(ChildThreadException childThreadException) {
        super();
        this.childThreadException = childThreadException;
    }

    public MenuRunnableApi(ChildThreadException childThreadException, Map resultTypeObjectMap) {
        this.childThreadException = childThreadException;
        this.resultTypeObjectMap = resultTypeObjectMap;
    }
}
