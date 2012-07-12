package com.ewid.ewidserveradmin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;

import com.ewid.ewidmanagers.client.ejb.local.EmployeesManagerLocal;
import com.ewid.ewidmanagers.client.struct.Employee;

public class EmployeesConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		int employeesId = Integer.parseInt(arg2);
		try {
			InitialContext ctx = new InitialContext();
			EmployeesManagerLocal manager = (EmployeesManagerLocal) ctx.lookup("java:ejb/EwidManagers/local/EmployeesManagerLocal");
			return manager.getEmployeeById(employeesId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Employee employee = (Employee) arg2;
		return employee.getId() + "";
	}

}
