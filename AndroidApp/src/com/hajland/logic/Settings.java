package com.hajland.logic;

import android.content.SharedPreferences;

public class Settings {
	
	private SharedPreferences preferences;
	
	private Settings()
	{
	}
	
	private static final Settings instance = new Settings();
	
	public static Settings getInstance()
	{
		return instance;
	}
	
	public void init(SharedPreferences preferences)
	{
		this.preferences = preferences;
	}
	
	public int getSavedUserId()
	{
		return preferences.getInt("user",-1);
	}
	
	public void saveUser(int id)
	{
		SharedPreferences.Editor editor = preferences.edit();
	    editor.putInt("user", id);
	    editor.commit();
	}
}
