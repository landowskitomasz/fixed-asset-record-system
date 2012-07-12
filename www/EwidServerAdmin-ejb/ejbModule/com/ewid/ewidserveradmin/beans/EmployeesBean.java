package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidmanagers.client.ejb.local.EmployeesManagerLocal;
import com.ewid.ewidmanagers.client.struct.Employee;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@AutoCreate
@Name("employeesBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class EmployeesBean implements Serializable {

	private static final long serialVersionUID = -2516398688684245896L;

	private Logger logger = Logger.getLogger(EmployeesBean.class);
	
	@Manager
	private EmployeesManagerLocal employeesManager;
	
	private List<Employee> employees;
	private Employee employeeToDelete;
	private List<Employee> employeeToAddFake;
	private Employee employeeToAdd;
	private Employee employeeToEdit;
	
	@Create
	public void onCreate() {
		logger.info("EmployeesBean.onCreate");
		retrieveEmployees();
		employeeToAddFake = new ArrayList<Employee>();
		employeeToAddFake.add(new Employee());
		employeeToAdd = new Employee();
	}
	
	private void retrieveEmployees() {
		logger.info("EmployeesBean.retrieveEmployees");
		employees = employeesManager.getEmployees();
	}
	
	public void deleteEmployee(Employee employee) {
		logger.info("EmployeesBean.deleteEmployee");
		employeesManager.deleteEmployee(employee.getId());
		retrieveEmployees();
		employeeToDelete = new Employee();
	}
	
	public void addEmployee(Employee employee) {
		logger.info("EmployeesBean.addEmployee");
		employeesManager.addEmployee(employee);
		retrieveEmployees();
		employeeToAdd = new Employee();
	}
	
	public void editEmployee(Employee employee) {
		logger.info("EmployeesBean.deleteEmployee");
		employeesManager.editEmployee(employee);
		retrieveEmployees();
		employeeToEdit = new Employee();
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Employee> getEmployeeToAddFake() {
		return employeeToAddFake;
	}

	public void setEmployeeToAddFake(List<Employee> employeeToAddFake) {
		this.employeeToAddFake = employeeToAddFake;
	}

	public Employee getEmployeeToAdd() {
		return employeeToAdd;
	}

	public void setEmployeeToAdd(Employee employeeToAdd) {
		this.employeeToAdd = employeeToAdd;
	}

	public Employee getEmployeeToDelete() {
		return employeeToDelete;
	}

	public void setEmployeeToDelete(Employee employeeToDelete) {
		this.employeeToDelete = employeeToDelete;
	}

	public Employee getEmployeeToEdit() {
		return employeeToEdit;
	}

	public void setEmployeeToEdit(Employee employeeToEdit) {
		this.employeeToEdit = employeeToEdit;
	}
}
