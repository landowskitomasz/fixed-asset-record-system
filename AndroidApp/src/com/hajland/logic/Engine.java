package com.hajland.logic;

import com.mobeelizer.mobile.android.Mobeelizer;
import com.mobeelizer.mobile.android.api.MobeelizerDatabase;
import com.mobeelizer.mobile.android.api.MobeelizerLoginCallback;
import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;

public class Engine 
{
	private static Engine instance;
	private Mapper mapper;
	private final String userName = "androidClient";
	private final String password = "hajland";
	
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
	
	public void clearWorkingEnvironment(final ClearingCallback callback)
	{
		// TODO: implement it 
	}
}
