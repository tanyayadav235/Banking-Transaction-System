
import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import java.util.Optional;

public class AccountDAOTest {

    public static void main(String[] args) {

        AccountDAO accountDAO = new AccountDAO();

        Optional<Account> result = accountDAO.findById(1);

        if (result.isPresent()) {

            Account account = result.get();

            System.out.println("Account Found!");
            System.out.println("Account ID: " + account.getAccountId());
            System.out.println("Customer ID: " + account.getCustomerId());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: ₹" + account.getBalance());
            System.out.println("Status: " + account.getStatus());

        } else {
            System.out.println("Account not found!");
        }
    }
}
