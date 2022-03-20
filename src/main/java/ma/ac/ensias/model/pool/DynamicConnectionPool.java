package ma.ac.ensias.model.pool;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DynamicConnectionPool {
    private static final int MIN_POOL_SIZE = 8;
    private static final int MAX_POOL_SIZE = 32;
    private final AtomicInteger currentPoolSize = new AtomicInteger(MIN_POOL_SIZE);

    private static final DynamicConnectionPool instance = new DynamicConnectionPool();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;

    private static final Lock locker = new ReentrantLock();

    private DynamicConnectionPool() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {

        }
        freeConnections = new LinkedBlockingDeque<>();
        for (int i = 0; i < MIN_POOL_SIZE; i++) {
            Connection connection;
            try {
                connection = ConnectionCreator.createConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Error while getting connection from driver manager", e);
            }
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnections.offer(proxyConnection);
        }
        givenAwayConnections = new LinkedBlockingDeque<>();
    }

    public static DynamicConnectionPool getInstance() {
        return instance;
    }

    public Connection provideConnection() {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
            locker.lock();
            if(givenAwayConnections.size() == currentPoolSize.get()){
                if(currentPoolSize.get() < MAX_POOL_SIZE){

                    if(currentPoolSize.get() < MAX_POOL_SIZE){
                        for(int i = 0; i < currentPoolSize.get(); i++){
                            Connection newConnection;
                            try {
                                newConnection = ConnectionCreator.createConnection();
                            } catch (SQLException e) {
                                throw new RuntimeException("Error while getting connection from driver manager", e);
                            }
                            ProxyConnection proxyConnection = new ProxyConnection(newConnection);
                            freeConnections.offer(proxyConnection);
                        }
                        currentPoolSize.set(currentPoolSize.get() * 2);
                    }

                }
            }
            locker.unlock();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error while getting connection from queue", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
            locker.lock();
            if(givenAwayConnections.size() < currentPoolSize.get() / 4){
                if(currentPoolSize.get() > MIN_POOL_SIZE){
                    if(currentPoolSize.get() > MIN_POOL_SIZE){
                        currentPoolSize.set(currentPoolSize.get() / 2);
                        for(int i = 0; i < currentPoolSize.get(); i++){
                            try {
                                try {
                                    freeConnections.take().realClose();
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                        }
                    }
                }

            }
            locker.unlock();
        } else {
            System.out.println("invalid con");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < currentPoolSize.get(); i++) {
            try {
                try {
                    freeConnections.take().realClose();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                System.out.println("lol") ;
            }
        });
    }
}
