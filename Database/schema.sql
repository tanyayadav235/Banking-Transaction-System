
-- ==========================================
-- BANKING TRANSACTION PROCESSING SYSTEM
-- DATABASE SCHEMA
-- ==========================================

-- 1. CUSTOMER TABLE
CREATE TABLE customer (
                          customer_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE,
                          phone VARCHAR(15)
);

-- 2. ACCOUNT TABLE
CREATE TABLE account (
                         account_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         customer_id BIGINT NOT NULL,
                         account_number VARCHAR(20) UNIQUE NOT NULL,
                         balance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_customer
                             FOREIGN KEY (customer_id)
                                 REFERENCES customer(customer_id),

                         CONSTRAINT chk_balance
                             CHECK (balance >= 0),

                         CONSTRAINT chk_account_status
                             CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED'))
);

-- 3. TRANSACTIONS TABLE
CREATE TABLE transactions (
                              transaction_id VARCHAR(100) PRIMARY KEY,
                              source_account_id BIGINT,
                              destination_account_id BIGINT,
                              amount NUMERIC(15,2) NOT NULL,
                              transaction_type VARCHAR(20) NOT NULL,
                              status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              processed_at TIMESTAMP,
                              failure_reason TEXT,

                              CONSTRAINT fk_source_account
                                  FOREIGN KEY (source_account_id)
                                      REFERENCES account(account_id),

                              CONSTRAINT fk_destination_account
                                  FOREIGN KEY (destination_account_id)
                                      REFERENCES account(account_id),

                              CONSTRAINT chk_transaction_amount
                                  CHECK (amount > 0),

                              CONSTRAINT chk_transaction_type
                                  CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),

                              CONSTRAINT chk_transaction_status
                                  CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED', 'REJECTED')),

                              CONSTRAINT chk_transaction_accounts
                                  CHECK (
                                      (transaction_type = 'DEPOSIT'
                                          AND source_account_id IS NULL
                                          AND destination_account_id IS NOT NULL)
                                          OR
                                      (transaction_type = 'WITHDRAWAL'
                                          AND source_account_id IS NOT NULL
                                          AND destination_account_id IS NULL)
                                          OR
                                      (transaction_type = 'TRANSFER'
                                          AND source_account_id IS NOT NULL
                                          AND destination_account_id IS NOT NULL
                                          AND source_account_id <> destination_account_id)
                                      )
);

-- 4. TRANSACTION AUDIT TABLE
CREATE TABLE transaction_audit (
                                   audit_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   transaction_id VARCHAR(100),
                                   thread_name VARCHAR(100),
                                   operation VARCHAR(20),
                                   execution_status VARCHAR(20),
                                   processing_time_ms BIGINT,
                                   audit_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   remarks TEXT,

                                   CONSTRAINT fk_audit_transaction
                                       FOREIGN KEY (transaction_id)
                                           REFERENCES transactions(transaction_id)
);

-- 5. FAILED TRANSACTION TABLE
CREATE TABLE failed_transaction (
                                    failure_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    transaction_id VARCHAR(100),
                                    account_id BIGINT,
                                    failure_reason TEXT NOT NULL,
                                    error_category VARCHAR(50),
                                    failure_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                    CONSTRAINT fk_failed_transaction
                                        FOREIGN KEY (transaction_id)
                                            REFERENCES transactions(transaction_id),

                                    CONSTRAINT fk_failed_account
                                        FOREIGN KEY (account_id)
                                            REFERENCES account(account_id)
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_account_customer
    ON account(customer_id);

CREATE INDEX idx_transactions_source
    ON transactions(source_account_id);

CREATE INDEX idx_transactions_destination
    ON transactions(destination_account_id);

CREATE INDEX idx_transactions_created_at
    ON transactions(created_at);

CREATE INDEX idx_transactions_status
    ON transactions(status);

CREATE INDEX idx_audit_transaction
    ON transaction_audit(transaction_id);

CREATE INDEX idx_failed_transaction
    ON failed_transaction(transaction_id);

CREATE INDEX idx_failed_account
    ON failed_transaction(account_id);
