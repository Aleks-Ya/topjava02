package jmxlog;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.io.IOException;
import java.util.List;

abstract class AbstractCommand implements ICommand {
    protected final MBeanServerConnection connection;
    protected final ObjectName jmxConfiguratorName;

    AbstractCommand(ObjectName jmxConfiguratorName, MBeanServerConnection connection) {
        this.jmxConfiguratorName = jmxConfiguratorName;
        this.connection = connection;
    }

    protected List<String> takeLoggerList() throws MBeanException, AttributeNotFoundException,
            InstanceNotFoundException, ReflectionException, IOException {
        return (List<String>) connection.getAttribute(jmxConfiguratorName, "LoggerList");
    }
}
