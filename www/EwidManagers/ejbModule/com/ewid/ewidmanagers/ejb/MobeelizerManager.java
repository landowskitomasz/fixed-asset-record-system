package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ewid.ewidmanagers.client.ejb.remote.MobeelizerManagerRemote;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
public class MobeelizerManager implements /* MobeelizerManagerLocal,*/ MobeelizerManagerRemote, Serializable {

	private static final long serialVersionUID = -5774263983622287881L;

	private Logger logger = Logger.getLogger(MobeelizerManager.class);
	
	@Override
	public List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.User> getUsersToSync() throws EwidGetListException {
		logger.info("MobeelizerManager.getUsersToSync");
		
		List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.User> resultList = 
				new ArrayList<com.ewid.ewidmanagers.client.struct.mobeelizer.model.User>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, guid, login, name, surname, password from users";
			logger.debug("MobeelizerManager.getUsersToSync sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				com.ewid.ewidmanagers.client.struct.mobeelizer.model.User user = 
						new com.ewid.ewidmanagers.client.struct.mobeelizer.model.User();
				
				user.setId(rs.getInt("id"));
				user.setGuid(rs.getString("guid"));
				user.setLogin(rs.getString("login"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setPassword(rs.getString("password"));
				
				resultList.add(user);
			}
		} catch (Exception e) {
			logger.error("MobeelizerManager.getUsersToSync error", e);
			throw new EwidGetListException("MobeelizerManager.getUsersToSync error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping> getMappingsToSync() throws EwidGetListException {
		// TODO: funkcjonalnosc nie potrzebna!
		return null;
	}

	@Override
	public List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place> getPlacesToSync() throws EwidGetListException {
		logger.info("MobeelizerManager.getPlacesToSync");
		
		List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place> resultList = 
				new ArrayList<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, guid, country, province, city, postal_code, street, building, floor, room_number from places";
			logger.debug("MobeelizerManager.getPlacesToSync sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place place = 
						new com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place();
				
				place.setId(rs.getInt("id"));
				place.setGuid(rs.getString("guid"));
				place.setCountry(rs.getString("country"));
				place.setProvince(rs.getString("province"));
				place.setCity(rs.getString("city"));
				place.setPostalCode(rs.getString("postal_code"));
				place.setStreet(rs.getString("street"));
				place.setBuilding(rs.getString("building"));
				place.setFloor(rs.getInt("floor"));
				place.setRoomNumber(rs.getString("room_number"));
				
				resultList.add(place);
			}
		} catch (Exception e) {
			logger.error("MobeelizerManager.getPlacesToSync error", e);
			throw new EwidGetListException("MobeelizerManager.getPlacesToSync error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee> getEmployeesToSync() throws EwidGetListException {
		logger.info("MobeelizerManager.getEmployeesToSync");
		
		List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee> resultList = 
				new ArrayList<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, guid, name, surname, date_of_birth, place_of_birth, pesel, email from employees";
			logger.debug("MobeelizerManager.getEmployeesToSync sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee employee = 
						new com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee();
				
				employee.setId(rs.getInt("id"));
				employee.setGuid(rs.getString("guid"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setDateOfBirth(rs.getTimestamp("date_of_birth"));
				employee.setPlaceOfBirth(rs.getString("place_of_birth"));
				employee.setPesel(rs.getString("pesel"));
				employee.setEmail(rs.getString("email"));
				
				resultList.add(employee);
			}
		} catch (Exception e) {
			logger.error("MobeelizerManager.getEmployeesToSync error", e);
			throw new EwidGetListException("MobeelizerManager.getEmployeesToSync error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment> getEquipmentToSync() throws EwidGetListException {
		logger.info("MobeelizerManager.getEquipmentToSync");
		
		List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment> resultList = 
				new ArrayList<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, brand, model, serial_number, guid from equipment";
			logger.debug("MobeelizerManager.getEquipmentToSync sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment equipment = 
						new com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment();
				
				equipment.setId(rs.getInt("id"));
				equipment.setGuid(rs.getString("guid"));
				equipment.setBrand(rs.getString("brand"));
				equipment.setModel(rs.getString("model"));
				equipment.setSerialNumer(rs.getString("serial_number"));
								
				resultList.add(equipment);
			}
		} catch (Exception e) {
			logger.error("MobeelizerManager.getEquipmentToSync error", e);
			throw new EwidGetListException("MobeelizerManager.getEquipmentToSync error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public void addMappingsToDb(List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping> mappingToAdd) throws EwidAddException {
		
		logger.info("MobeelizerManager.addMappingsToDb mappingToAdd=" + mappingToAdd);
		
		Map<String, String> userPlaceMap = 
				new HashMap<String, String>(); //only place & employee
		
		List<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping> finalMappings = 
				new ArrayList<com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping>();
		
		if(mappingToAdd != null) {
			for(com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping mapping : mappingToAdd) {
				if(mapping.getPlace() != null && mapping.getEmployee() != null) {
					userPlaceMap.put(mapping.getEmployee(), mapping.getPlace());
				}
			}
			for(com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping mapping : mappingToAdd) {
				if(mapping.getEmployee() != null && mapping.getEquipment() != null) {
					com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping mapToAdd = new com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping();
					mapToAdd.setCreatedBy(mapping.getCreatedBy());
					mapToAdd.setCreationDate(mapping.getCreationDate());
					mapToAdd.setEmployee(mapping.getEmployee());
					mapToAdd.setEquipment(mapping.getEquipment());
					mapToAdd.setPlace(userPlaceMap.get(mapping.getEmployee()));
					finalMappings.add(mapToAdd);
				}
			}
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
				
			if(finalMappings != null) {
			
				for (com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping mapping : finalMappings) {
				
					String sql = "select 1 from mappings where employee_id = (select id from employees where guid = ?) and " +
						"equipment_id = (select id from equipment where guid = ?) and " +
						"place_id = (select id from places where guid = ?)";//sprawdzam czy mapowanie jest juz w bazie
					
					logger.info("MobeelizerManager.addMappingsToDb sql=" + sql);
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, mapping.getEmployee());
					stmt.setString(2, mapping.getEquipment());
					stmt.setString(3, mapping.getPlace());
					rs = stmt.executeQuery();
					
					if(rs.next()) { //jesli jest to update
						sql = "update mappings set employee_id = (select id from employees where guid = ?), " +
						"equipment_id = (select id from equipment where guid = ?), " +
						"place_id = (select id from places where guid = ?), " +
						"created_by = ? " +
						//"creation_date = ? " +
						"where guid = ?";
						
						logger.info("MobeelizerManager.addMappingsToDb sql=" + sql);
						
						stmt = conn.prepareStatement(sql);
						
						stmt.setString(1, mapping.getEmployee());
						stmt.setString(2, mapping.getEquipment());
						stmt.setString(3, mapping.getPlace());
						stmt.setInt(4, mapping.getCreatedBy());
						//stmt.setDate(5, mapping.getCreationDate());
						stmt.setString(5, mapping.getGuid());
					}
					else { //jesli nie ma to sprawdzam czy jest jest w bazie pracownik, sprzet i miejsce wymienione w mapowaniu
						sql = "select id from employees where guid = ? " +
						"union all " +
						"select id from places where guid = ? " +
						"union all " +
						"select id from equipment where guid = ? " +
						"union all " +
						"select id from users where id = ? ";
						
						logger.info("MobeelizerManager.addMappingsToDb sql=" + sql);
						
						stmt = conn.prepareStatement(sql);
						
						stmt.setString(1, mapping.getEmployee());
						stmt.setString(2, mapping.getPlace());
						stmt.setString(3, mapping.getEquipment());
						stmt.setInt(4, mapping.getCreatedBy());
						
						rs = stmt.executeQuery();
						
						int count = 0;
						while (rs.next()) {
							++count;
						}
						
						if(count == 4) { //dodaj mapowanie
							sql = "insert into mappings (guid, employee_id, equipment_id, place_id, created_by, creation_date) values " +
									"(?, (select id from employees where guid = ?), (select id from equipment where guid = ?), " +
									"(select id from places where guid = ?), ?, now())";
							
							logger.info("MobeelizerManager.addMappingsToDb sql=" + sql);
							
							stmt = conn.prepareStatement(sql);
							
							stmt.setString(1, mapping.getGuid());
							stmt.setString(2, mapping.getEmployee());
							stmt.setString(3, mapping.getEquipment());
							stmt.setString(4, mapping.getPlace());
							stmt.setInt(5, mapping.getCreatedBy());
							
							stmt.execute();
						}
						else { 
							//nie rob nic!
						}
						
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("MobeelizerManager.addMappingsToDb error", e);
			throw new EwidGetListException("MobeelizerManager.addMappingsToDb error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
	}
	

}
