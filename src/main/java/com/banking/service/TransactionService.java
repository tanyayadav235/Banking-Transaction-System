
package com.banking.service;

import com.banking.exception.InsufficientBalanceException;
import com.banking.database.DBConnection;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {

    public void deposit(long accountId, BigDecimal amount) {

        // Validate amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException(
                    "Deposit amount must be greater than zero"
            );
        }

        String sql = """
                UPDATE account
                SET balance = balance + ?
                WHERE account_id = ?
                AND status = 'ACTIVE'
                """;

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setBigDecimal(1, amount);
                statement.setLong(2, accountId);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new AccountNotFoundException(
                            "Account not found or account is not active: "
                                    + accountId
                    );
                }

                connection.commit();

                System.out.println("Deposit successful!");
                System.out.println("Deposited amount: ₹" + amount);

            } catch (Exception e) {

                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Deposit failed", e);
        }
    }


    public void withdraw(long accountId, BigDecimal amount) {

        // 1. Validate withdrawal amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // 2. Lock the account row and retrieve its balance
                String selectSql = """
                    SELECT balance, status
                    FROM account
                    WHERE account_id = ?
                    FOR UPDATE
                    """;

                BigDecimal currentBalance;
                String status;

                try (PreparedStatement selectStatement =
                             connection.prepareStatement(selectSql)) {

                    selectStatement.setLong(1, accountId);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {

                        if (!resultSet.next()) {
                            throw new AccountNotFoundException(
                                    "Account not found: " + accountId
                            );
                        }

                        currentBalance = resultSet.getBigDecimal("balance");
                        status = resultSet.getString("status");
                    }
                }

                // 3. Check account status
                if (!"ACTIVE".equals(status)) {
                    throw new InvalidTransactionException(
                            "Account is not active"
                    );
                }

                // 4. Check sufficient balance
                if (currentBalance.compareTo(amount) < 0) {
                    throw new InsufficientBalanceException(
                            "Insufficient balance! Available: ₹"
                                    + currentBalance
                    );
                }

                // 5. Deduct the amount
                String updateSql = """
                    UPDATE account
                    SET balance = balance - ?
                    WHERE account_id = ?
                    """;

                try (PreparedStatement updateStatement =
                             connection.prepareStatement(updateSql)) {

                    updateStatement.setBigDecimal(1, amount);
                    updateStatement.setLong(2, accountId);

                    updateStatement.executeUpdate();
                }

                // 6. Commit transaction
                connection.commit();

                System.out.println("Withdrawal successful!");
                System.out.println("Withdrawn amount: ₹" + amount);

            } catch (Exception e) {

                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Withdrawal failed", e);
        }
    }


    public void transfer(long sourceAccountId,
                         long destinationAccountId,
                         BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException(
                    "Transfer amount must be greater than zero"
            );
        }

        if (sourceAccountId == destinationAccountId) {
            throw new InvalidTransactionException(
                    "Source and destination accounts cannot be the same"
            );
        }

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                String selectSql = """
                    SELECT account_id, balance, status
                    FROM account
                    WHERE account_id IN (?, ?)
                    ORDER BY account_id
                    FOR UPDATE
                    """;

                BigDecimal sourceBalance = null;
                int foundAccounts = 0;

                try (PreparedStatement statement =
                             connection.prepareStatement(selectSql)) {

                    statement.setLong(1, sourceAccountId);
                    statement.setLong(2, destinationAccountId);

                    try (ResultSet resultSet = statement.executeQuery()) {

                        while (resultSet.next()) {

                            foundAccounts++;

                            long id = resultSet.getLong("account_id");
                            BigDecimal balance =
                                    resultSet.getBigDecimal("balance");
                            String status = resultSet.getString("status");

                            if (!"ACTIVE".equals(status)) {
                                throw new InvalidTransactionException(
                                        "Account is not active: " + id
                                );
                            }

                            if (id == sourceAccountId) {
                                sourceBalance = balance;
                            }
                        }
                    }
                }

                if (foundAccounts != 2) {
                    throw new AccountNotFoundException(
                            "Source or destination account not found"
                    );
                }

                if (sourceBalance.compareTo(amount) < 0) {
                    throw new InsufficientBalanceException(
                            "Insufficient balance! Available: ₹"
                                    + sourceBalance
                    );
                }

                String debitSql = """
                    UPDATE account
                    SET balance = balance - ?
                    WHERE account_id = ?
                    """;

                try (PreparedStatement statement =
                             connection.prepareStatement(debitSql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setLong(2, sourceAccountId);
                    statement.executeUpdate();
                }

                String creditSql = """
                    UPDATE account
                    SET balance = balance + ?
                    WHERE account_id = ?
                    """;

                try (PreparedStatement statement =
                             connection.prepareStatement(creditSql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setLong(2, destinationAccountId);
                    statement.executeUpdate();
                }

                connection.commit();

                System.out.println("Transfer successful!");
                System.out.println("Transferred amount: ₹" + amount);

            } catch (Exception e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Transfer failed", e);
        }
    }


}
