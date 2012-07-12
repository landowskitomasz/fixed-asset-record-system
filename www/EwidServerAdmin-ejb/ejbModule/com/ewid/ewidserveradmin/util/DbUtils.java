package com.ewid.ewidserveradmin.util;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

public class DbUtils {
	static String DATASOURCE_CONTEXT = "java:/EwidServerAdminDatasource";
	
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
	
	public static void closeConnection(Connection conn, ResultSet rs) {
		try{
			if(rs != null) {
				rs.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			// zalogowac niepowodzenie zamkniecia polaczenia!
		}
	}
	
	public static void closeStatement(Statement stmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			// zalogowac niepowodzenie zamkniecia polaczenia!
		}
	}
}
