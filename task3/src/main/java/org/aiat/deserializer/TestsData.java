package org.aiat.deserializer;
import java.util.List;


public class TestsData {
    private final long id;
    private final String title;
    private final String value;
    private final List<TestsData> values;

    public TestsData(long id, String title, String value, List<TestsData> values) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.values = values;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getValue() {
        return value;
    }
    public List<TestsData> getValues() {
        return values;
    }


}
