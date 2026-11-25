
package com.banking.dao;

import com.banking.database.DBConnection;
import com.banking.model.TransactionAudit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionAuditDAO {

    public void save(TransactionAudit audit) {

        String sql = """
                INSERT INTO transaction_audit (
                    transaction_id,
                    thread_name,
                    operation,
                    execution_status,
                    processing_time_ms,
                    audit_timestamp,
                    remarks
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, audit.getTransactionId());
            statement.setString(2, audit.getThreadName());
            statement.setString(3, audit.getOperation());
            statement.setString(4, audit.getExecutionStatus());
            statement.setLong(5, audit.getProcessingTimeMs());

            statement.setTimestamp(
                    6, Timestamp.valueOf(audit.getAuditTimestamp()));

            statement.setString(7, audit.getRemarks());

            statement.executeUpdate();

            System.out.println("Audit record saved successfully!");

        } catch (SQLException e) {
            throw new RuntimeException("Error saving audit record", e);
        }
    }
}
