-- Insert sample customers
INSERT INTO customer (name, email, phone) VALUES
                                              ('Asha', 'asha@example.com', '9000000001'),
                                              ('Rahul', 'rahul@example.com', '9000000002'),
                                              ('Priya', 'priya@example.com', '9000000003');

-- Insert sample accounts
INSERT INTO account (customer_id, account_number, balance, status)
SELECT customer_id, 'ACC1001', 10000.00, 'ACTIVE'
FROM customer WHERE email = 'asha@example.com';

INSERT INTO account (customer_id, account_number, balance, status)
SELECT customer_id, 'ACC1002', 15000.00, 'ACTIVE'
FROM customer WHERE email = 'rahul@example.com';

INSERT INTO account (customer_id, account_number, balance, status)
SELECT customer_id, 'ACC1003', 20000.00, 'ACTIVE'
FROM customer WHERE email = 'priya@example.com';