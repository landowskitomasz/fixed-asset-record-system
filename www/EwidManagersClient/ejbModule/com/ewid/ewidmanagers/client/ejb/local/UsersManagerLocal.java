package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.UsersManagerCommon;

@Local
public interface UsersManagerLocal extends UsersManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/UsersManagerLocal";
}
