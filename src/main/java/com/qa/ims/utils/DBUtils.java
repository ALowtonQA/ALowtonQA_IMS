package com.qa.ims.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtils {

	private static final Logger LOGGER = LogManager.getLogger();

	private final String dbUrl;
	private final String dbUser;
	private final String dbPassword;
	private final Connection conn;
	
	public DBUtils(String properties) {
		Properties dbProps = new Properties();
		try (InputStream fis = ClassLoader.getSystemResourceAsStream(properties)) {
			dbProps.load(fis);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		this.dbUrl = dbProps.getProperty("db.url", "");
		this.dbUser = dbProps.getProperty("db.user", "");
		this.dbPassword = dbProps.getProperty("db.password", "");
		this.conn = makeConnection();
	}

	public DBUtils() {
		this("db.properties");
	}

	/**
	 * @param paths - file paths as strings to execute
	 * @return int representing the row count of affected rows
	 */
	public int init(String... paths) {
		int modified = 0;

		for (String path : paths) {
			modified += executeSQLFile(path);
		}

		return modified;
	}

	public int executeSQLFile(String file) {
		int modified = 0;
		try (InputStream in = DBUtils.class.getResourceAsStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String fileAsString = br.lines().reduce((acc, next) -> acc + next).orElse("");
			String[] queries = fileAsString.split(";");
			modified += Stream.of(queries).map(string -> {
				try (Statement statement = conn.createStatement();) {
					return statement.executeUpdate(string);
				} catch (Exception e) {
					LOGGER.debug(e);
					return 0;
				}
			}).reduce((acc, next) -> acc + next).orElse(0);
		} catch (Exception e) {
			LOGGER.debug(e);
		}
		return modified;
	}
	
	/**
	 * @return New connection to the database
	 */
	public Connection makeConnection() {
		try {
			return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (SQLException e) {
			LOGGER.debug(e);
			return null;
		}
	}
	
	/**
	 * @return Current connection to the database
	 */
	public Connection getConnection() {
		return this.conn;
	}
	
	/**
	 * Closes the connection to the database
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			LOGGER.debug(e);
		}
	}
}
