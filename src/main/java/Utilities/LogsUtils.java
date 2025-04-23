package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsUtils
{
    public static String LOGS_PATH="test-outputs/Logs" ;









    public static void logTrace(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .trace(message);

    }
    public static void logDebug(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .debug(message);

    }
    public static void logInfo(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .info(message);

    }
    public static void logWarn(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .warn(message);

    }
    public static void logError(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .error(message);

    }
    public static void logFatal(String message)
    {

        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .fatal(message);

    }

}
