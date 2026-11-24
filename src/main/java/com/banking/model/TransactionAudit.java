
package com.banking.model;

import java.time.LocalDateTime;

public class TransactionAudit {

    private long auditId;
    private String transactionId;
    private String threadName;
    private String operation;
    private String executionStatus;
    private long processingTimeMs;
    private LocalDateTime auditTimestamp;
    private String remarks;

    // Constructor
    public TransactionAudit(long auditId,
                            String transactionId,
                            String threadName,
                            String operation,
                            String executionStatus,
                            long processingTimeMs,
                            LocalDateTime auditTimestamp,
                            String remarks) {

        this.auditId = auditId;
        this.transactionId = transactionId;
        this.threadName = threadName;
        this.operation = operation;
        this.executionStatus = executionStatus;
        this.processingTimeMs = processingTimeMs;
        this.auditTimestamp = auditTimestamp;
        this.remarks = remarks;
    }

    // Getters
    public long getAuditId() {
        return auditId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getOperation() {
        return operation;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public LocalDateTime getAuditTimestamp() {
        return auditTimestamp;
    }

    public String getRemarks() {
        return remarks;
    }
}
