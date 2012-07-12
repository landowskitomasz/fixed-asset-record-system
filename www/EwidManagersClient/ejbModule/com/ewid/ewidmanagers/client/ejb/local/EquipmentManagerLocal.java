package com.ewid.ewidmanagers.client.ejb.local;

import javax.ejb.Local;

import com.ewid.ewidmanagers.client.ejb.common.EquipmentManagerCommon;

@Local
public interface EquipmentManagerLocal extends EquipmentManagerCommon {
	public static final String JNDI_NAME = "java:ejb/EwidManagers/local/EquipmentsManagerLocal";
}
