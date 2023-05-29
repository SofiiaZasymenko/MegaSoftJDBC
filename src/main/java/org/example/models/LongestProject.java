package org.example.models;

public class LongestProject {
    private final Integer id;
    private final Integer monthCount;

    public LongestProject(Integer id, Integer monthCount) {
        this.id = id;
        this.monthCount = monthCount;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    @Override
    public String toString() {
        return "LongestProject{" +
                "id=" + id +
                ", monthCount=" + monthCount +
                '}';
    }
}