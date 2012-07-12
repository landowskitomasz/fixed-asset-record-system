package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.RecordManagerLocal;
import com.ewid.ewidmanagers.client.struct.PurchaseDocument;

public class PurchaseDocumentsConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int purchaseDocumentId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			RecordManagerLocal manager = (RecordManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/RecordManagerLocal");
			return manager.getPurchaseDocumentsById(purchaseDocumentId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		PurchaseDocument purchaseDocument = (PurchaseDocument) arg2;
		return purchaseDocument.getId() + "";
	}

}
