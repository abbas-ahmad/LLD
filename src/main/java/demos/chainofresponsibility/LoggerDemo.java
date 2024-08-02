package demos.chainofresponsibility;

public class LoggerDemo {
    public static void main(String[] args) {
        Logger logger = new InfoLogger(new DebugLogger(new ErrorLogger(null)));

        logger.log(Logger.INFO, "This is test log message");
        logger.log(Logger.DEBUG, "This is test log message");
        logger.log(Logger.ERROR, "This is test log message");
        logger.log(4, "This is test log message");
    }
}

abstract class Logger {
    protected static final int INFO = 1;
    protected static final int DEBUG = 2;
    protected static final int ERROR = 3;

    Logger next;

    Logger(Logger next) {
        this.next = next;
    }

    public void log(int level, String message) {
        if(next != null){
            next.log(level, message);
        }
        else{
            System.out.println("DEFAULT: " + message);
        }
    }
}

class InfoLogger extends Logger {

    InfoLogger(Logger next) {
        super(next);
    }

    public void log(int level, String message) {
        if(level == INFO){
            System.out.println("INFO: " + message);
        }
        else{
            super.log(level, message);
        }
    }
}

class DebugLogger extends Logger {
    DebugLogger(Logger next) {
        super(next);
    }

    public void log(int level, String message) {
        if(level == DEBUG){
            System.out.println("DEBUG: " + message);
        }
        else{
            super.log(level, message);
        }
    }
}

class ErrorLogger extends Logger {
    ErrorLogger(Logger next) {
        super(next);
    }

    public void log(int level, String message) {
        if(level == ERROR){
            System.out.println("ERROR: " + message);
        }
        else {
            super.log(level, message);
        }
    }
}