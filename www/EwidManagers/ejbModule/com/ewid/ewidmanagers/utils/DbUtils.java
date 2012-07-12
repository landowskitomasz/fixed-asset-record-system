package com.ewid.ewidmanagers.utils;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

import org.apache.log4j.Logger;

public class DbUtils {
	private static String DATASOURCE_CONTEXT = "java:/EwidServerAdminDatasource";
	private static Logger logger = Logger.getLogger(DbUtils.class);
	
	public static Connection getDbConnection() throws NamingException, SQLException {
		Connection result = null;
		try {
		  Context initialContext = new InitialContext();
		  DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
		  result = datasource.getConnection();
		}
		catch (NamingException ex) {
			throw ex;
		}
		catch(SQLException ex){
			throw ex;
		}
	    return result;
	}
	
	public static void closeConnection(Connection conn) {
		try{
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			logger.error("Error while closeConnection.", e);
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		try{
			if(rs != null) {
				rs.close();
			}
		} catch(SQLException e) {
			logger.error("Error while closeConnection.", e);
		}
	}
	
	public static void closeStatement(Statement stmt) {
		try {
			if(stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("Error while closeConnection.", e);
		}
	}
}
