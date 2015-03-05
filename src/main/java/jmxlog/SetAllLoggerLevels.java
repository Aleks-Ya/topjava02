package jmxlog;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.io.IOException;
import java.util.List;

/**
 * Команда "Задать уровни для всех логгеров".
 */
class SetAllLoggerLevels extends AbstractCommand {

    public SetAllLoggerLevels(MBeanServerConnection connection, ObjectName jmxConfiguratorName) {
        super(jmxConfiguratorName, connection);
    }

    @Override
    public boolean isAppropriateParameters(String[] args) {
        return (args != null) &&
                (args.length == 2) &&
                ("set-levels".equalsIgnoreCase(args[0])) &&
                (("TRACE".equalsIgnoreCase(args[1])) ||
                        ("DEBUG".equalsIgnoreCase(args[1])) ||
                        ("INFO".equalsIgnoreCase(args[1])) ||
                        ("WARN".equalsIgnoreCase(args[1])) ||
                        ("ERROR".equalsIgnoreCase(args[1])) ||
                        ("FATAL".equalsIgnoreCase(args[1]))
                );
    }

    @Override
    public void execute(String[] args) throws Exception {
        List<String> loggerNames = (List<String>) connection.getAttribute(jmxConfiguratorName, "LoggerList");
        for (String loggerName : loggerNames) {
            setLoggerLevel(loggerName, args[1]);
        }
        System.out.println("New level: " + args[1]);
    }

    private void setLoggerLevel(String loggerName, String newLevel) throws MBeanException,
            AttributeNotFoundException, InstanceNotFoundException, ReflectionException,
            IOException, MalformedObjectNameException {

        connection.invoke(
                jmxConfiguratorName,
                "setLoggerLevel",
                new String[]{loggerName, newLevel},
                new String[]{String.class.getName(), String.class.getName()}
        );
    }
}