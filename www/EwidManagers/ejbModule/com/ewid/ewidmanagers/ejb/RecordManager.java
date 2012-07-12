package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.RecordManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidmanagers.client.struct.FixedAssetClassSymbol;
import com.ewid.ewidmanagers.client.struct.PurchaseDocument;
import com.ewid.ewidmanagers.client.struct.Record;
import com.ewid.ewidmanagers.client.struct.User;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = RecordManagerLocal.JNDI_NAME)
public class RecordManager implements RecordManagerLocal, Serializable {

	private static final long serialVersionUID = -2734800323297505695L;

	private Logger logger = Logger.getLogger(RecordManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<Record> getRecords() throws EwidGetListException {
		logger.info("RecordManager.getRecords");
		
		List<Record> resultList = new ArrayList<Record>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select f_a.id, e.id, e.brand, e.model, e.serial_number, f_a.purchase_date, f_a.first_use_date, f_a.purchase_document_id, f_a.description, " +
					"f_a.fixed_assets_classification_symbol_id, f_a.initial_value, f_a.amortization_rate, f_a.deduction_amount, " +
					"f_a.updated_initial_value,	f_a.updated_deduction_amount, f_a.liquidation_date, f_a.liquidation_couse, " +
					"f_a.creation_date, u.id, u.name, u.surname, u.login, f_a.last_modify, u2.id, u2.name, u2.surname, u2.login, " +
					"f_s.id, f_s.name, p_d.id, p_d.name " +
					"from fixed_assets f_a inner join equipment e on (f_a.equipment_id=e.id) " +
					"left outer join users u on (f_a.created_by=u.id) left outer join users u2 on (f_a.modify_by=u2.id) " +
					"left outer join fixed_assets_classification_symbols f_s on (f_a.fixed_assets_classification_symbol_id=f_s.id) " +
					"left outer join purchase_documents p_d on (f_a.purchase_document_id=p_d.id)";
			logger.debug("RecordManager.getRecords sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Record record = new Record();
				
				record.setId(rs.getInt("f_a.id"));
				
				Equipment equipment = new Equipment();
				equipment.setId(rs.getInt("e.id"));
				equipment.setBrand(rs.getString("e.brand"));
				equipment.setModel(rs.getString("e.model"));
				equipment.setSerialNumber(rs.getString("e.serial_number"));
				record.setEquipment(equipment);
				
				record.setPurchaseDate(rs.getDate("f_a.purchase_date"));
				record.setFirstUseDate(rs.getDate("f_a.first_use_date"));
				record.setDescription(rs.getString("f_a.description"));
				
				User creator = new User();
				creator.setId(rs.getInt("u.id"));
				creator.setName(rs.getString("u.name"));
				creator.setSurname(rs.getString("u.surname"));
				creator.setLogin(rs.getString("u.login"));
				record.setCreatedBy(creator);
				
				User editor = new User();
				editor.setId(rs.getInt("u2.id"));
				editor.setName(rs.getString("u2.name"));
				editor.setSurname(rs.getString("u2.surname"));
				editor.setLogin(rs.getString("u2.login"));
				record.setModifyBy(editor);
				
				FixedAssetClassSymbol fixedAssetClassSymbol = new FixedAssetClassSymbol();
				fixedAssetClassSymbol.setId(rs.getInt("f_s.id"));
				fixedAssetClassSymbol.setName(rs.getString("f_s.name"));
				record.setFixedAssetClassSymbol(fixedAssetClassSymbol);
				
				PurchaseDocument purchaseDocument = new PurchaseDocument();
				purchaseDocument.setId(rs.getInt("p_d.id"));
				purchaseDocument.setName(rs.getString("p_d.name"));
				record.setPurchaseDocument(purchaseDocument);
				
				record.setInitialValue(rs.getDouble("f_a.initial_value"));
				record.setAmortizationRate(rs.getDouble("f_a.amortization_rate"));
				record.setDeductionAmount(rs.getDouble("f_a.deduction_amount"));
				record.setUpdatedInitialValue(rs.getDouble("f_a.updated_initial_value"));
				record.setUpdatedDeductionAmount(rs.getString("f_a.updated_deduction_amount"));
				record.setLiquidationDate(rs.getDate("f_a.liquidation_date"));
				record.setLiquidationCouse(rs.getString("f_a.liquidation_couse"));
				record.setCreationDate(rs.getDate("f_a.creation_date"));
				record.setLastModify(rs.getDate("f_a.last_modify"));
				
				resultList.add(record);
			}
		} catch (Exception e) {
			logger.error("RecordManager.getRecords error", e);
			throw new EwidGetListException("RecordManager.getRecords error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public void addRecord(Record record) throws EwidAddException {
		logger.info("RecordManager.addRecord record=" + record);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into fixed_assets (equipment_id,purchase_date,first_use_date,purchase_document_id,description," +
					"fixed_assets_classification_symbol_id,initial_value,amortization_rate,deduction_amount,updated_initial_value," +
					"updated_deduction_amount,liquidation_date,liquidation_couse,creation_date,created_by,last_modify,modify_by) " +
					"values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,now(),?, now(),?)";
			logger.debug("RecordManager.addRecord sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, record.getEquipment().getId());
			if(record.getPurchaseDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getPurchaseDate().getTime());
				stmt.setDate(2, date);
			}
			else {
				stmt.setDate(2, null);
			}
			if(record.getFirstUseDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getFirstUseDate().getTime());
				stmt.setDate(3, date);
			}
			else {
				stmt.setDate(3, null);
			}
			stmt.setInt(4, record.getPurchaseDocument().getId());
			stmt.setString(5, record.getDescription());
			stmt.setInt(6, record.getFixedAssetClassSymbol().getId());
			stmt.setDouble(7, record.getInitialValue());
			stmt.setDouble(8, record.getAmortizationRate());
			stmt.setDouble(9, record.getDeductionAmount());
			stmt.setDouble(10, record.getUpdatedInitialValue());
			stmt.setString(11, record.getUpdatedDeductionAmount());
			if(record.getLiquidationDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getLiquidationDate().getTime());
				stmt.setDate(12, date);
			}
			else {
				stmt.setDate(12, null);
			}
			stmt.setString(13, record.getLiquidationCouse());
			stmt.setInt(14, record.getCreatedBy().getId());
			stmt.setInt(15, record.getModifyBy().getId());
			
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.FIXED_ASSET, Action.ADD);
		} catch (Exception e) {
			logger.error("RecordManager.addRecord error", e);
			throw new EwidAddException("RecordManager.addRecord error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void editRecord(Record record) throws EwidEditException {
		logger.info("RecordManager.editRecord record=" + record);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update fixed_assets set equipment_id=?, purchase_date=?, first_use_date=?, purchase_document_id=?, description=?," +
					"fixed_assets_classification_symbol_id=?, initial_value=?, amortization_rate=?, deduction_amount=?, updated_initial_value=?," +
					"updated_deduction_amount=?,liquidation_date=?, liquidation_couse=?, last_modify=now(), modify_by=? " +
					"where id = ?";
			logger.debug("RecordManager.editRecord sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, record.getEquipment().getId());
			if(record.getPurchaseDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getPurchaseDate().getTime());
				stmt.setDate(2, date);
			}
			else {
				stmt.setDate(2, null);
			}
			if(record.getFirstUseDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getFirstUseDate().getTime());
				stmt.setDate(3, date);
			}
			else {
				stmt.setDate(3, null);
			}
			
			stmt.setInt(4, record.getPurchaseDocument().getId());
			stmt.setString(5, record.getDescription());
			stmt.setInt(6, record.getFixedAssetClassSymbol().getId());
			stmt.setDouble(7, record.getInitialValue());
			stmt.setDouble(8, record.getAmortizationRate());
			stmt.setDouble(9, record.getDeductionAmount());
			stmt.setDouble(10, record.getUpdatedInitialValue());
			stmt.setString(11, record.getUpdatedDeductionAmount());
			if(record.getLiquidationDate() != null) {
				java.sql.Date date = new java.sql.Date(record.getLiquidationDate().getTime());
				stmt.setDate(12, date);
			}
			else {
				stmt.setDate(12, null);
			}
			stmt.setString(13, record.getLiquidationCouse());
			stmt.setInt(14, record.getModifyBy().getId());
			stmt.setInt(15, record.getId());
			
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.FIXED_ASSET, Action.EDIT);
		} catch (Exception e) {
			logger.error("RecordManager.editRecord error", e);
			throw new EwidEditException("RecordManager.editRecord error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public void deleteRecord(int recordId) throws EwidDeleteException {
		logger.info("RecordManager.deleteRecord recordId=" + recordId);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "delete from fixed_assets where id = ?";
			logger.debug("RecordManager.deleteRecord sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, recordId);
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.FIXED_ASSET, Action.DELETE);
		} catch (Exception e) {
			logger.error("RecordManager.deleteRecord error", e);
			throw new EwidDeleteException("RecordManager.deleteRecord error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public Record getRecordById(int recordId) throws EwidGetObjectException {
		logger.info("RecordManager.getRecordById recordId=" + recordId);
		
		Record record = new Record();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select f_a.id, e.id, e.brand, e.model, e.serial_number, f_a.purchase_date, f_a.first_use_date, f_a.purchase_document_id, f_a.description, " +
					"f_a.fixed_assets_classification_symbol_id, f_a.initial_value, f_a.amortization_rate, f_a.deduction_amount, " +
					"f_a.updated_initial_value,	f_a.updated_deduction_amount, f_a.liquidation_date, f_a.liquidation_couse, " +
					"f_a.creation_date, u.id, u.name, u.surname, u.login, f_a.last_modify, u2.id, u2.name, u2.surname, u2.login, " +
					"f_s.id, f_s.name, p_d.id, p_d.name " +
					"from fixed_assets f_a inner join equipment e on (f_a.equipment_id=e.id) " +
					"left outer join users u on (f_a.created_by=u.id) left outer join users u2 on (f_a.modify_by=u2.id) " +
					"left outer join fixed_assets_classification_symbols f_s on (f_a.fixed_assets_classification_symbol_id=f_s.id) " +
					"left outer join purchase_documents p_d on (f_a.purchase_document_id=p_d.id) where f_a.id = ?";
			logger.debug("RecordManager.getRecordById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, recordId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				record.setId(rs.getInt("f_a.id"));
				
				Equipment equipment = new Equipment();
				equipment.setId(rs.getInt("e.id"));
				equipment.setBrand(rs.getString("e.brand"));
				equipment.setModel(rs.getString("e.model"));
				equipment.setSerialNumber(rs.getString("e.serial_number"));
				record.setEquipment(equipment);
				
				record.setPurchaseDate(rs.getDate("f_a.purchase_date"));
				record.setFirstUseDate(rs.getDate("f_a.first_use_date"));
				record.setDescription(rs.getString("f_a.description"));
				
				User creator = new User();
				creator.setId(rs.getInt("u.id"));
				creator.setName(rs.getString("u.name"));
				creator.setSurname(rs.getString("u.surname"));
				creator.setLogin(rs.getString("u.login"));
				record.setCreatedBy(creator);
				
				User editor = new User();
				editor.setId(rs.getInt("u2.id"));
				editor.setName(rs.getString("u2.name"));
				editor.setSurname(rs.getString("u2.surname"));
				editor.setLogin(rs.getString("u2.login"));
				record.setModifyBy(editor);
				
				FixedAssetClassSymbol fixedAssetClassSymbol = new FixedAssetClassSymbol();
				fixedAssetClassSymbol.setId(rs.getInt("f_s.id"));
				fixedAssetClassSymbol.setName(rs.getString("f_s.name"));
				record.setFixedAssetClassSymbol(fixedAssetClassSymbol);
				
				PurchaseDocument purchaseDocument = new PurchaseDocument();
				purchaseDocument.setId(rs.getInt("p_d.id"));
				purchaseDocument.setName(rs.getString("p_d.name"));
				record.setPurchaseDocument(purchaseDocument);
				
				record.setInitialValue(rs.getDouble("f_a.initial_value"));
				record.setAmortizationRate(rs.getDouble("f_a.amortization_rate"));
				record.setDeductionAmount(rs.getDouble("f_a.deduction_amount"));
				record.setUpdatedInitialValue(rs.getDouble("f_a.updated_initial_value"));
				record.setUpdatedDeductionAmount(rs.getString("f_a.updated_initial_value"));
				record.setLiquidationDate(rs.getDate("f_a.liquidation_date"));
				record.setLiquidationCouse(rs.getString("f_a.liquidation_couse"));
				record.setCreationDate(rs.getDate("f_a.creation_date"));
				record.setLastModify(rs.getDate("f_a.last_modify"));
			}
		} catch (Exception e) {
			logger.error("RecordManager.getRecordById error", e);
			throw new EwidGetObjectException("RecordManager.getRecordById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return record;
	}
	
	@Override
	public List<FixedAssetClassSymbol> getFixedAssetClassSymbols() throws EwidGetListException {
		logger.info("RecordManager.getFixedAssetClassSymbols");
		
		List<FixedAssetClassSymbol> resultList = new ArrayList<FixedAssetClassSymbol>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name from fixed_assets_classification_symbols order by name";
			logger.debug("RecordManager.getFixedAssetClassSymbols sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				FixedAssetClassSymbol fixedAssetClassSymbol = new FixedAssetClassSymbol();
				
				fixedAssetClassSymbol.setId(rs.getInt("id"));
				fixedAssetClassSymbol.setName(rs.getString("name"));
				
				resultList.add(fixedAssetClassSymbol);
			}
		} catch (Exception e) {
			logger.error("RecordManager.getFixedAssetClassSymbols error", e);
			throw new EwidGetListException("RecordManager.getFixedAssetClassSymbols error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}
	
	@Override
	public List<PurchaseDocument> getPurchaseDocuments() throws EwidGetListException {
		logger.info("RecordManager.getPurchaseDocuments");
		
		List<PurchaseDocument> resultList = new ArrayList<PurchaseDocument>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name from purchase_documents order by name";
			logger.debug("RecordManager.getEmployeeById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				PurchaseDocument purchaseDocument = new PurchaseDocument();
				
				purchaseDocument.setId(rs.getInt("id"));
				purchaseDocument.setName(rs.getString("name"));
				
				resultList.add(purchaseDocument);
			}
		} catch (Exception e) {
			logger.error("RecordManager.getPurchaseDocuments error", e);
			throw new EwidGetListException("RecordManager.getPurchaseDocuments error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}

	@Override
	public FixedAssetClassSymbol getFixedAssetClassSymbolsById(
			int fixedAssetClassSymbolId) throws EwidGetObjectException {

		logger.info("RecordManager.getFixedAssetClassSymbolsById fixedAssetClassSymbolId=" + 
				fixedAssetClassSymbolId);
		
		FixedAssetClassSymbol result = new FixedAssetClassSymbol();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name from fixed_assets_classification_symbols where id = ? order by name";
			logger.debug("RecordManager.getFixedAssetClassSymbolsById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, fixedAssetClassSymbolId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				result.setId(rs.getInt("id"));
				result.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.error("RecordManager.getFixedAssetClassSymbolsById error", e);
			throw new EwidGetObjectException("RecordManager.getFixedAssetClassSymbolsById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return result;

	}

	@Override
	public PurchaseDocument getPurchaseDocumentsById(int purchaseDocumentId)
			throws EwidGetObjectException {
		logger.info("RecordManager.getPurchaseDocumentsById purchaseDocumentId=" + purchaseDocumentId);
		
		PurchaseDocument result = new PurchaseDocument();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, name from purchase_documents where id = ? order by name";
			logger.debug("RecordManager.getPurchaseDocumentsById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, purchaseDocumentId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.setId(rs.getInt("id"));
				result.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.error("RecordManager.getPurchaseDocumentsById error", e);
			throw new EwidGetObjectException("RecordManager.getPurchaseDocumentsById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return result;
	}
}
