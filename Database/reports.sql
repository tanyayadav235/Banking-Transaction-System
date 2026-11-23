
-- ==========================================
-- BANKING TRANSACTION PROCESSING SYSTEM
-- REPORT QUERIES
-- ==========================================


-- 1. TOP 10 ACCOUNTS BY TRANSACTION VOLUME

WITH account_activity AS (
    SELECT source_account_id AS account_id
    FROM transactions
    WHERE source_account_id IS NOT NULL

    UNION ALL

    SELECT destination_account_id AS account_id
    FROM transactions
    WHERE destination_account_id IS NOT NULL
)
SELECT
    a.account_id,
    a.account_number,
    COUNT(*) AS transaction_volume
FROM account_activity aa
         JOIN account a
              ON aa.account_id = a.account_id
GROUP BY a.account_id, a.account_number
ORDER BY transaction_volume DESC
    LIMIT 10;


-- 2. FAILED TRANSACTIONS GROUPED BY REASON

SELECT
    failure_reason,
    COUNT(*) AS total_failures
FROM failed_transaction
GROUP BY failure_reason
ORDER BY total_failures DESC;


-- 3. DAILY TRANSACTION AMOUNT BY TRANSACTION TYPE

SELECT
    DATE(created_at) AS transaction_date,
    transaction_type,
    SUM(amount) AS total_amount,
    COUNT(*) AS total_transactions
FROM transactions
WHERE status = 'SUCCESS'
GROUP BY
    DATE(created_at),
    transaction_type
ORDER BY
    transaction_date DESC,
    transaction_type;


-- 4. ACCOUNTS WITH MORE THAN 5 TRANSACTIONS

WITH account_activity AS (
    SELECT source_account_id AS account_id
    FROM transactions
    WHERE source_account_id IS NOT NULL

    UNION ALL

    SELECT destination_account_id AS account_id
    FROM transactions
    WHERE destination_account_id IS NOT NULL
)
SELECT
    a.account_id,
    a.account_number,
    COUNT(*) AS total_transactions
FROM account_activity aa
         JOIN account a
              ON aa.account_id = a.account_id
GROUP BY a.account_id, a.account_number
HAVING COUNT(*) > 5
ORDER BY total_transactions DESC;


-- 5. COMPLETE TRANSACTION HISTORY FOR A CUSTOMER

SELECT
    c.customer_id,
    c.name,
    a.account_number,
    t.transaction_id,
    t.transaction_type,
    t.amount,
    t.status,
    t.created_at,
    t.processed_at,
    t.failure_reason
FROM customer c
         JOIN account a
              ON c.customer_id = a.customer_id
         JOIN transactions t
              ON a.account_id = t.source_account_id
                  OR a.account_id = t.destination_account_id
WHERE c.customer_id = 1
ORDER BY t.created_at DESC;
