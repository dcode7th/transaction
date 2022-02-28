package au.com.me.transaction.model;
/**
 * This class represent the transaction type.
 * @author  Bancha Setthanan
 * @version 1.0
 * @since   2022-02-28
 * */
public enum TransactionTypeEnum {
    PAYMENT("PAYMENT"),
    REVERSAL("REVERSAL");

    private String value;
    TransactionTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    // Convert String value to Enum
    public static TransactionTypeEnum fromValue(String v) {
        for (TransactionTypeEnum c : TransactionTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}