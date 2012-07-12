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

import com.ewid.ewidmanagers.client.ejb.local.EquipmentManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = EquipmentManagerLocal.JNDI_NAME)
public class EquipmentManager implements EquipmentManagerLocal, Serializable {

	private static final long serialVersionUID = -7404930263034736568L;

	private Logger logger = Logger.getLogger(EquipmentManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<Equipment> getEquipment() throws EwidGetListException {
		logger.info("EquipmentManager.getEquipments");
		
		List<Equipment> resultList = new ArrayList<Equipment>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, brand, model, serial_number, synchronized from equipment";
			logger.debug("EquipmentManager.getEquipments sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Equipment equipment = new Equipment();
				
				equipment.setId(rs.getInt("id"));
				equipment.setBrand(rs.getString("brand"));
				equipment.setModel(rs.getString("model"));
				equipment.setSerialNumber(rs.getString("serial_number"));
				equipment.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
				
				resultList.add(equipment);
			}
		} catch (Exception e) {
			logger.error("EquipmentManager.getEquipments error", e);
			throw new EwidGetListException("EquipmentManager.getEquipments error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}
	
	@Override
	public List<Equipment> getNotUsedInMappingEquipment() throws EwidGetListException {
		logger.info("EquipmentManager.getNotUsedInMappingEquipment");
		
		List<Equipment> resultList = new ArrayList<Equipment>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, brand, model, serial_number, synchronized from equipment where id not in (select equipment_id from mappings)";
			logger.debug("EquipmentManager.getNotUsedInMappingEquipment sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Equipment equipment = new Equipment();
				
				equipment.setId(rs.getInt("id"));
				equipment.setBrand(rs.getString("brand"));
				equipment.setModel(rs.getString("model"));
				equipment.setSerialNumber(rs.getString("serial_number"));
				equipment.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
				
				resultList.add(equipment);
			}
		} catch (Exception e) {
			logger.error("EquipmentManager.getNotUsedInMappingEquipment error", e);
			throw new EwidGetListException("EquipmentManager.getNotUsedInMappingEquipment error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public Equipment getEquipmentById(int equipmentId) throws EwidGetObjectException {
		logger.info("EquipmentManager.getEquipmentById equipmentId=" + equipmentId);
		
		Equipment resultEquipment = new Equipment();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, brand, model, serial_number, synchronized from equipment where id = ?";
			logger.debug("EquipmentManager.getEquipmentById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, equipmentId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultEquipment.setId(rs.getInt("id"));
				resultEquipment.setBrand(rs.getString("brand"));
				resultEquipment.setModel(rs.getString("model"));
				resultEquipment.setSerialNumber(rs.getString("serial_number"));
				resultEquipment.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
			}
		} catch (Exception e) {
			logger.error("EquipmentManager.getEquipmentById error", e);
			throw new EwidGetObjectException("EquipmentManager.getEquipmentById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultEquipment;
	}

	@Override
	public void addEquipment(Equipment equipment) throws EwidAddException {
		logger.info("EquipmentManager.addEquipment equipment=" + equipment);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into equipment (brand, model, serial_number, synchronized, guid) values (?,?,?,0,?)";
			logger.debug("EquipmentManager.addEquipment sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, equipment.getBrand());
			stmt.setString(2, equipment.getModel());
			stmt.setString(3, equipment.getSerialNumber());
			stmt.setString(4, UUID.randomUUID().toString());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EQUIPMENT, Action.ADD);
		} catch (Exception e) {
			logger.error("EquipmentManager.addEquipment error", e);
			throw new EwidAddException("EquipmentManager.addEquipment error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void editEquipment(Equipment equipment) throws EwidEditException {
		logger.info("EquipmentManager.editEquipment equipment=" + equipment);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update equipment set brand = ?, model = ?, serial_number = ?, synchronized = 0 where id = ?";
			logger.debug("EquipmentManager.editEquipment sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, equipment.getBrand());
			stmt.setString(2, equipment.getModel());
			stmt.setString(3, equipment.getSerialNumber());
			stmt.setInt(4, equipment.getId());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EQUIPMENT, Action.EDIT);
		} catch (Exception e) {
			logger.error("EquipmentManager.editEquipment error", e);
			throw new EwidEditException("EquipmentManager.editEquipment error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void deleteEquipment(int equipmentId) throws EwidDeleteException {
		logger.info("EquipmentManager.deleteEquipment equipmentId=" + equipmentId);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "delete from equipment where id = ?";
			logger.debug("EquipmentManager.deleteEquipment sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, equipmentId);
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.EQUIPMENT, Action.DELETE);
		} catch (Exception e) {
			logger.error("EquipmentManager.deleteEquipment error", e);
			throw new EwidDeleteException("EquipmentManager.deleteEquipment error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public List<Equipment> getNotUsedInRecordEquipment() throws EwidGetListException {
		
		logger.info("EquipmentManager.getNotUsedInRecordEquipment");
		
		List<Equipment> resultList = new ArrayList<Equipment>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, brand, model, serial_number, synchronized from equipment where id not in (select equipment_id from fixed_assets)";
			logger.debug("EquipmentManager.getNotUsedInRecordEquipment sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Equipment equipment = new Equipment();
				
				equipment.setId(rs.getInt("id"));
				equipment.setBrand(rs.getString("brand"));
				equipment.setModel(rs.getString("model"));
				equipment.setSerialNumber(rs.getString("serial_number"));
				equipment.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
				
				resultList.add(equipment);
			}
		} catch (Exception e) {
			logger.error("EquipmentManager.getNotUsedInRecordEquipment error", e);
			throw new EwidGetListException("EquipmentManager.getNotUsedInRecordEquipment error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}
}
