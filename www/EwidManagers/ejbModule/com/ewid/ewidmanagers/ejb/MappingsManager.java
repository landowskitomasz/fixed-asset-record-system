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
import org.jboss.ejb3.annotation.RemoteBinding;

import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.MappingsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.remote.MappingsManagerRemote;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Employee;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidmanagers.client.struct.Mapping;
import com.ewid.ewidmanagers.client.struct.Place;
import com.ewid.ewidmanagers.client.struct.User;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = MappingsManagerLocal.JNDI_NAME)
public class MappingsManager implements MappingsManagerLocal, Serializable {

	private static final long serialVersionUID = -5417167888146462342L;

	private Logger logger = Logger.getLogger(MappingsManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<Mapping> getMappings() throws EwidGetListException {
		logger.info("MappingsManager.getMappings");
		
		List<Mapping> resultList = new ArrayList<Mapping>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select m.id, m.creation_date, m.last_modify, m.synchronized, u.id, u.name, u.surname, u.login, u2.id, u2.name, u2.surname, u2.login," +
					"e.id, e.brand, e.model, e.serial_number, emp.id, emp.name, emp.surname, p.id, p.country, p.province, p.city, p.street, p.building, p.floor, p.room_number " +
					"from mappings m inner join equipment e on (m.equipment_id=e.id) " +
					"inner join employees emp on (m.employee_id=emp.id) inner join places p on (m.place_id=p.id) left outer join users u on (m.created_by=u.id) " +
					"left outer join users u2 on (m.modify_by=u2.id)";
			logger.debug("MappingsManager.getMappings sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Mapping mapping = new Mapping();
				
				mapping.setId(rs.getInt("m.id"));
				mapping.setCreationDate(rs.getDate("m.creation_date"));
				mapping.setLastModify(rs.getDate("m.last_modify"));
				mapping.setSynchronizedWithMobeelizer(rs.getBoolean("m.synchronized"));
				
				User creator = new User();
				creator.setId(rs.getInt("u.id"));
				creator.setName(rs.getString("u.name"));
				creator.setSurname(rs.getString("u.surname"));
				creator.setLogin(rs.getString("u.login"));
				mapping.setCreatedBy(creator);
				
				User editor = new User();
				editor.setId(rs.getInt("u2.id"));
				editor.setName(rs.getString("u2.name"));
				editor.setSurname(rs.getString("u2.surname"));
				editor.setLogin(rs.getString("u2.login"));
				mapping.setModifyBy(editor);
				
				Employee employee = new Employee();
				employee.setId(rs.getInt("emp.id"));
				employee.setName(rs.getString("emp.name"));
				employee.setSurname(rs.getString("emp.surname"));
				mapping.setEmployee(employee);
				
				Equipment equipment = new Equipment();
				equipment.setId(rs.getInt("e.id"));
				equipment.setBrand(rs.getString("e.brand"));
				equipment.setModel(rs.getString("e.model"));
				equipment.setSerialNumber(rs.getString("e.serial_number"));
				mapping.setEquipment(equipment);
				
				Place place = new Place();
				place.setId(rs.getInt("p.id"));
				place.setCountry(rs.getString("p.country"));
				place.setProvince(rs.getString("p.province"));
				place.setCity(rs.getString("p.city"));
				place.setStreet(rs.getString("p.street"));
				place.setBuilding(rs.getString("p.building"));
				place.setFloor(rs.getInt("p.floor"));
				place.setRoomNumber(rs.getString("p.room_number"));
				mapping.setPlace(place);
				
				resultList.add(mapping);
			}
		} catch (Exception e) {
			logger.error("MappingsManager.getMappings error", e);
			throw new EwidGetListException("MappingsManager.getMappings error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public void addMapping(Mapping mapping) throws EwidAddException {
		logger.info("MappingsManager.addMapping mapping=" + mapping);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into mappings (created_by, creation_date, modify_by, last_modify, synchronized, " +
					"employee_id, equipment_id, place_id, guid) values (?,now(),?,now(), 0,?,?,?,?)";
			logger.debug("MappingsManager.addMapping sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mapping.getCreatedBy().getId());
			stmt.setInt(2, mapping.getModifyBy().getId());
			stmt.setInt(3, mapping.getEmployee().getId());
			stmt.setInt(4, mapping.getEquipment().getId());
			stmt.setInt(5, mapping.getPlace().getId());
			stmt.setString(6, UUID.randomUUID().toString());
			
			stmt.executeUpdate();
			
			eventsManager.raiseAddEvent(ObjectType.MAPPING, Action.ADD);
		} catch (Exception e) {
			logger.error("MappingsManager.addMapping error", e);
			throw new EwidAddException("MappingsManager.addMapping error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void deleteMapping(int mappingId) throws EwidDeleteException {
		logger.info("MappingsManager.deleteMapping mappingId=" + mappingId);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "delete from mappings where id = ?";
			logger.debug("MappingsManager.deleteMapping sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mappingId);
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.MAPPING, Action.DELETE);
		} catch (Exception e) {
			logger.error("MappingsManager.deleteMapping error", e);
			throw new EwidDeleteException("MappingsManager.deleteMapping error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void editMapping(Mapping mapping) throws EwidEditException {
		logger.info("MappingsManager.editMapping mapping=" + mapping);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update mappings set modify_by=?, last_modify=now(), synchronized=0, " +
					"employee_id = ?, equipment_id = ?, place_id = ? where id = ?";
			logger.debug("MappingsManager.editMapping sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mapping.getModifyBy().getId());
			stmt.setInt(2, mapping.getEmployee().getId());
			stmt.setInt(3, mapping.getEquipment().getId());
			stmt.setInt(4, mapping.getPlace().getId());
			stmt.setInt(5, mapping.getId());
			stmt.executeUpdate();
			
			eventsManager.raiseAddEvent(ObjectType.MAPPING, Action.EDIT);
		} catch (Exception e) {
			logger.error("MappingsManager.editMapping error", e);
			throw new EwidEditException("MappingsManager.editMapping error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void getMappingById(int mappingId) throws EwidGetObjectException {
		logger.info("MappingsManager.getMappingById mappingId=" + mappingId);
		
		Mapping mapping = new Mapping();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select m.id, m.creation_date, m.last_modify, m.synchronized, u.id, u.name, u.surname, u.login, u2.id, u2.name, u2.surname, u2.login," +
					"e.id, e.brand, e.model, e.serial_number, emp.id, emp.name, emp.surname, p.id, p.country, p.province, p.city " +
					"from mappings m inner join equipment e on (m.equipment_id=e.id) " +
					"inner join employees emp on (m.employee_id=emp.id) inner join places p on (m.place_id=p.id) left outer join users u on (m.created_by=u.id) " +
					"left outer join users u2 on (m.modify_by=u2.id) where m.id = ?";
			logger.debug("MappingsManager.getMappingById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mappingId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				mapping.setId(rs.getInt("m.id"));
				mapping.setCreationDate(rs.getDate("m.creation_date"));
				mapping.setLastModify(rs.getDate("m.last_modify"));
				mapping.setSynchronizedWithMobeelizer(rs.getBoolean("m.synchronized"));
				
				User creator = new User();
				creator.setId(rs.getInt("u.id"));
				creator.setName(rs.getString("u.name"));
				creator.setSurname(rs.getString("u.surname"));
				creator.setLogin(rs.getString("u.login"));
				mapping.setCreatedBy(creator);
				
				User editor = new User();
				editor.setId(rs.getInt("u2.id"));
				editor.setName(rs.getString("u2.name"));
				editor.setSurname(rs.getString("u2.surname"));
				editor.setLogin(rs.getString("u2.login"));
				mapping.setModifyBy(editor);
				
				Employee employee = new Employee();
				employee.setId(rs.getInt("emp.id"));
				employee.setName(rs.getString("emp.name"));
				employee.setSurname(rs.getString("emp.surname"));
				mapping.setEmployee(employee);
				
				Equipment equipment = new Equipment();
				equipment.setId(rs.getInt("e.id"));
				equipment.setBrand(rs.getString("e.brand"));
				equipment.setModel(rs.getString("e.model"));
				equipment.setSerialNumber(rs.getString("e.serial_number"));
				mapping.setEquipment(equipment);
				
				Place place = new Place();
				place.setId(rs.getInt("p.id"));
				place.setCountry(rs.getString("p.country"));
				place.setProvince(rs.getString("p.province"));
				place.setCity(rs.getString("p.city"));
				mapping.setPlace(place);
				
			}
		} catch (Exception e) {
			logger.error("MappingsManager.getMappingById error", e);
			throw new EwidGetObjectException("MappingsManager.getMappingById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
	}

}
