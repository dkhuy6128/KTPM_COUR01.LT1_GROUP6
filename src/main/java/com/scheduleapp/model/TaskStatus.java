package com.scheduleapp.model;

/**
 * Enum để xác định trạng thái của task
 */
public enum TaskStatus {
    TODO("📝 Chưa làm"),
    IN_PROGRESS("⏳ Đang làm"),
    COMPLETED("✅ Hoàn thành"),
    CANCELLED("❌ Hủy");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
