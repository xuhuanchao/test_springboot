package com.xhc.test.springboot.exception;

/**
 * Created by xuhuanchao on 2018/5/24.
 */
public class PlatformException extends RuntimeException {

    private String code;
    private String msg;

    public PlatformException(String code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public PlatformException(String message) {
        super(message);
        this.msg = message;
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    protected PlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = message;
    }
}
