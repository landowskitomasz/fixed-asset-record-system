package com.ewid.ewidserveradmin.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.ewid.ewidserveradmin.util.DbUtils;
import com.ewid.ewidserveradmin.util.SHA1;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;

    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
        	conn = DbUtils.getDbConnection();
        	stmt = conn.prepareStatement("select 1 from users where login = ? and password = ? and active = 1");
        	stmt.setString(1, credentials.getUsername());
        	stmt.setString(2, SHA1.doSHA1(credentials.getPassword()));
        	rs = stmt.executeQuery();
        	
        	if(!rs.next()) {
        		return false;
        	}
        	rs.close();
        	stmt.close();
        	
        	stmt = conn.prepareStatement("select r.role from users u inner join groups g on (u.group_id = g.id) inner join groups_roles g_r on (g.id = g_r.group_id) inner join roles r on (g_r.role = r.role) where u.login = ? and u.password = ?");
        	stmt.setString(1, credentials.getUsername());
        	stmt.setString(2, SHA1.doSHA1(credentials.getPassword()));
        	rs = stmt.executeQuery();
        	
        	while(rs.next()) {
        		identity.addRole(rs.getString(1));
        	}
        	     	
        	return true;
        }
        catch(Exception e) {
        	return false;
        }
        finally {
        	DbUtils.closeConnection(conn, rs);
        	DbUtils.closeStatement(stmt, null);
        }
    }
}
