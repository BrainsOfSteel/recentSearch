package org.example.model;

public class CachedSearch {
    private String value;
    private Long timestamp;

    public CachedSearch(String value, Long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public CachedSearch() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CachedSearch{" +
                "value='" + value + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
