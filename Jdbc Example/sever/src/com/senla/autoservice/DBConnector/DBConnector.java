package com.senla.autoservice.DBConnector;

import java.io.Closeable;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.Connection;

public class DBConnector implements Closeable {
	private final Logger logger = LogManager.getLogger(getClass().getSimpleName());
	
	private static Connection con;
	private static DBConnector instance;

	private DBConnector() throws SQLException {
		connect();
	}

	public static DBConnector getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnector();
		}
		return instance;
	}

	private void connect() throws SQLException {
		new DBConfig();
		String url;
		String user;
		String password;
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			url = DBConfig.getProp("url");
			password = DBConfig.getProp("password");
			user = DBConfig.getProp("user");
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			SQLException noCon = new SQLException("Connection failed");
			logger.error(e);
			throw noCon;
		}
	}

	public Connection getConnnection() throws SQLException{
		try {
			if (con == null || con.isClosed()) {
				connect();
				return con;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return con;
	}
	
	@Override
	public void close() {
		try {
			if (!con.isClosed() || con != null) {
				con.close();
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

}
