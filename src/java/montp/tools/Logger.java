package montp.tools;

import java.time.LocalDateTime;

public abstract class Logger {

    public static enum LogLevel {
        INFO, MORE_INFO, NOTICE, WARNING, ERROR, CRITICAL
    }
    
    public static void info(String msg, Object... args) {
        log(LogLevel.INFO, "", msg, args);
    }

    public static void log(LogLevel logLevel, String func, String msg, Object... args) {
        String levelName;
        switch (logLevel) {
            case INFO:
            case MORE_INFO:
                levelName = "INFO";
                break;
            case NOTICE:
                levelName = "NOTICE";
                break;
            case WARNING:
                levelName = "WARNING";
                break;
            case ERROR:
                levelName = "ERROR";
                break;
            case CRITICAL:
                levelName = "CRITICAL";
                break;
            default:
                levelName = "";
                break;
        }
        System.out.format("%s %s %s : %s\n",
                LocalDateTime.now().toString(),
                levelName,
                func,
                String.format(msg, args));
    }

}
