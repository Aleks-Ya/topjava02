package jmxlog;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * Управление LogBack через JMX.
 * <p>
 * Команды:
 * Вывести в консоль уровни всех логгеров: java jmxlog.JmxLoggerControl print-levels
 * Установить уровень для всех логгеров: java jmxlog.JmxLoggerControl set-levels INFO
 */
public class JmxLoggerControl {

    public static void main(String[] args) throws Exception {
        JMXConnector jmxc = null;
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:1099/jmxrmi");
            jmxc = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection connection = jmxc.getMBeanServerConnection();
            ObjectName jmxConfiguratorName = new ObjectName(
                    "ch.qos.logback.classic:Name=default,Type=ch.qos.logback.classic.jmx.JMXConfigurator");

            Set<ICommand> commands = new HashSet<>();
            commands.add(new PrintAllLoggerLevels(connection, jmxConfiguratorName));
            commands.add(new SetAllLoggerLevels(connection, jmxConfiguratorName));

            for (ICommand command : commands) {
                if (command.isAppropriateParameters(args)) {
                    command.execute(args);
                    return;
                }
            }
            throw new InvalidParameterException("Wrong command");
        } finally {
            if (jmxc != null) {
                jmxc.close();
            }
        }
    }
}