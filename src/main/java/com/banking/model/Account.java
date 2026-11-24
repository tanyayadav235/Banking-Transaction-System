
package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private long accountId;
    private long customerId;
    private String accountNumber;
    private BigDecimal balance;
    private String status;
    private LocalDateTime createdAt;

    // Constructor
    public Account(long accountId, long customerId,
                   String accountNumber, BigDecimal balance,
                   String status, LocalDateTime createdAt) {

        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters
    public long getAccountId() {
        return accountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
