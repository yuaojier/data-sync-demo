package event;

import lombok.Data;

@Data
public abstract class Event {
    private String sourceTag;

    public Event(String sourceTag) {
        this.sourceTag = sourceTag;
    }

    public String getSourceTag() {
        return sourceTag;
    }
}
