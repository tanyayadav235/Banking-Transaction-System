
import com.banking.service.TransactionService;
import java.math.BigDecimal;

public class TransactionServiceTest {

    public static void main(String[] args) {

        TransactionService service = new TransactionService();

        try {
            service.transfer(
                    1,
                    2,
                    new BigDecimal("1000.00")
            );

        } catch (Exception e) {
            System.out.println("Transfer rejected!");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
