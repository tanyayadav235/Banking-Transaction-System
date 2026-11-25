
import com.banking.dao.TransactionAuditDAO;
import com.banking.model.TransactionAudit;

import java.time.LocalDateTime;

public class TransactionAuditDAOTest {

    public static void main(String[] args) {

        TransactionAuditDAO auditDAO = new TransactionAuditDAO();

        TransactionAudit audit = new TransactionAudit(
                0,
                "TEST_TXN_001",
                Thread.currentThread().getName(),
                "WITHDRAWAL",
                "PENDING",
                25,
                LocalDateTime.now(),
                "Test audit record"
        );

        auditDAO.save(audit);
    }
}
