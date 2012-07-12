package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;

import com.ewid.ewidmanagers.client.ejb.local.EquipmentManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.RecordManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidmanagers.client.struct.FixedAssetClassSymbol;
import com.ewid.ewidmanagers.client.struct.PurchaseDocument;
import com.ewid.ewidmanagers.client.struct.Record;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@Name("recordBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class RecordBean implements Serializable {

	private static final long serialVersionUID = 5238651705116494521L;

	private Logger logger = Logger.getLogger(RecordBean.class);
	@In
	Credentials credentials;
	@Manager
	RecordManagerLocal recordManager;
	@Manager
	EquipmentManagerLocal equipmentManager;
	@Manager
	UsersManagerLocal usersManager;
	
	private List<Record> records;
	private Record recordToDelete;
	private Record recordToAdd;
	private Record recordToEdit;
	private Record recordToShowDetails;
	
	private List<Equipment> equipmentList;
	private List<FixedAssetClassSymbol> kstList;
	private List<PurchaseDocument> purchaseDocumentsList;
	
	@Create
	public void onCreate() {
		logger.info("RecordBean.onCreate");
		retrieveRecords();
		retrieveEquipment();
		retrievePurchaseDocuments();
		retrieveKST();
		recordToAdd = new Record();
	}
	
	private void retrieveRecords() {
		logger.info("RecordBean.retrieveRecords");
		records = recordManager.getRecords();
	}
	
	private void retrieveEquipment() {
		logger.info("RecordBean.retrieveEquipment");
		equipmentList = equipmentManager.getNotUsedInRecordEquipment();
	}
	
	private void retrievePurchaseDocuments() {
		logger.info("RecordBean.retrieveEquipment");
		purchaseDocumentsList = recordManager.getPurchaseDocuments();
	}
	
	private void retrieveKST() {
		logger.info("RecordBean.retrieveEquipment");
		kstList = recordManager.getFixedAssetClassSymbols();
	}
	
	public void deleteRecord(Record record) {
		logger.info("RecordBean.deleteRecord record=" + record);
		recordManager.deleteRecord(record.getId());
		retrieveRecords();
		retrieveEquipment();
	}
	
	public void addRecord(Record record) {
		logger.info("RecordBean.addRecord record=" + record);
		record.setCreatedBy(usersManager.getUserByLogin(credentials.getUsername()));
		record.setModifyBy(usersManager.getUserByLogin(credentials.getUsername()));
		recordManager.addRecord(record);
		retrieveRecords();
		retrieveEquipment();
		this.recordToAdd = new Record();
	}
	
	public void editRecord(Record record) {
		logger.info("RecordBean.addRecord record=" + record);
		record.setModifyBy(usersManager.getUserByLogin(credentials.getUsername()));
		recordManager.editRecord(record);
		retrieveRecords();
		retrieveEquipment();
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Record getRecordToDelete() {
		return recordToDelete;
	}

	public void setRecordToDelete(Record recordToDelete) {
		this.recordToDelete = recordToDelete;
	}

	public Record getRecordToAdd() {
		return recordToAdd;
	}

	public void setRecordToAdd(Record recordToAdd) {
		this.recordToAdd = recordToAdd;
	}

	public Record getRecordToEdit() {
		return recordToEdit;
	}

	public void setRecordToEdit(Record recordToEdit) {
		this.recordToEdit = recordToEdit;
	}

	public Record getRecordToShowDetails() {
		return recordToShowDetails;
	}

	public void setRecordToShowDetails(Record recordToShowDetails) {
		this.recordToShowDetails = recordToShowDetails;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<FixedAssetClassSymbol> getKstList() {
		return kstList;
	}

	public void setKstList(List<FixedAssetClassSymbol> kstList) {
		this.kstList = kstList;
	}

	public List<PurchaseDocument> getPurchaseDocumentsList() {
		return purchaseDocumentsList;
	}

	public void setPurchaseDocumentsList(
			List<PurchaseDocument> purchaseDocumentsList) {
		this.purchaseDocumentsList = purchaseDocumentsList;
	}
}
