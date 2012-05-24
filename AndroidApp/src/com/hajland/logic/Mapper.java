package com.hajland.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

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
		mapping.setCreatedBy(1);
		mapping.setCreationDate(new Date());
		database.save(mapping);
	}

	public void removeMapping(Place place, Employee employee) {
		List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).add(MobeelizerRestrictions.eq("place", place.getGuid())).list();
		for(int i =0 ; i < mappings.size(); ++i)
		{
			database.delete(Mapping.class, mappings.get(i).getGuid());
		}	
	}
}
