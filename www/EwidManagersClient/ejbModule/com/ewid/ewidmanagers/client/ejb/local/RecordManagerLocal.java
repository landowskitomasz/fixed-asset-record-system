package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.RecordManagerCommon;

@Local
public interface RecordManagerLocal extends RecordManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/RecordManagerLocal";
}
