package liuwei.job.core.exception;

public class RegisterJobException extends RuntimeException {

    public RegisterJobException() {
    }

    public RegisterJobException(String message) {
        super(message);
    }

    public RegisterJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterJobException(Throwable cause) {
        super(cause);
    }

    public RegisterJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
