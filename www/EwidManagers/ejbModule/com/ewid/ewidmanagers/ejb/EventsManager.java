package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = EventsManagerLocal.JNDI_NAME)
public class EventsManager implements EventsManagerLocal, Serializable{

	private static final long serialVersionUID = -3487033658917383329L;

	Logger logger = Logger.getLogger(EventsManager.class);

	@Override
	public void raiseAddEvent(ObjectType objectType, Action action) throws EwidAddException {
		logger.info("EventsManager.raiseAddEvent");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into events (object_type_id, action_id, event_date) values " +
					"(?, ?, now())";
			logger.debug("EventsManager.raiseAddEvent sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, objectType.getId());
			stmt.setInt(2, action.getId());
			
			stmt.execute();

		} catch (Exception e) {
			logger.error("EventsManager.raiseAddEvent error", e);
			throw new EwidAddException("EventsManager.raiseAddEvent error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}
}
