package com.senla.autoservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.senla.autoservice.bean.Place;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import com.senla.autoservice.utills.Convert;

public class GarageDao extends GenericDao<Place> {

	public static final String ADD_PLACE = "INSERT INTO `mydb`.`place` (`placeName`, `isBusy`) VALUES (?,?)";
	public static final String UPDATE_PLACE= "UPDATE `mydb`.`place` SET `placeName`='?', `isBusy`='?' WHERE `id`='?'";
	public static final String GET_SORTED_PLACES = "select * from mydb.place group by (?)";
	public static final String GET_FREE_PLACES = "select * from mydb.place where mydb.place.isBusy = 0";
	


	public GarageDao() {

	}


	
	public void changeBusying(Connection con,boolean busying,int id) throws SQLException {
		Place place = getById(id, con);
		PreparedStatement update = (PreparedStatement) con.prepareStatement(UPDATE_PLACE);
		update.setInt(0, place.getId());
		update.setString(1, place.getName());
		update.setInt(2, Convert.fromBooleanToIntSQL(busying));
		update.executeUpdate();
		update.clearParameters();
		update.close();
	}


	public ArrayList<Place> getSortedPlaces(Connection con, String comp) throws SQLException {
		ArrayList<Place> places = new ArrayList<Place>();
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(GET_SORTED_PLACES);
		pstmt.setString(1, comp);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			places.add(parseFromSql(rs));
		}
		pstmt.close();
		return places;
	}

	public ArrayList<Place> getFreePlaces(Connection con) throws SQLException {
		ArrayList<Place> places = new ArrayList<Place>();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(GET_FREE_PLACES);
		while (rs.next()) {
			places.add(parseFromSql(rs));
		}
		st.close();
		return places;
	}

	

	@Override
	public PreparedStatement prepareStmtForGetById(int id,Connection con) throws SQLException {
		PreparedStatement  pstmt= con.prepareStatement(GET_BY_ID);
		pstmt.setString(0, "place");
		pstmt.setInt(1, id);
		return pstmt;
	}

	@Override
	public Place parseFromSql(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nameOfPlace = rs.getString("placeName");
		boolean isBusy = Convert.fromIntToBooleanSQL(rs.getString("isBusy"));
		Place place = new Place(id, nameOfPlace, isBusy);
		return place;
	}



	@Override
	public PreparedStatement prepareStmtToAdd(Connection con, Place place) throws SQLException {
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(ADD_PLACE);
		ps.setString(1, place.getName());
		ps.setInt(2, 0);
		return ps;
	}


}
