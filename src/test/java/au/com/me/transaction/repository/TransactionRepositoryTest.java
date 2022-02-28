package au.com.me.transaction.repository;

import au.com.me.transaction.constant.DateConstant;
import au.com.me.transaction.model.Transaction;
import au.com.me.transaction.service.TransactionService;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TransactionRepositoryTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.DATE_FORMAT);

    @Test
    public void depositsWithAReversalTest() {

        List<Transaction> transactions = (new TransactionRepository()).getTransactions("ACC334455",setupDate("20/10/2018 12:47:55"), setupDate("20/10/2018 19:00:00"));
        assertEquals(2, transactions.size());
    }
    @Test
    public void noRecordsFoundTest() {

        List<Transaction> transactions = (new TransactionRepository()).getTransactions("ACC334456",setupDate("20/10/2018 12:47:55"), setupDate("20/10/2018 19:00:00"));
        assertEquals(0, transactions.size());
    }

    @Test
    public void largeDataTest() {
        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.setFileName("large-data.csv");
        List<Transaction> transactions = transactionRepository.getTransactions("ACC334455",setupDate("28/02/2022 10:00:00"), setupDate("28/02/2022 10:10:10"));
        assertEquals(183337, transactions.size());
    }

    private Date setupDate(String dateString) {
        Date result = null;
        try {
            result = dateFormat.parse(dateString);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
