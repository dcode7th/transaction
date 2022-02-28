package au.com.me.transaction.exception;
/**
 * This class is for data validation exception.
 * @author  Bancha Setthanan
 * @version 1.0
 * @since   2022-02-28
 * */
public class InvalidValueException extends RuntimeException{
    public InvalidValueException(String value) {
        super(value);
    }
}
