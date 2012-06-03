package com.hajland.logic;

import com.hajland.models.Mapping;

public class ConflictItem {
	private Mapping mapping;
	
	private String itemHeader;

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public void setItemHeader(String itemHeader) {
		this.itemHeader = itemHeader;
	}
	
	@Override
	public String toString()
	{
		return this.itemHeader;
	}
}
