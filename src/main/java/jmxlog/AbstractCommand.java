package jmxlog;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

abstract class AbstractCommand implements ICommand{
    protected final MBeanServerConnection connection;
    protected final ObjectName jmxConfiguratorName;

    AbstractCommand(ObjectName jmxConfiguratorName, MBeanServerConnection connection) {
        this.jmxConfiguratorName = jmxConfiguratorName;
        this.connection = connection;
    }
}
