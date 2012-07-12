package com.ewid.ewidmanagers.ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;

import com.ewid.ewidmanagers.client.ejb.local.EventsManagerLocal;
import com.ewid.ewidmanagers.client.ejb.local.PlacesManagerLocal;
import com.ewid.ewidmanagers.client.enums.Action;
import com.ewid.ewidmanagers.client.enums.ObjectType;
import com.ewid.ewidmanagers.client.exceptions.EwidAddException;
import com.ewid.ewidmanagers.client.exceptions.EwidDeleteException;
import com.ewid.ewidmanagers.client.exceptions.EwidEditException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetListException;
import com.ewid.ewidmanagers.client.exceptions.EwidGetObjectException;
import com.ewid.ewidmanagers.client.struct.Place;
import com.ewid.ewidmanagers.utils.DbUtils;

@Stateless
@LocalBinding(jndiBinding = PlacesManagerLocal.JNDI_NAME)
public class PlacesManager implements PlacesManagerLocal, Serializable {

	private static final long serialVersionUID = -4343910521599104582L;

	private Logger logger = Logger.getLogger(PlacesManager.class);
	
	@EJB
	private EventsManagerLocal eventsManager;
	
	@Override
	public List<Place> getPlaces() throws EwidGetListException {
		logger.info("PlacesManager.getPlaces");
		
		List<Place> resultList = new ArrayList<Place>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, country, province, city, postal_code, street, building, floor, room_number, synchronized from places";
			logger.debug("PlacesManager.getPlaces sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Place place = new Place();
				
				place.setId(rs.getInt("id"));
				place.setCountry(rs.getString("country"));
				place.setProvince(rs.getString("province"));
				place.setCity(rs.getString("city"));
				place.setPostalCode(rs.getString("postal_code"));
				place.setStreet(rs.getString("street"));
				place.setBuilding(rs.getString("building"));
				place.setFloor(rs.getInt("floor"));
				place.setRoomNumber(rs.getString("room_number"));
				place.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
				
				resultList.add(place);
			}
		} catch (Exception e) {
			logger.error("PlacesManager.getPlaces error", e);
			throw new EwidGetListException("PlacesManager.getPlaces error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultList;
	}
	
	@Override
	public void deletePlace(Place place) throws EwidDeleteException {
		logger.info("PlacesManager.delete place=" + place);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select place_id from mappings where place_id = ?";
			logger.debug("PlacesManager.delete sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, place.getId());
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				throw new EwidDeleteException("Lokalizacja jest wykorzystywana w mapowaniu!");
			}
			
			sql = "delete from places where id = ?";
			logger.debug("PlacesManager.delete sql=" + sql);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, place.getId());
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.LOCALIZATION, Action.DELETE);
		} catch (Exception e) {
			logger.error("PlacesManager.delete error", e);
			throw new EwidDeleteException("PlacesManager.delete error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
	}
	
	@Override
	public void addPlace(Place place) throws EwidAddException {
		logger.info("PlacesManager.addPlace place=" + place);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "insert into places (country, province, city, postal_code, street, building, floor, room_number, synchronized, guid) "
						+ "values (?,?,?, ?,?,?, ?,?,?, ?)";
			logger.debug("PlacesManager.addPlace sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, place.getCountry());
			stmt.setString(2, place.getProvince());
			stmt.setString(3, place.getCity());
			stmt.setString(4, place.getPostalCode());
			stmt.setString(5, place.getStreet());
			stmt.setString(6, place.getBuilding());
			stmt.setInt(7, place.getFloor());
			stmt.setString(8, place.getRoomNumber());
			stmt.setInt(9, 0);
			stmt.setString(10, UUID.randomUUID().toString());
			
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.LOCALIZATION, Action.ADD);
		} catch (Exception e) {
			logger.error("PlacesManager.addPlace error", e);
			throw new EwidAddException("PlacesManager.addPlace error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
	}

	@Override
	public void editPlace(Place place) throws EwidEditException {
		logger.info("PlacesManager.editPlace place=" + place);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "update places set country = ?, province = ?, city = ?, postal_code = ?, street = ?, building = ?, floor = ?, room_number = ?, synchronized = 0 where id = ?";
			logger.debug("PlacesManager.editPlace sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, place.getCountry());
			stmt.setString(2, place.getProvince());
			stmt.setString(3, place.getCity());
			stmt.setString(4, place.getPostalCode());
			stmt.setString(5, place.getStreet());
			stmt.setString(6, place.getBuilding());
			stmt.setInt(7, place.getFloor());
			stmt.setString(8, place.getRoomNumber());
			stmt.setInt(9, place.getId());
						
			stmt.execute();
			
			eventsManager.raiseAddEvent(ObjectType.LOCALIZATION, Action.EDIT);
		} catch (Exception e) {
			logger.error("PlacesManager.editPlace error", e);
			throw new EwidEditException("PlacesManager.editPlace error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeStatement(stmt);
		}
		
	}

	@Override
	public Place getPlaceById(int placeId) throws EwidGetObjectException {
		logger.info("PlacesManager.getPlaceById placeId=" + placeId);
		
		Place resultPlace = new Place();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DbUtils.getDbConnection();
			
			String sql = "select id, country, province, city, postal_code, street, building, floor, room_number, synchronized from places where id = ?";
			logger.debug("PlacesManager.getPlaceById sql=" + sql);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, placeId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				resultPlace.setId(rs.getInt("id"));
				resultPlace.setCountry(rs.getString("country"));
				resultPlace.setProvince(rs.getString("province"));
				resultPlace.setCity(rs.getString("city"));
				resultPlace.setPostalCode(rs.getString("postal_code"));
				resultPlace.setStreet(rs.getString("street"));
				resultPlace.setBuilding(rs.getString("building"));
				resultPlace.setFloor(rs.getInt("floor"));
				resultPlace.setRoomNumber(rs.getString("room_number"));
				resultPlace.setSynchronizedWithMobeelizer(rs.getBoolean("synchronized"));
			}
		} catch (Exception e) {
			logger.error("PlacesManager.getPlaceById error", e);
			throw new EwidGetObjectException("PlacesManager.getPlaceById error",e);
		} finally {
			DbUtils.closeConnection(conn);
			DbUtils.closeResultSet(rs);
			DbUtils.closeStatement(stmt);
		}
		
		return resultPlace;
	}
}
