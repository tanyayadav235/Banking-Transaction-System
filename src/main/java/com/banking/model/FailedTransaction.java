
package com.banking.model;

import java.time.LocalDateTime;

public class FailedTransaction {

    private long failureId;
    private String transactionId;
    private Long accountId;
    private String failureReason;
    private String errorCategory;
    private LocalDateTime failureTimestamp;

    // Constructor
    public FailedTransaction(long failureId,
                             String transactionId,
                             Long accountId,
                             String failureReason,
                             String errorCategory,
                             LocalDateTime failureTimestamp) {

        this.failureId = failureId;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.failureReason = failureReason;
        this.errorCategory = errorCategory;
        this.failureTimestamp = failureTimestamp;
    }

    // Getters
    public long getFailureId() {
        return failureId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getErrorCategory() {
        return errorCategory;
    }

    public LocalDateTime getFailureTimestamp() {
        return failureTimestamp;
    }
}
