package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.Employee;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.Equipment;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.Mapping;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.Place;
import com.ewid.ewidmanagers.client.struct.mobeelizer.model.User;


public interface MobeelizerManagerCommon {
	public List<User> getUsersToSync() throws EwidGetListException;
	public List<Mapping> getMappingsToSync() throws EwidGetListException;
	public List<Place> getPlacesToSync() throws EwidGetListException;
	public List<Employee> getEmployeesToSync() throws EwidGetListException;
	public List<Equipment> getEquipmentToSync() throws EwidGetListException;
	public void addMappingsToDb(List<Mapping> mappingToAdd) throws EwidAddException;
}
