package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;

import com.ewid.ewidmanagers.client.ejb.local.EmployeesManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.EquipmentManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.MappingsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.PlacesManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
import com.ewid.ewidmanagers.client.struct.Employee;
import com.ewid.ewidmanagers.client.struct.Equipment;
import com.ewid.ewidmanagers.client.struct.Mapping;
import com.ewid.ewidmanagers.client.struct.Place;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@Name("mappingsBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class MappingsBean implements Serializable {

	private static final long serialVersionUID = 354171934094747327L;
	
	private Logger logger = Logger.getLogger(MappingsBean.class);
	
	@In Credentials credentials;
	
	@Manager
	private MappingsManagerLocal mappingsManager;
	@Manager
	private EmployeesManagerLocal employeesManager;
	@Manager
	private EquipmentManagerLocal equipmentManager;
	@Manager
	private PlacesManagerLocal placesManager;
	@Manager
	private UsersManagerLocal usersManager;
	
	private List<Mapping> mappings;
	private Mapping mappingToDelete;
	private List<Mapping> mappingToAddFake;
	private Mapping mappingToAdd;
	private Mapping mappingToEdit;
	private Mapping mappingToShowDetails;
		
	private List<Employee> employeesList;
	private List<Equipment> equipmentList;
	private List<Place> placesList; 
	
	@Create
	public void onCreate() {
		logger.info("MappingsBean.onCreate");
		retrieveMappings();
		retrieveEmployees();
		retrieveEquipment();
		retrievePlaces();
		mappingToAddFake = new ArrayList<Mapping>();
		mappingToAddFake.add(new Mapping());
		mappingToAdd = new Mapping();
	}
	
	public void addMapping(Mapping mapping) {
		logger.info("MappingsBean.addMapping mapping=" + mapping);
		mapping.setCreatedBy(usersManager.getUserByLogin(credentials.getUsername()));
		mapping.setModifyBy(usersManager.getUserByLogin(credentials.getUsername()));
		mappingsManager.addMapping(mapping);
		retrieveEquipment();
		retrieveMappings();
		mappingToAdd = new Mapping();
	}
	
	public void editMapping(Mapping mapping) {
		logger.info("MappingsBean.editMapping mapping=" + mapping);
		mapping.setModifyBy(usersManager.getUserByLogin(credentials.getUsername()));
		mappingsManager.editMapping(mapping);
		retrieveEquipment();
		retrieveMappings();
		mappingToAdd = new Mapping();
	}
	
	public void deleteMapping(Mapping mapping) {
		logger.info("MappingsBean.deleteMapping mapping=" + mapping);
		mappingsManager.deleteMapping(mapping.getId());
		retrieveEquipment();
		retrieveMappings();
		mappingToAdd = new Mapping();
	}
	
	private void retrieveMappings() {
		mappings = mappingsManager.getMappings();
	}
	
	private void retrieveEmployees() {
		employeesList = employeesManager.getEmployees();
	}
	
	private void retrieveEquipment() {
		equipmentList = equipmentManager.getNotUsedInMappingEquipment();
	}
	
	private void retrievePlaces() {
		placesList = placesManager.getPlaces();
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}

	public Mapping getMappingToDelete() {
		return mappingToDelete;
	}

	public void setMappingToDelete(Mapping mappingToDelete) {
		this.mappingToDelete = mappingToDelete;
	}

	public List<Mapping> getMappingToAddFake() {
		return mappingToAddFake;
	}

	public void setMappingToAddFake(List<Mapping> mappingToAddFake) {
		this.mappingToAddFake = mappingToAddFake;
	}

	public Mapping getMappingToAdd() {
		return mappingToAdd;
	}

	public void setMappingToAdd(Mapping mappingToAdd) {
		this.mappingToAdd = mappingToAdd;
	}

	public List<Employee> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employee> employeesList) {
		this.employeesList = employeesList;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<Place> getPlacesList() {
		return placesList;
	}

	public void setPlacesList(List<Place> placesList) {
		this.placesList = placesList;
	}

	public Mapping getMappingToEdit() {
		return mappingToEdit;
	}

	public void setMappingToEdit(Mapping mappingToEdit) {
		this.mappingToEdit = mappingToEdit;
	}

	public Mapping getMappingToShowDetails() {
		return mappingToShowDetails;
	}

	public void setMappingToShowDetails(Mapping mappingToShowDetails) {
		this.mappingToShowDetails = mappingToShowDetails;
	}
}
