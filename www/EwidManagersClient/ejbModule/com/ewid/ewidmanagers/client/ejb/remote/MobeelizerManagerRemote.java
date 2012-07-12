package com.ewid.ewidmanagers.client.ejb.remote;

import javax.ejb.Remote;

import com.ewid.ewidmanagers.client.ejb.common.MobeelizerManagerCommon;

@Remote
public interface MobeelizerManagerRemote extends MobeelizerManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/remote/MobeelizerManagerRemote";
}
