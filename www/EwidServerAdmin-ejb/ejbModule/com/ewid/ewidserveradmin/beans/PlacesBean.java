package com.ewid.ewidserveradmin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ewid.ewidmanagers.client.ejb.local.PlacesManagerLocal;
import com.ewid.ewidmanagers.client.struct.Place;
import com.ewid.ewidserveradmin.InjectManagers;
import com.ewid.ewidserveradmin.Manager;

@AutoCreate
@Name("placesBean")
@InjectManagers
@Scope(ScopeType.PAGE)
public class PlacesBean implements Serializable {

	private static final long serialVersionUID = -7009484243383625766L;

	private Logger logger = Logger.getLogger(PlacesBean.class);
	
	@Manager
	private PlacesManagerLocal placesManager;
	
	private List<Place> places;
	private Place placeToDelete;
	private List<Place> placeToAddFake;
	private Place placeToAdd;
	private Place placeToEdit;
	
	@Create
	public void onCreate() {
		logger.info("PlacesBean.onCreate");
		retrievePlaces();
		placeToAddFake = new ArrayList<Place>();
		placeToAddFake.add(new Place());
		placeToAdd = new Place();
	}
	
	private void retrievePlaces() {
		logger.info("PlacesBean.retrievePlaces");
		places = placesManager.getPlaces();
	}
	
	public void deletePlace() {
		logger.info("PlacesBean.deletePlace");
		placesManager.deletePlace(placeToDelete);
		retrievePlaces();
		placeToDelete = new Place();
	}
	
	public void addPlace(Place place) {
		logger.info("PlacesBean.addPlace");
		placesManager.addPlace(placeToAdd);
		retrievePlaces();
		placeToAdd = new Place();
	}
	
	public void editPlace(Place place) {
		logger.info("PlacesBean.editPlace");
		placesManager.editPlace(place);
		retrievePlaces();
		placeToEdit = new Place();
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public Place getPlaceToDelete() {
		return placeToDelete;
	}

	public void setPlaceToDelete(Place placeToDelete) {
		this.placeToDelete = placeToDelete;
	}

	public List<Place> getPlaceToAddFake() {
		return placeToAddFake;
	}

	public void setPlaceToAddFake(List<Place> placeToAddFake) {
		this.placeToAddFake = placeToAddFake;
	}

	public Place getPlaceToAdd() {
		return placeToAdd;
	}

	public void setPlaceToAdd(Place placeToAdd) {
		this.placeToAdd = placeToAdd;
	}

	public Place getPlaceToEdit() {
		return placeToEdit;
	}

	public void setPlaceToEdit(Place placeToEdit) {
		this.placeToEdit = placeToEdit;
	}
}
