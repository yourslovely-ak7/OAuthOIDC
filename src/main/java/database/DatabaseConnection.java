package database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection 
{
	private static final String url = "jdbc:mysql://localhost:3306/demo";
	private static final String user = "athi-pt7617";
	private static final String password = "root";
	private static final String driverClass = "com.mysql.cj.jdbc.Driver";
	private static final HikariConfig config = new HikariConfig();
	private static final HikariDataSource dataSource;

	static {
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		config.setDriverClassName(driverClass);
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		config.setIdleTimeout(30000);
		config.setMaxLifetime(1800000);
		dataSource = new HikariDataSource(config);
	}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
