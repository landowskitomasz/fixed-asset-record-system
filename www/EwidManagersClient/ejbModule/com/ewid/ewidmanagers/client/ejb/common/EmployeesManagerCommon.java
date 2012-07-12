package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Employee;

public interface EmployeesManagerCommon {
	public List<Employee> getEmployees() throws EwidGetListException;
	public void addEmployee(Employee employee) throws EwidAddException;
	public void deleteEmployee(int employeeId) throws EwidDeleteException;
	public void editEmployee(Employee employee) throws EwidEditException;
	public Employee getEmployeeById(int employeeId) throws EwidGetObjectException;
}
