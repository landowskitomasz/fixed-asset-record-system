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

import com.ewid.ewidmanagers.client.ejb.local.EmployeesManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Employee;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = EmployeesManagerLocal.JNDI_NAME)
public class EmployeesManager implements EmployeesManagerLocal, Serializable{

	private static final long serialVersionUID = -782659348736066608L;

	private Logger logger = Logger.getLogger(EmployeesManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<Employee> getEmployees() throws EwidGetListException {
		logger.info("EmployeesManager.getEmployees");
		
		List<Employee> resultList = new ArrayList<Employee>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name, surname, date_of_birth, place_of_birth, pesel, email, msisdn, synchronized from employees";
			logger.debug("PlacesManager.getEmployees sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setDateOfBirth(rs.getTimestamp("date_of_birth"));
				employee.setPlaceOfBirth(rs.getString("place_of_birth"));
				employee.setPesel(rs.getString("pesel"));
				employee.setEmail(rs.getString("email"));
				employee.setMsisdn(rs.getLong("msisdn"));
				employee.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
				
				resultList.add(employee);
			}
		} catch (Exception e) {
			logger.error("EmployeesManager.getEmployees error", e);
			throw new EwidGetListException("EmployeesManager.getEmployees error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public void addEmployee(Employee employee) throws EwidAddException {
		logger.info("EmployeesManager.addEmployee");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into employees (name, surname, date_of_birth, place_of_birth, pesel, email, msisdn, synchronized, guid) " +
					"values (?,?,?,?, ?,?,?,0, ?)";
			logger.debug("PlacesManager.addEmployee sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, employee.getName());
			stmt.setString(2, employee.getSurname());
			if(employee.getDateOfBirth() != null) {
				java.sql.Date date = new java.sql.Date(employee.getDateOfBirth().getTime());
				stmt.setDate(3, date);
			}
			else {
				stmt.setDate(3, null);
			}
			stmt.setString(4, employee.getPlaceOfBirth());
			stmt.setString(5, employee.getPesel());
			stmt.setString(6, employee.getEmail());
			stmt.setLong(7, employee.getMsisdn());
			stmt.setString(8, UUID.randomUUID().toString());
			
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EMPLOYEE, Action.ADD);
		} catch (Exception e) {
			logger.error("EmployeesManager.addEmployee error", e);
			throw new EwidAddException("EmployeesManager.addEmployee error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void deleteEmployee(int employeeId) throws EwidDeleteException {
		logger.info("EmployeesManager.deleteEmployee employeeId=" + employeeId);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "delete from employees where id = ?";
			logger.debug("PlacesManager.deleteEmployee sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, employeeId);
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EMPLOYEE, Action.DELETE);
		} catch (Exception e) {
			logger.error("EmployeesManager.deleteEmployee error", e);
			throw new EwidDeleteException("EmployeesManager.deleteEmployee error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void editEmployee(Employee employee) throws EwidEditException {
		logger.info("EmployeesManager.editEmployee employee=" + employee);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update employees set name=?, surname=?, date_of_birth=?, place_of_birth=?, pesel=?, email=?, msisdn=?, synchronized=0 where id = ?";
			logger.debug("PlacesManager.editEmployee sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, employee.getName());
			stmt.setString(2, employee.getSurname());
			if(employee.getDateOfBirth() != null) {
				java.sql.Date date = new java.sql.Date(employee.getDateOfBirth().getTime());
				stmt.setDate(3, date);
			}
			else {
				stmt.setDate(3, null);
			}
			stmt.setString(4, employee.getPlaceOfBirth());
			stmt.setString(5, employee.getPesel());
			stmt.setString(6, employee.getEmail());
			stmt.setLong(7, employee.getMsisdn());
			stmt.setInt(8, employee.getId());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EMPLOYEE, Action.EDIT);
		} catch (Exception e) {
			logger.error("EmployeesManager.editEmployee error", e);
			throw new EwidEditException("EmployeesManager.editEmployee error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) throws EwidGetObjectException {
		logger.info("EmployeesManager.getEmployeeById employeeId=" + employeeId);
		
		Employee resultEmployee = new Employee();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name, surname, date_of_birth, place_of_birth, pesel, email, msisdn, synchronized from employees where id = ?";
			logger.debug("PlacesManager.getEmployeeById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, employeeId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultEmployee.setId(rs.getInt("id"));
				resultEmployee.setName(rs.getString("name"));
				resultEmployee.setSurname(rs.getString("surname"));
				resultEmployee.setDateOfBirth(rs.getTimestamp("date_of_birth"));
				resultEmployee.setPlaceOfBirth(rs.getString("place_of_birth"));
				resultEmployee.setPesel(rs.getString("pesel"));
				resultEmployee.setEmail(rs.getString("email"));
				resultEmployee.setMsisdn(rs.getLong("msisdn"));
				resultEmployee.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
			}
		} catch (Exception e) {
			logger.error("EmployeesManager.getEmployeeById error", e);
			throw new EwidGetListException("EmployeesManager.getEmployeeById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultEmployee;
	}
}
