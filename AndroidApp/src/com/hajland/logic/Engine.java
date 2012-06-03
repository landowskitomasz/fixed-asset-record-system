package com.hajland.logic;

import java.util.ArrayList;
import java.util.List;
import com.hajland.models.Employee;
import com.hajland.models.Equipment;
import com.hajland.models.Mapping;
import com.mobeelizer.mobile.android.Mobeelizer;
import com.mobeelizer.mobile.android.api.MobeelizerDatabase;
import com.mobeelizer.mobile.android.api.MobeelizerLoginCallback;
import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;
import com.mobeelizer.mobile.android.api.MobeelizerRestrictions;
import com.mobeelizer.mobile.android.api.MobeelizerSyncCallback;
import com.mobeelizer.mobile.android.api.MobeelizerSyncStatus;

public class Engine 
{
	private static Engine instance;
	
	private Mapper mapper;
	
	private UserIdentyfication userIdentyfication;
	
	private final String userName = "androidClient";
	
	private final String password = "hajland";

	private List<Mapping> employeeInConflictList = new ArrayList<Mapping>();

	private List<Mapping> equipmentInConflictList = new ArrayList<Mapping>();
	
	public List<Mapping> getEmployeeInConflictList()
	{
		return this.employeeInConflictList;
	}
	
	public List<Mapping> getEquipmentInConflictList ()
	{
		return this.equipmentInConflictList;
	}
	
	private Engine()
	{	
	}
	
	public static Engine getInstance()
	{
		if(instance == null)
		{
			instance = new Engine();
		}
		
		return instance;
	}
	
	public void login(final LoginCallback callback)
	{
		Mobeelizer.login(userName, password, new MobeelizerLoginCallback() 
		{	 
		    public void onLoginFinished(MobeelizerLoginStatus status) 
		    {
		    	if(status == MobeelizerLoginStatus.OK)
		    	{
			        MobeelizerDatabase database = Mobeelizer.getDatabase();
			       
			        mapper = new Mapper(database);
			        
			        userIdentyfication = new UserIdentyfication(database);
		    	}
		        callback.onLoginFinished(status);
		    }
		});
	}
	
	public void logout()
	{
		Mobeelizer.logout();
	}
	
	public Mapper getMapper()
	{
		return this.mapper;
	}
	
	public UserIdentyfication getUserIdentyfication()
	{
		return this.userIdentyfication;
	}
	
	public void clearWorkingEnvironment(final ClearingCallback callback)
	{
		Mobeelizer.syncAll(new MobeelizerSyncCallback(){
			public void onSyncFinished(MobeelizerSyncStatus status) {
				if(status == MobeelizerSyncStatus.FINISHED_WITH_SUCCESS)
				{
					callback.onClearingFinished(ClearingStatus.Finished);
				}
				else
				{
					// TODO: other errors handling if necessarily 
					callback.onClearingFinished(ClearingStatus.Failed);
				}
			}
		});
	}
	
	public void synchronize(final SynchronizeCallback synchronizeCallback) {
		
		Mobeelizer.sync(new MobeelizerSyncCallback(){
			public void onSyncFinished(MobeelizerSyncStatus status) {
				if(status == MobeelizerSyncStatus.FINISHED_WITH_SUCCESS)
				{
					if(!lookForConflicts())
					{
		    			Settings.getInstance().setConflictStatus(false);
						synchronizeCallback.onFinished(SynchronizationStatus.SUCCESS);
					}
					else
					{
		    			Settings.getInstance().setConflictStatus(true);
						synchronizeCallback.onFinished(SynchronizationStatus.CONFLICTS);
					}
				}
				else if ( status == MobeelizerSyncStatus.FINISHED_WITH_FAILURE)
				{
					synchronizeCallback.onFinished(SynchronizationStatus.FATALERROR);
				}
			}
		});
	}
	
	public boolean lookForConflicts()
	{
		equipmentInConflictList.clear();
		employeeInConflictList.clear();
		MobeelizerDatabase database = Mobeelizer.getDatabase();
		List<Employee> employees = database.list(Employee.class);
		List<Equipment> equipmentList = database.list(Equipment.class);
		for(Employee employee: employees)
		{
			List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("employee", employee.getGuid())).list();
			Mapping foundMpping = null;
			int number = 0;
			for(Mapping mapping: mappings)
			{
				if(mapping.getPlace() != null)
				{
					if(foundMpping == null)
					{
						foundMpping = mapping;
					}
					else
					{
						if(number == 1)
						{
							employeeInConflictList.add(foundMpping);
						}
						employeeInConflictList.add(mapping);
					}
					number++;
				}
			}
		}
		
		for(Equipment equipment: equipmentList)
		{
			List<Mapping> mappings = database.find(Mapping.class).add(MobeelizerRestrictions.eq("equipment", equipment.getGuid())).list();

			Mapping foundMpping = null;
			int number = 0;
			for(Mapping mapping: mappings)
			{
				if(mapping.getEmployee() != null)
				{
					if(foundMpping == null)
					{
						foundMpping = mapping;
					}
					else
					{
						if(number == 1)
						{
							equipmentInConflictList.add(foundMpping);
						}
						equipmentInConflictList.add(mapping);
					}
					number++;
				}
			}
		}
		if(equipmentInConflictList.size() == 0 && employeeInConflictList.size() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
