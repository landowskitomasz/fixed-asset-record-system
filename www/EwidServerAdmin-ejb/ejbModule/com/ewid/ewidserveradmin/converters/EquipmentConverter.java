package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.EquipmentManagerLocal;
import com.ewid.ewidmanagers.client.struct.Equipment;


public class EquipmentConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int equipmentId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			EquipmentManagerLocal manager = (EquipmentManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/EquipmentsManagerLocal");
			return manager.getEquipmentById(equipmentId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Equipment equipment = (Equipment) arg2;
		return equipment.getId() + "";
	}

}
