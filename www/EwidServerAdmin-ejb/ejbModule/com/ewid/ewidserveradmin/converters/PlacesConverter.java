package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.PlacesManagerLocal;
import com.ewid.ewidmanagers.client.struct.Place;

public class PlacesConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int placeId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			PlacesManagerLocal manager = (PlacesManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/PlacesManagerLocal");
			return manager.getPlaceById(placeId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Place place = (Place) arg2;
		return place.getId() + "";
	}

}
