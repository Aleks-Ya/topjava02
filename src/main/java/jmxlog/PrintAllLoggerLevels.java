package jmxlog;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.List;

/**
 * Команда "Вывести на консоль уровни всех логгеров".
 */
class PrintAllLoggerLevels extends AbstractCommand {

    public PrintAllLoggerLevels(MBeanServerConnection connection, ObjectName jmxConfiguratorName) {
        super(jmxConfiguratorName, connection);
    }

    @Override
    public boolean isAppropriateParameters(String[] args) {
        return (args != null) && (args.length == 1) && ("print-levels".equalsIgnoreCase(args[0]));
    }

    @Override
    public void execute(String[] args) throws Exception {
        List<String> loggerNames = (List<String>) connection.getAttribute(jmxConfiguratorName, "LoggerList");
        System.out.println("Logger levels:");
        for (String loggerName : loggerNames) {
            String level = (String) connection.invoke(
                    jmxConfiguratorName,
                    "getLoggerEffectiveLevel",
                    new String[]{loggerName},
                    new String[]{String.class.getName()}
            );
            System.out.printf("%s: %s%n", loggerName, level);
        }
    }
}
