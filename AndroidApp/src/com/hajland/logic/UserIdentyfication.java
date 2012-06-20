package com.hajland.logic;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import com.hajland.models.User;
import com.mobeelizer.mobile.android.api.MobeelizerDatabase;
import com.mobeelizer.mobile.android.api.MobeelizerRestrictions;

public class UserIdentyfication {
	private MobeelizerDatabase database;
	
	private User currentUser;
	
	public UserIdentyfication(MobeelizerDatabase database)
	{
		this.database = database;
	}
	

	public boolean tryIdentifyUser(String login, String password)
	{
		
		User user = this.database.find(User.class).add(MobeelizerRestrictions.eq("login", login)).uniqueResult();
		if(user == null)
		{
			return false;
		}
		else
		{
			try
			{
				if(SHA1.doSHA1(password).equals(user.getPassword()))
				{
					setCurrentUser(user);
					return true;
				}
			}
			catch(NoSuchAlgorithmException e){}
			catch(UnsupportedEncodingException e){}
			return false;
		}
	}

	public User getCurrentUser() {
		if(currentUser == null)
		{
			int id = Settings.getInstance().getSavedUserId();
			if(id != -1)
			{
				currentUser = this.database.find(User.class).add(MobeelizerRestrictions.eq("id", id)).uniqueResult();
			}
		}
		return currentUser;
	}


	public void setCurrentUser(User currentUser) {
		Settings.getInstance().saveUser(currentUser.getId());
		this.currentUser = currentUser;
	}


	public void forgetUser() {
		Settings.getInstance().saveUser(-1);
	}
}
