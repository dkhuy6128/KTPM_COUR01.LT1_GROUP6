package com.scheduleapp.model;

/**
 * Enum để phân loại độ ưu tiên của task
 */
public enum Priority {
    LOW("🟢 Thấp"),
    MEDIUM("🟡 Trung bình"),
    HIGH("🟠 Cao"),
    URGENT("🔴 Cấp bách");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
