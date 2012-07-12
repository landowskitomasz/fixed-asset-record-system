package com.ewid.ewidserveradmin.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
public class EmailValidator implements Validator {
	
	public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) 
			throws ValidatorException{
		
		String mail = (String)object;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(mail);
		boolean matchFound = m.matches();
		
		if (!matchFound) {
			FacesMessage message = new FacesMessage();
			message.setSummary("Invalid e-mail adress.");
			throw new ValidatorException(message);
		}
	}
}