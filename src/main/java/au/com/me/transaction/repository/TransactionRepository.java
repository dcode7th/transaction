package au.com.me.transaction.repository;

import au.com.me.transaction.constant.DateConstant;
import au.com.me.transaction.constant.FileConstant;
import au.com.me.transaction.exception.InvalidValueException;
import au.com.me.transaction.model.Transaction;
import au.com.me.transaction.model.TransactionTypeEnum;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * This class provides transaction data access.
 * @author  Bancha Setthanan
 * @version 1.0
 * @since   2022-02-28
 * */

public class TransactionRepository {

    public TransactionRepository() {
        setFileName(FileConstant.INITIAL_FILE_NAME);
    }

    String fileName;

    /**
     * Returns transactions  from CSV file that match input parameters.
     *
     * @param  accountId  the source account Id in transaction
     * @param  fromDateTime the start date time to search for transactions
     * @param  toDateTime the end date time to search for transactions
     * @return      the transactions
     */
    public List<Transaction> getTransactions(String accountId, Date fromDateTime, Date toDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.DATE_FORMAT);

        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            String file = getClass().getClassLoader().getResource(getFileName()).getFile();
            // Prepare CSV reader
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] fields = null;
            //Skip the header row
            reader.readNext();
            // Read and parse CSV record by record
            while ((fields = reader.readNext()) != null) {
                // Validate field values in the record
                validateFields(fields);
                // Populate data to model
                Transaction transaction = new Transaction();
                transaction.setTransactionId(fields[0].trim());
                transaction.setFromAccountId(fields[1].trim());
                transaction.setToAccountId(fields[2].trim());
                transaction.setCreatedAt(dateFormat.parse(fields[3].trim()));
                transaction.setAmount(Double.valueOf(fields[4].trim()));
                transaction.setTransactionType(TransactionTypeEnum.fromValue(fields[5].trim()));
                if(transaction.getTransactionType() == TransactionTypeEnum.REVERSAL) {
                    transaction.setRelatedTransaction(fields[6].trim());
                }
                if(isMatchCriteria(transaction, accountId,fromDateTime,toDateTime)){
                    transactions.add(transaction);
                }
            }

            reader.close();
        }catch( Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
    void validateFields(String[] fields) {
        for(String field: fields){
            if(field == null || "".equals(field)) {
                throw new InvalidValueException("Record contain null or empty value");
            }
        }
    }
    boolean isMatchCriteria(Transaction transaction, String accountId, Date fromDate, Date toDate) {
        boolean result = false;
        long transactionCreatedTime = transaction.getCreatedAt().getTime();
        long fromDateTime = fromDate.getTime();
        long toDateTime = toDate.getTime();
        if(transaction.getFromAccountId().equals(accountId) && transactionCreatedTime >= fromDateTime && transactionCreatedTime <= toDateTime) {
            result = true;
        }
        return result;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
