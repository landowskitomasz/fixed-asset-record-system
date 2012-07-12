package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Mapping;

public interface MappingsManagerCommon {
	public List<Mapping> getMappings() throws EwidGetListException;
	public void addMapping(Mapping mapping) throws EwidAddException;
	public void deleteMapping(int mappingId) throws EwidDeleteException;
	public void editMapping(Mapping mapping) throws EwidEditException;
	public void getMappingById(int mappingId) throws EwidGetObjectException;
}
