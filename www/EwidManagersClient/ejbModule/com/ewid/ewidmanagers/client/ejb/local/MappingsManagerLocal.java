package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.MappingsManagerCommon;

@Local
public interface MappingsManagerLocal extends MappingsManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/MappingsManagerLocal";
}
