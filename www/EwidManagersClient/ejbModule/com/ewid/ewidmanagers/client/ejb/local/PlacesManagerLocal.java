package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.PlacesManagerCommon;

@Local
public interface PlacesManagerLocal extends PlacesManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/PlacesManagerLocal";
}
