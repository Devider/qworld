package org.dron.world;

/**
 * Created by PhantomX on 20.04.14.
 */
public class CrashException extends Exception {
    public CrashException() {
    }

    public CrashException(String message) {
        super(message);
    }

    public CrashException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrashException(Throwable cause) {
        super(cause);
    }

    public CrashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
