package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.RecordManagerLocal;
import com.ewid.ewidmanagers.client.struct.FixedAssetClassSymbol;

public class KstConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int kstId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			RecordManagerLocal manager = (RecordManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/RecordManagerLocal");
			return manager.getFixedAssetClassSymbolsById(kstId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		FixedAssetClassSymbol fixedAssetClassSymbol = (FixedAssetClassSymbol) arg2;
		return fixedAssetClassSymbol.getId() + "";
	}

}
