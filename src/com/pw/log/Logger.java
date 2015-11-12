package com.pw.log;

/**
 * Simple logger which logs to standars output
 *
 */
public class Logger {

    private String className;

    private Logger() {}
    private Logger(Class clazz) {
        className = clazz.getName();
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(clazz);
    }

    /**
     * Logs a given String to standard output
     *
     * @param message    - the message to log
     */
    public void log(String message) {
        System.out.println(String.format("%s - %s", className, message));
    }

}

