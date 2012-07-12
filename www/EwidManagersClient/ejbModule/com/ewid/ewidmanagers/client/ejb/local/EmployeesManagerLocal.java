package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.EmployeesManagerCommon;

@Local
public interface EmployeesManagerLocal extends EmployeesManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/EmployeesManagerLocal";
}
