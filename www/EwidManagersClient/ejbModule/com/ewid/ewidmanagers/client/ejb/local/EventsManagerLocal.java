package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.EventsManagerCommon;

@Local
public interface EventsManagerLocal extends EventsManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/EventsManagerLocal";
}
