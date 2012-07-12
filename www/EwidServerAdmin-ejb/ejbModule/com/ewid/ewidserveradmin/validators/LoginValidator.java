package com.ewid.ewidserveradmin.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ewid.ewidmanagers.client.ejb.local.UsersManagerLocal;
public class LoginValidator implements Validator {
	
	public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) 
			throws ValidatorException{
		try {
			String login = (String)object;
			InitialContext ctx = new InitialContext();
			UsersManagerLocal manager = (UsersManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/UsersManagerLocal");
			boolean isLoginEmpty = manager.checkIfLoginEmpty(login);
			
			if (!isLoginEmpty) {
				FacesMessage message = new FacesMessage();
				message.setSummary("Login exists.");
				throw new ValidatorException(message);
			}
		} catch(NamingException e) {
			FacesMessage message = new FacesMessage();
			message.setSummary("LoginValidator.validate error: " + e.getMessage());
			throw new ValidatorException(message);
		}
	}
}