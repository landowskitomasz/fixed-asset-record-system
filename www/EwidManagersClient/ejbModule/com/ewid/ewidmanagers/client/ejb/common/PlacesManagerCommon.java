package com.ewid.ewidmanagers.client.ejb.common;

import java.util.List;

import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Place;

public interface PlacesManagerCommon {
	public List<Place> getPlaces() throws EwidGetListException;
	public void deletePlace(Place place) throws EwidDeleteException;
	public void addPlace(Place place) throws EwidAddException;
	public void editPlace(Place place) throws EwidEditException;
	public Place getPlaceById(int placeId) throws EwidGetObjectException;
}
