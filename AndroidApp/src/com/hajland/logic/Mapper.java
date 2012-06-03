package com.hajland.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.util.Log;
import com.hajland.models.Equipment;
import com.hajland.models.Employee;
import com.hajland.models.Mapping;
import com.hajland.models.Place;
import com.mobeelizer.mobile.android.api.MobeelizerDatabase;
import com.mobeelizer.mobile.android.api.MobeelizerRestrictions;

public class Mapper 
{
	private MobeelizerDatabase database;
	
	public Mapper(MobeelizerDatabase database)
	{
		this.database = database;
	}
	
	public List<Place> getPlaces()
	{
		return this.database.list(Place.class);
	}
	
	public Place getPlace(int id)
	{
		List<Place> places = database.find(Place.class).add(MobeelizerRestrictions.eq("id", id)).list();
		if(places.isEmpty())
		{
			return null;
		}
		else
		{
			return places.get(0);
		}
	}

	public List<Employee> findEmpoyees(Place place) {
		List<Employee> employees = new ArrayList<Employee>();
		
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("place", place.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			employees.add(database.get(Employee.class, mappings.get(i).getEmployee()));
		}
		return employees;
	}

	public List<Employee> findOtherEmployees(Place place) {
		List<Employee> employees = database.list(Employee.class);
		
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("place", place.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			for(int j= 0; j < employees.size(); ++j)
			{
				if(employees.get(j).getGuid().equals(mappings.get(i).getEmployee()))
				{
					employees.remove(j);
				}
			}
		}
		return employees;
	}

	public Employee getEmployee(int employeeID) {
		List<Employee> employees = database.find(Employee.class).add(MobeelizerRestrictions.eq("id", employeeID)).list();
		if(employees.isEmpty())
		{
			Log.i("Mapper", "Not found employee.");
			return null;
		}
		else
		{
			Log.i("Mapper", "Found employee:"+employees.get(0));
			return employees.get(0);
		}
	}

	public List<Equipment> findEmployeeEquipment(Employee employee) {
		List<Equipment> equipmentList = new ArrayList<Equipment>();
		
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			if(mappings.get(i).getEquipment() !=  null)
			{
				equipmentList.add(database.get(Equipment.class, mappings.get(i).getEquipment()));
			}
		}
		return equipmentList;
	}


	public List<Equipment> findOtherEquipment(Employee employee) {
	    List<Equipment> equipmentList = database.list(Equipment.class);
		
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			for(int j= 0; j < equipmentList.size(); ++j)
			{
				if(equipmentList.get(j).getGuid().equals(mappings.get(i).getEquipment()))
				{
					equipmentList.remove(j);
				}
			}
		}
		return equipmentList;
	}

	public void map(Employee employee, Equipment equipment) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("equipment", equipment.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			if(mappings.get(i).getEmployee() != null)
			{
				database.delete(Mapping.class, mappings.get(i).getGuid());
			}
		}
		
		Mapping mapping = new Mapping();
		mapping.setEmployee(employee.getGuid());
		mapping.setEquipment(equipment.getGuid());
		mapping.setCreatedBy(Engine.getInstance().getUserIdentyfication().getCurrentUser().getId());
		mapping.setCreationDate(new Date());
		database.save(mapping);
	}
	
	public void changeMaping(Employee employee, Equipment equipment) {
		Mapping mapping = database.find(Mapping.class).add(MobeelizerRestrictions.eq("equipment", equipment.getGuid())).uniqueResult();
		mapping.setEmployee(employee.getGuid());
		mapping.setCreationDate(new Date());
		mapping.setCreatedBy(Engine.getInstance().getUserIdentyfication().getCurrentUser().getId());
		database.save(mapping);
	}

	public void removeMapping(Employee employee, Equipment equipment) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).add(MobeelizerRestrictions.eq("equipment", equipment.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			database.delete(Mapping.class, mappings.get(i).getGuid());
		}	
	}
	
	public void map(Place place, Employee employee) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			if(mappings.get(i).getPlace() != null)
			{
				database.delete(Mapping.class, mappings.get(i).getGuid());
			}
		}
		
		Mapping mapping = new Mapping();
		mapping.setEmployee(employee.getGuid());
		mapping.setPlace(place.getGuid());
		mapping.setCreatedBy(Engine.getInstance().getUserIdentyfication().getCurrentUser().getId());
		mapping.setCreationDate(new Date());
		database.save(mapping);
	}
	public void changeMaping(Place place, Employee employee) {
		Mapping mapping = null;
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			if(mappings.get(i).getPlace() != null)
			{
				mapping = mappings.get(i);
				break;
			}
		}
		if(mapping != null)
		{
			mapping.setPlace(place.getGuid());
			mapping.setCreationDate(new Date());
			mapping.setCreatedBy(Engine.getInstance().getUserIdentyfication().getCurrentUser().getId());
			database.save(mapping);
		}
	}

	public void removeMapping(Place place, Employee employee) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).add(MobeelizerRestrictions.eq("place", place.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			database.delete(Mapping.class, mappings.get(i).getGuid());
		}	
	}

	public Employee getEquipmentOwner(Equipment equipment) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("equipment", equipment.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			if(mappings.get(i).getEmployee() !=  null)
			{
				return database.get(Employee.class, mappings.get(i).getEmployee());
			}
		}
		return null;
	}

	public Place getEmployeePlace(Employee employee) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
		for(int i =0; i <mappings.size(); ++i)
		{
			if(mappings.get(i).getPlace() !=  null)
			{
				return database.get(Place.class, mappings.get(i).getPlace());
			}
		}
		return null;
	}

	public void removeMapping(Mapping mapping) {
		database.delete(Mapping.class, mapping.getGuid());
		Log.i("Mapper", "mapping deleted");
	}

}
