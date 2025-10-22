package com.oreo;

public class WeeklySummaryEvent {
    private final String summary;

    public WeeklySummaryEvent(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
