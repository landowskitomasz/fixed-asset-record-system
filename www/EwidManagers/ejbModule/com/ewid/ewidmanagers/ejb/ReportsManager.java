package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import com.ewid.ewidmanagers.client.ejb.local.ReportsManagerLocal;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Report;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = ReportsManagerLocal.JNDI_NAME)
public class ReportsManager implements ReportsManagerLocal, Serializable{

	private static final long serialVersionUID = 1207575356362916691L;
	
	private Logger logger = Logger.getLogger(ReportsManager.class);

	@Override
	public Report getLastMonthReport() throws EwidGetObjectException {
		logger.info("ReportsManager.getLastMonthReport");
		Report result = new Report();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select user_add, user_edit, user_delete, employee_add, employee_edit, employee_delete, " +
					"equipment_add, equipment_edit, equipment_delete, localization_add, localization_edit, localization_delete, " +
					"mapping_add, mapping_edit, mapping_delete, fixed_asset_add, fixed_asset_edit, fixed_asset_delete " +
					"from last_month_report";
			
			logger.debug("ReportsManager.getLastMonthReport sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				result.setUserAdd(rs.getInt("user_add"));
				result.setUserEdit(rs.getInt("user_edit"));
				result.setUserDelete(rs.getInt("user_delete"));
				result.setEmployeeAdd(rs.getInt("employee_add"));
				result.setEmployeeEdit(rs.getInt("employee_edit"));
				result.setEmployeeDelete(rs.getInt("employee_delete"));
				result.setEquipmentAdd(rs.getInt("equipment_add"));
				result.setEquipmentEdit(rs.getInt("equipment_edit"));
				result.setEquipmentDelete(rs.getInt("equipment_delete"));
				result.setLocalizationAdd(rs.getInt("localization_add"));
				result.setLocalizationEdit(rs.getInt("localization_edit"));
				result.setLocalizationDelete(rs.getInt("localization_delete"));
				result.setMappingAdd(rs.getInt("mapping_add"));
				result.setMappingEdit(rs.getInt("mapping_edit"));
				result.setMappingDelete(rs.getInt("mapping_delete"));
				result.setFixedAssetAdd(rs.getInt("fixed_asset_add"));
				result.setFixedAssetEdit(rs.getInt("fixed_asset_edit"));
				result.setFixedAssetDelete(rs.getInt("fixed_asset_delete"));
			}

		} catch (Exception e) {
			logger.error("ReportsManager.getLastMonthReport error", e);
			throw new EwidGetObjectException("ReportsManager.getLastMonthReport error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		return result;
	}

	@Override
	public Report getLastYearReport() throws EwidGetObjectException {
		logger.info("ReportsManager.getLastYearReport");
		Report result = new Report();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select user_add, user_edit, user_delete, employee_add, employee_edit, employee_delete, " +
					"equipment_add, equipment_edit, equipment_delete, localization_add, localization_edit, localization_delete, " +
					"mapping_add, mapping_edit, mapping_delete, fixed_asset_add, fixed_asset_edit, fixed_asset_delete " +
					"from last_year_report";
			
			logger.debug("ReportsManager.getLastYearReport sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				result.setUserAdd(rs.getInt("user_add"));
				result.setUserEdit(rs.getInt("user_edit"));
				result.setUserDelete(rs.getInt("user_delete"));
				result.setEmployeeAdd(rs.getInt("employee_add"));
				result.setEmployeeEdit(rs.getInt("employee_edit"));
				result.setEmployeeDelete(rs.getInt("employee_delete"));
				result.setEquipmentAdd(rs.getInt("equipment_add"));
				result.setEquipmentEdit(rs.getInt("equipment_edit"));
				result.setEquipmentDelete(rs.getInt("equipment_delete"));
				result.setLocalizationAdd(rs.getInt("localization_add"));
				result.setLocalizationEdit(rs.getInt("localization_edit"));
				result.setLocalizationDelete(rs.getInt("localization_delete"));
				result.setMappingAdd(rs.getInt("mapping_add"));
				result.setMappingEdit(rs.getInt("mapping_edit"));
				result.setMappingDelete(rs.getInt("mapping_delete"));
				result.setFixedAssetAdd(rs.getInt("fixed_asset_add"));
				result.setFixedAssetEdit(rs.getInt("fixed_asset_edit"));
				result.setFixedAssetDelete(rs.getInt("fixed_asset_delete"));
			}

		} catch (Exception e) {
			logger.error("ReportsManager.getLastYearReport error", e);
			throw new EwidGetObjectException("ReportsManager.getLastYearReport error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		return result;
	}

	@Override
	public Report getSummaryReport() throws EwidGetObjectException {
		logger.info("ReportsManager.getSummaryReport");
		Report result = new Report();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select user_add, user_edit, user_delete, employee_add, employee_edit, employee_delete, " +
					"equipment_add, equipment_edit, equipment_delete, localization_add, localization_edit, localization_delete, " +
					"mapping_add, mapping_edit, mapping_delete, fixed_asset_add, fixed_asset_edit, fixed_asset_delete " +
					"from summary_report";
			
			logger.debug("ReportsManager.getSummaryReport sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				result.setUserAdd(rs.getInt("user_add"));
				result.setUserEdit(rs.getInt("user_edit"));
				result.setUserDelete(rs.getInt("user_delete"));
				result.setEmployeeAdd(rs.getInt("employee_add"));
				result.setEmployeeEdit(rs.getInt("employee_edit"));
				result.setEmployeeDelete(rs.getInt("employee_delete"));
				result.setEquipmentAdd(rs.getInt("equipment_add"));
				result.setEquipmentEdit(rs.getInt("equipment_edit"));
				result.setEquipmentDelete(rs.getInt("equipment_delete"));
				result.setLocalizationAdd(rs.getInt("localization_add"));
				result.setLocalizationEdit(rs.getInt("localization_edit"));
				result.setLocalizationDelete(rs.getInt("localization_delete"));
				result.setMappingAdd(rs.getInt("mapping_add"));
				result.setMappingEdit(rs.getInt("mapping_edit"));
				result.setMappingDelete(rs.getInt("mapping_delete"));
				result.setFixedAssetAdd(rs.getInt("fixed_asset_add"));
				result.setFixedAssetEdit(rs.getInt("fixed_asset_edit"));
				result.setFixedAssetDelete(rs.getInt("fixed_asset_delete"));
			}

		} catch (Exception e) {
			logger.error("ReportsManager.getSummaryReport error", e);
			throw new EwidGetObjectException("ReportsManager.getSummaryReport error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		return result;
	}

	
}
