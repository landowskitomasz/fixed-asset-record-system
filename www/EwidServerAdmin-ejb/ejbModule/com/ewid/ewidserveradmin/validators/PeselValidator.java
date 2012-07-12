package com.ewid.ewidserveradmin.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
public class PeselValidator implements Validator {
	
	public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) 
			throws ValidatorException{
		
		String pesel = (String)object;
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(pesel);
		boolean matchFound = m.matches();
		
		if (!matchFound) {
			FacesMessage message = new FacesMessage();
			message.setSummary("Invalid pesel.");
			throw new ValidatorException(message);
		}
	}
}