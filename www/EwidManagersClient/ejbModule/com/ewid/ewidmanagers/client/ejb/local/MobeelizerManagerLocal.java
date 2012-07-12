package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.MobeelizerManagerCommon;

@Local
public interface MobeelizerManagerLocal extends MobeelizerManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/MobeelizerManagerLocal";
}
