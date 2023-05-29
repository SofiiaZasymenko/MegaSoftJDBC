package org.example.models;

public class MaxProjectsClient {
    private final String name;
    private final Integer projectCount;

    public MaxProjectsClient(String name, Integer projectCount) {
        this.name = name;
        this.projectCount = projectCount;
    }

    public String getName() {
        return name;
    }

    public Integer getProjectCount() {
        return projectCount;
    }

    @Override
    public String toString() {
        return "MaxProjectsClient{" +
                "name='" + name + '\'' +
                ", projectCount=" + projectCount +
                '}';
    }
}