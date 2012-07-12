package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.ReportsManagerCommon;

@Local
public interface ReportsManagerLocal extends ReportsManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/ReportsManagerLocal";
}
