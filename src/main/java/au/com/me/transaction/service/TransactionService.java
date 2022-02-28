package au.com.me.transaction.service;

import au.com.me.transaction.model.Transaction;
import au.com.me.transaction.model.TransactionTypeEnum;
import au.com.me.transaction.repository.TransactionRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * This class provides transaction functional methods.
 * @author  Bancha Setthanan
 * @version 1.0
 * @since   2022-02-28
 * */
public class TransactionService {
    /**
     * Returns relative account balance that match input parameters.
     *
     * @param  accountId  the source account Id in transaction
     * @param  fromDate the start date time to search for transactions
     *
     * @param  toDate the end date time to search for transactions
     * @return      the relative balance
     */
    public Double getRelativeAccountBalance(String accountId, Date fromDate, Date toDate){
        TransactionRepository transactionRepository = new TransactionRepository();
        // Recreate date time from toDate
        // if the minute or second are 0, it is implied that the user intend to search over the 59 minute or 59 second
        Calendar calendar = getCalendar(toDate);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        if(minutes == 0) {
            calendar.set(Calendar.MINUTE, 59);
        }
        if(seconds == 0) {
            calendar.set(Calendar.SECOND, 59);
        }
        Double result = 0.00;
        // Get all transaction from repository
        List<Transaction> transactions = transactionRepository.getTransactions(accountId,fromDate,calendar.getTime());
        // Process payment and reversal amount
        for(Transaction transaction: transactions){
            if(transaction.getTransactionType() == TransactionTypeEnum.PAYMENT) {
                result -= transaction.getAmount();
            }
            if(transaction.getTransactionType() == TransactionTypeEnum.REVERSAL) {
                result += transaction.getAmount();
            }
        }
        return result;
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
