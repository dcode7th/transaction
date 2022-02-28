package au.com.me.transaction.service;

import au.com.me.transaction.constant.DateConstant;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TransactionServiceTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.DATE_FORMAT);

    @Test
    public void depositsWithAReversalTest() {

        Double result = (new TransactionService()).getRelativeAccountBalance("ACC334455",setupDate("20/10/2018 12:47:55"), setupDate("20/10/2018 19:00:00"));
        assertEquals(-25.0, result.doubleValue());
    }
    @Test
    public void noRecordsFoundTest() {

        Double result = (new TransactionService()).getRelativeAccountBalance("ACC334456",setupDate("20/10/2018 12:47:55"), setupDate("20/10/2018 19:00:00"));
        assertEquals(0.0, result.doubleValue());
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
