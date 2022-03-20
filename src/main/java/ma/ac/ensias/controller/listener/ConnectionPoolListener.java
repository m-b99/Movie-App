package ma.ac.ensias.controller.listener;

import ma.ac.ensias.model.pool.DynamicConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DynamicConnectionPool.getInstance().destroyPool();
    }
}
