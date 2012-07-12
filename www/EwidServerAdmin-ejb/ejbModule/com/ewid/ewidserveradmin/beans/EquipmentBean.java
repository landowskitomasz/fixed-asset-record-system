package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidmanagers.client.ejb.local.EquipmentManagerLocal;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@AutoCreate
@Name("equipmentBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class EquipmentBean implements Serializable {

	private static final long serialVersionUID = -5561150155062857558L;
	
	private Logger logger = Logger.getLogger(EquipmentBean.class);
	
	@Manager
	private EquipmentManagerLocal equipmentManager;
	
	private List<Equipment> equipment;
	private Equipment equipmentToDelete;
	private List<Equipment> equipmentToAddFake;
	private Equipment equipmentToAdd;
	private Equipment equipmentToEdit;
	
	@Create 
	public void onCreate() {
		logger.info("EquipmentBean.onCreate");
		retrieveEquipment();
		equipmentToAddFake = new ArrayList<Equipment>();
		equipmentToAddFake.add(new Equipment());
		equipmentToAdd = new Equipment();
	}
	
	private void retrieveEquipment() {
		logger.info("EquipmentBean.retrieveEquipment");
		equipment = equipmentManager.getEquipment();
	}
	
	public void deleteEquipment(Equipment equip) {
		logger.info("EquipmentBean.deleteEquipment");
		equipmentManager.deleteEquipment(equip.getId());
		retrieveEquipment();
		equipmentToDelete = new Equipment();
	}
	
	public void addEquipment(Equipment equipment) {
		logger.info("EquipmentBean.addEquipment");
		equipmentManager.addEquipment(equipment);
		retrieveEquipment();
		equipmentToAdd = new Equipment();
	}
	
	public void editEquipment(Equipment equipment) {
		logger.info("EquipmentBean.editEquipment");
		equipmentManager.editEquipment(equipment);
		retrieveEquipment();
		equipmentToEdit = new Equipment();
	}

	public List<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(List<Equipment> equipment) {
		this.equipment = equipment;
	}

	public Equipment getEquipmentToDelete() {
		return equipmentToDelete;
	}

	public void setEquipmentToDelete(Equipment equipmentToDelete) {
		this.equipmentToDelete = equipmentToDelete;
	}

	public List<Equipment> getEquipmentToAddFake() {
		return equipmentToAddFake;
	}

	public void setEquipmentToAddFake(List<Equipment> equipmentToAddFake) {
		this.equipmentToAddFake = equipmentToAddFake;
	}

	public Equipment getEquipmentToAdd() {
		return equipmentToAdd;
	}

	public void setEquipmentToAdd(Equipment equipmentToAdd) {
		this.equipmentToAdd = equipmentToAdd;
	}

	public Equipment getEquipmentToEdit() {
		return equipmentToEdit;
	}

	public void setEquipmentToEdit(Equipment equipmentToEdit) {
		this.equipmentToEdit = equipmentToEdit;
	}
}
