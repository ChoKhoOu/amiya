package top.chokhoou.amiya.utils.exception;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
public class BusinessException extends AmiyaException {

    public BusinessException() {
        super(-1);
    }

    public BusinessException(String message) {
        super(-1, message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }
}
