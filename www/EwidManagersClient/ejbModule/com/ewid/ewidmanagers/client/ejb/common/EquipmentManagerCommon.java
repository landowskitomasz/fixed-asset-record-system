package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Equipment;

public interface EquipmentManagerCommon {
	public List<Equipment> getEquipment() throws EwidGetListException;
	public Equipment getEquipmentById(int equipmentId) throws EwidGetObjectException;
	public void addEquipment(Equipment equipment) throws EwidAddException;
	public void editEquipment(Equipment equipment) throws EwidEditException;
	public void deleteEquipment(int equipmentId) throws EwidDeleteException;
	public List<Equipment> getNotUsedInMappingEquipment() throws EwidGetListException;
	public List<Equipment> getNotUsedInRecordEquipment() throws EwidGetListException;
}
