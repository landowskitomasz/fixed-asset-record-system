package com.ewid.ewidmanagers.client.ejb.common;

import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;

public interface EventsManagerCommon {
	public void raiseAddEvent(ObjectType objectType, Action action) throws EwidAddException;
}
