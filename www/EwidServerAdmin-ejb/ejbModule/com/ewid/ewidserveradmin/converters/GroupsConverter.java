package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
import com.ewid.ewidmanagers.client.struct.Group;

public class GroupsConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int groupId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			UsersManagerLocal manager = (UsersManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/UsersManagerLocal");
			return manager.getGroupById(groupId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Group group = (Group) arg2;
		return group.getId() + "";
	}

}
