package top.chokhoou.amiya.utils.exception;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
public abstract class AmiyaException extends RuntimeException {
    protected int code;

    public AmiyaException(int code) {
        super();
        this.code = code;
    }

    public AmiyaException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
