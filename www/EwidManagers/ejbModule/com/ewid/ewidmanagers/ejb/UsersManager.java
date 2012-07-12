package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Group;
import com.ewid.ewidmanagers.client.struct.User;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = UsersManagerLocal.JNDI_NAME)
public class UsersManager implements UsersManagerLocal, Serializable {

	private static final long serialVersionUID = -4016690131125804306L;

	private Logger logger = Logger.getLogger(UsersManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<User> getUsers() throws EwidGetListException {
		logger.info("UsersManager.getUsers");
		
		List<User> resultList = new ArrayList<User>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select u.id as user_id, u.login as user_login, u.name as user_name, " +
					"u.surname as user_surname, u.creation_date as user_creation_date, " +
					"u.active as user_active, u.password as user_password, u.group_id , " +
					"u.synchronized as user_synchronized, g.name as group_name, " +
					"g.active as group_active from users u inner join groups g on (u.group_id = g.id)";
			logger.debug("UsersManager.getUsers sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				
				user.setId(rs.getInt("user_id"));
				user.setLogin(rs.getString("user_login"));
				user.setName(rs.getString("user_name"));
				user.setSurname(rs.getString("user_surname"));
				user.setCreationDate(rs.getTimestamp("user_creation_date"));
				user.setActive(rs.getBoolean("user_active"));
				user.setPassword(rs.getString("user_password"));
				
				Group group = new Group();
				group.setId(rs.getInt("group_id"));
				group.setName(rs.getString("group_name"));
				group.setActive(rs.getBoolean("group_active"));
							
				user.setGroup(group);
				user.setSynchronizedWithMobeelizer(rs.getBoolean("user_synchronized"));
				
				resultList.add(user);
			}
		} catch (Exception e) {
			logger.error("UsersManager.getUsers error", e);
			throw new EwidGetListException("UsersManager.getUsers error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public void addUser(User user) throws EwidAddException {
		logger.info("UsersManager.addUser user: " + user);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			
			String sql = "insert into users (login, name, surname, creation_date, active, password, group_id, synchronized, guid) values" +
					"(?,?,?,now(), 1,?,?,0, ?)";
			logger.debug("UsersManager.addUser sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getSurname());
			stmt.setString(4, user.getPassword());
			stmt.setInt(5, user.getGroup().getId());
			stmt.setString(6, UUID.randomUUID().toString());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.USER, Action.ADD);
		} catch (Exception e) {
			logger.error("UsersManager.addUser error", e);
			throw new EwidAddException("UsersManager.addUser error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void deleteUser(int userId) throws EwidDeleteException {
		logger.info("UsersManager.deleteUser userId: " + userId);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			
			String sql = "delete from users where id = ?";
			logger.debug("UsersManager.deleteUser sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.USER, Action.DELETE);
		} catch (Exception e) {
			logger.error("UsersManager.deleteUser error", e);
			throw new EwidDeleteException("UsersManager.deleteUser error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void editUser(User user) throws EwidEditException {
		logger.info("UsersManager.editUser user: " + user);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update users set login=?, name=?, surname=?, password=?, " +
					"group_id=?, synchronized=0 where id = ?";
			logger.debug("UsersManager.editUser sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getSurname());
			stmt.setString(4, user.getPassword());
			stmt.setInt(5, user.getGroup().getId());
			stmt.setInt(6, user.getId());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.USER, Action.EDIT);
		} catch (Exception e) {
			logger.error("UsersManager.editUser error", e);
			throw new EwidEditException("UsersManager.editUser error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public User getUserById(int userId) throws EwidGetObjectException {
		logger.info("UsersManager.getUserById");
		
		User resultUser = new User();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select u.id as user_id, u.login as user_login, u.name as user_name, " +
					"u.surname as user_surname, u.creation_date as user_creation_date, " +
					"u.active as user_active, u.password as user_password, u.group_id , " +
					"u.synchronized as user_synchronized, g.name as group_name, " +
					"g.active as group_active from users u inner join groups g on (u.group_id = g.id) where u.id = ?";
			logger.debug("UsersManager.getUserById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultUser.setId(rs.getInt("user_id"));
				resultUser.setLogin(rs.getString("user_login"));
				resultUser.setName(rs.getString("user_name"));
				resultUser.setSurname(rs.getString("user_surname"));
				resultUser.setCreationDate(rs.getTimestamp("user_creation_date"));
				resultUser.setActive(rs.getBoolean("user_active"));
				resultUser.setPassword(rs.getString("user_password"));
				
				Group group = new Group();
				group.setId(rs.getInt("group_id"));
				group.setName(rs.getString("group_name"));
				group.setActive(rs.getBoolean("group_active"));
							
				resultUser.setGroup(group);
				resultUser.setSynchronizedWithMobeelizer(rs.getBoolean("user_synchronized"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getUserById error", e);
			throw new EwidGetObjectException("UsersManager.getUserById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultUser;
	}
	
	public List<Group> getGroups() throws EwidGetListException {
		logger.info("UsersManager.getGroups");
		
		List<Group> resultList = new ArrayList<Group>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name, active from groups";
			logger.debug("UsersManager.getGroups sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Group group = new Group();
				
				group.setId(rs.getInt("id"));
				group.setName(rs.getString("name"));
				group.setActive(rs.getBoolean("active"));
				
				resultList.add(group);
			}
		} catch (Exception e) {
			logger.error("UsersManager.getGroups error", e);
			throw new EwidGetListException("UsersManager.getGroups error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	public User getUserByLogin(String login) throws EwidGetObjectException {
		logger.info("UsersManager.getUserByLogin login=" + login);
		
		User resultUser = new User();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select u.id as user_id, u.login as user_login, u.name as user_name, " +
					"u.surname as user_surname, u.creation_date as user_creation_date, " +
					"u.active as user_active, u.password as user_password, u.group_id , " +
					"u.synchronized as user_synchronized, g.name as group_name, " +
					"g.active as group_active from users u inner join groups g on (u.group_id = g.id) where u.login = ?";
			logger.debug("UsersManager.getUserByLogin sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, login);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultUser.setId(rs.getInt("user_id"));
				resultUser.setLogin(rs.getString("user_login"));
				resultUser.setName(rs.getString("user_name"));
				resultUser.setSurname(rs.getString("user_surname"));
				resultUser.setCreationDate(rs.getTimestamp("user_creation_date"));
				resultUser.setActive(rs.getBoolean("user_active"));
				resultUser.setPassword(rs.getString("user_password"));
				
				Group group = new Group();
				group.setId(rs.getInt("group_id"));
				group.setName(rs.getString("group_name"));
				group.setActive(rs.getBoolean("group_active"));
							
				resultUser.setGroup(group);
				resultUser.setSynchronizedWithMobeelizer(rs.getBoolean("user_synchronized"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getUserByLogin error", e);
			throw new EwidGetObjectException("UsersManager.getUserByLogin error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultUser;
	}

	@Override
	public Group getGroupById(int groupId) throws EwidGetObjectException {
		logger.info("UsersManager.getGroupById groupId=" + groupId);
		
		Group resultGroup = new Group();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name, active from groups where id = ?";
			logger.debug("UsersManager.getGroupById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, groupId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultGroup.setId(rs.getInt("id"));
				resultGroup.setName(rs.getString("name"));
				resultGroup.setActive(rs.getBoolean("active"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getGroupById error", e);
			throw new EwidGetObjectException("UsersManager.getGroupById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultGroup;
	}
	
	@Override
	public List<String> getRoles() throws EwidGetListException {
		logger.info("UsersManager.getRoles");
		
		List<String> resultList = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("select role from roles");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(rs.getString("role"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getRoles error", e);
			throw new EwidGetListException("UsersManager.getRoles error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;	
	}
	
	@Override
	public List<String> getRolesForGroup(int groupId) throws EwidGetListException {
		logger.info("UsersManager.getRolesForGroup groupId=" + groupId);
		
		List<String> resultList = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("select role from groups_roles where group_id = ?");
			stmt.setInt(1, groupId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(rs.getString("role"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getRoles error", e);
			throw new EwidGetListException("UsersManager.getRoles error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;	
	}

	@Override
	public void addRoleToGroup(Group group, String role) throws EwidAddException {
		logger.info("UsersManager.addRoleToGroup group=" + group + ", role=" + role);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("insert into groups_roles (group_id, role) values (?,?)");
			stmt.setInt(1, group.getId());
			stmt.setString(2, role);
			stmt.execute();
			
		} catch (Exception e) {
			logger.error("UsersManager.addRoleToGroup error", e);
			throw new EwidGetListException("UsersManager.addRoleToGroup error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void removeRoleFromGroup(Group group, String role) throws EwidDeleteException {
		logger.info("UsersManager.removeRoleFromGroup group=" + group + ", role=" + role);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("delete from groups_roles where group_id = ? and role = ?");
			stmt.setInt(1, group.getId());
			stmt.setString(2, role);
			stmt.execute();
			
		} catch (Exception e) {
			logger.error("UsersManager.removeRoleFromGroup error", e);
			throw new EwidGetListException("UsersManager.removeRoleFromGroup error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public boolean checkIfLoginEmpty(String login) {
		logger.info("UsersManager.checkIfLoginEmpty login=" + login);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("select 1 from users where login = ?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return false;
			}
			return true;
			
		} catch (Exception e) {
			logger.error("UsersManager.checkIfLoginEmpty error", e);
			throw new EwidGetListException("UsersManager.checkIfLoginEmpty error",e);
		} finally {
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
			DbUtils.closeConnection(conn);
		}
	}

	@Override
	public List<String> getFreeRolesForGroup(int groupId) throws EwidGetListException {
		logger.info("UsersManager.getFreeRolesForGroup groupId=" + groupId);
		
		List<String> resultList = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			stmt = conn.prepareStatement("select role from roles where role not in (select role from groups_roles where group_id = ?)");
			stmt.setInt(1, groupId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(rs.getString("role"));
			}
		} catch (Exception e) {
			logger.error("UsersManager.getFreeRolesForGroup error", e);
			throw new EwidGetListException("UsersManager.getFreeRolesForGroup error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;	
	}
}
