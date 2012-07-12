package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class PlaceFloorConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int floor = Integer.parseInt(arg2);
		return floor;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 + "";
	}

}
