package top.chokhoou.amiya.utils.exception;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
public class ServiceException extends AmiyaException {

    public ServiceException() {
        super(-1);
    }

    public ServiceException(String message) {
        super(-1, message);
    }

    public ServiceException(int code, String message) {
        super(code, message);
    }
}
