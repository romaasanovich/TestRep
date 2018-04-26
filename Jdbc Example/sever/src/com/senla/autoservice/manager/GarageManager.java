package com.senla.autoservice.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.senla.autoservice.DBConnector.DBConnector;
import com.senla.autoservice.api.manager.IGarageManager;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.csvimporexport.CsvExportImport;
import com.senla.autoservice.dao.GarageDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;

public class GarageManager implements IGarageManager {

	public static final String ADD_PLACE = "INSERT INTO `mydb`.`place` (`placeName`, `isBusy`) VALUES (?,?)";
	public static final String UPDATE_PLACE = "UPDATE `mydb`.`place` SET `placeName`='?', `isBusy`='?' WHERE `id`='?'";

	CsvExportImport<Place> importerExporterPlaces = new CsvExportImport<Place>();
	private GarageDao places;
	private DBConnector con;
	private CsvExportImport<Place> importExport;

	public GarageManager() throws SQLException {
		con = DBConnector.getInstance();
		places = new GarageDao();
	}

	public GarageDao getPlaceDao() {
		return places;
	}
	
	public ArrayList<Place> getSortedPlaces(String comp) throws SQLException {
		return places.getSortedPlaces(con.getConnnection(), comp);
	}

	public ArrayList<Place> getFreePlaces() throws SQLException {
		return places.getFreePlaces(con.getConnnection());
	}

	public String add(String name) throws SQLException {
		Place place = new Place(null, name);
		return places.add(place, con.getConnnection());
	}

	public void changeBusying(int id) throws SQLException {
		places.changeBusying(con.getConnnection(), false, id);
	}

	@Override
	public void exportFromCSV() throws SQLException, IOException {
		boolean isNew = false;
		PreparedStatement update = con.getConnnection().prepareStatement(UPDATE_PLACE);
		PreparedStatement add = con.getConnnection().prepareStatement(ADD_PLACE);
		ArrayList<Place> dbData = places.getSortedPlaces(con.getConnnection(), "id");
		ArrayList<Place> csvData = readFromCSV();
		for (Place csvPlace : csvData) {
			isNew = true;
			for (Place sqlPlace : dbData) {
				if (csvPlace.getId() == sqlPlace.getId()) {
					update.setInt(3, csvPlace.getId());
					update.setString(1, csvPlace.getName());
					update.setInt(2, Convert.fromBooleanToIntSQL(csvPlace.getIsBusy()));
					update.executeUpdate();
					update.clearParameters();
					isNew = false;
					break;
				}
			}
			if (isNew == true) {
				add.setInt(3, csvPlace.getId());
				add.setString(1, csvPlace.getName());
				add.setInt(2, Convert.fromBooleanToIntSQL(csvPlace.getIsBusy()));
				add.executeUpdate();
				add.clearParameters();
			}
		}
		add.close();
		update.close();
	}

	private ArrayList<Place> readFromCSV() throws IOException {
		ArrayList<Place> csvData = new ArrayList<>();
		FileReader fR = new FileReader(new File(Prop.getProp("placeCsvPath")));
		Scanner sc = new Scanner(fR);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			csvData.add(Convert.fromStrToPlace(s));
		}
		sc.close();
		return csvData;
	}

	@Override
	public void importToCSV() throws Exception {
		ArrayList<Place> placeList = places.getSortedPlaces(con.getConnnection(), "id");
		String path = Prop.getProp("placeCsvPath");
		importExport.importToCsv(placeList, path);
	}

}
