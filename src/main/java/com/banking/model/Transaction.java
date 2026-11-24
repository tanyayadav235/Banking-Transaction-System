
package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private String transactionType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String failureReason;

    // Constructor
    public Transaction(String transactionId,
                       Long sourceAccountId,
                       Long destinationAccountId,
                       BigDecimal amount,
                       String transactionType,
                       String status,
                       LocalDateTime createdAt,
                       LocalDateTime processedAt,
                       String failureReason) {

        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
        this.failureReason = failureReason;
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
