package ma.ac.ensias.util;

import ma.ac.ensias.model.pool.DynamicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DaoUtil {

    private static final String EQUALS_STRING = " = ";
    private static final String AND_DELIMITER = " AND ";
    private static final char QUOTE = '\'';
    private static final String WHERE = " WHERE ";

    private DaoUtil(){}

    public static void releaseResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        releaseResources(connection, preparedStatement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void releaseResources(Connection connection, PreparedStatement preparedStatement) {
        DynamicConnectionPool.getInstance().releaseConnection(connection);
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static String createQueryWithCriteria(String queryStart, Map<String, String> criteria) {
        List<String> conditions = new ArrayList<>();
        Set<String> keys = criteria.keySet();
        for (String key : keys) {
            String condition = key + EQUALS_STRING + QUOTE + criteria.get(key) + QUOTE;
            conditions.add(condition);
        }
        StringJoiner query = new StringJoiner(AND_DELIMITER);
        for (String condition : conditions) {
            query.add(condition);
        }
        if (query.toString().isEmpty()) {
            return queryStart;
        }
        return queryStart + WHERE + query.toString();
    }
}
