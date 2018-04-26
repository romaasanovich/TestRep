package com.senla.autoservice.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.senla.autoservice.DBConnector.DBConnector;
import com.senla.autoservice.api.manager.IMasterManager;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.csvimporexport.CsvExportImport;
import com.senla.autoservice.dao.MasterDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;

public class MasterManager implements IMasterManager {

	public static final String ADD_MASTER = "INSERT INTO `mydb`.`master` (`nameMaster`, `isWork`) VALUES (?,?)";
	public static final String UPDATE_MASTER= "UPDATE `mydb`.`master` SET `nameMaster`='?', `isWork	`='?' WHERE `id`='?'";
	
	
	private CsvExportImport<Master> importExport;
	private MasterDao masters;
	private DBConnector con;

	public MasterManager() throws SQLException {
		masters = new MasterDao();
		con = DBConnector.getInstance();
	}
	
	public MasterDao getMasterDao() {
		return masters;
	}

	public ArrayList<Master> getSortedMasters(String comp) throws SQLException {
		return masters.getListOfMasters(comp, con.getConnnection());
	}

	public Master getMasterCarriedOutCurrentOrder(int idOrder) throws SQLException {
		return masters.getMasterCarriedOutOrder(idOrder, con.getConnnection());
	}

	public String add(String name) throws SQLException {
		Master master = new Master(null, name, false);
		return masters.add(master, con.getConnnection());
	}

	public void changeBusying(int id) throws SQLException {
		masters.changeBusying(con.getConnnection(), false, id);
	}

	private ArrayList<Master> readFromCSV() throws IOException {
		ArrayList<Master> csvData = new ArrayList<>();
		FileReader fR = new FileReader(new File(Prop.getProp("masterCsvPath")));
		Scanner sc = new Scanner(fR);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			csvData.add(Convert.fromStrToMaster(s));
		}
		sc.close();
		return csvData;
	}

	@Override
	public void exportFromCSV() throws SQLException, IOException {
		boolean isNew = false;
		PreparedStatement update = (PreparedStatement) con.getConnnection().prepareStatement(UPDATE_MASTER);
		PreparedStatement add = (PreparedStatement) con.getConnnection().prepareStatement(ADD_MASTER);
		ArrayList<Master> dbData = masters.getListOfMasters("id", con.getConnnection());
		ArrayList<Master> csvData = readFromCSV();
		for (Master csvMaster : csvData) {
			isNew = true;
			for (Master sqlMaster : dbData) {
				if (csvMaster.getId() == sqlMaster.getId()) {
					update.setInt(3, csvMaster.getId());
					update.setString(1, csvMaster.getName());
					update.setInt(2, Convert.fromBooleanToIntSQL(csvMaster.getIsWork()));
					update.executeUpdate();
					update.clearParameters();
					isNew = false;
					break;
				}
			}
			if (isNew == true) {
				add.setInt(3, csvMaster.getId());
				add.setString(1, csvMaster.getName());
				add.setInt(2, Convert.fromBooleanToIntSQL(csvMaster.getIsWork()));
				add.executeUpdate();
				add.clearParameters();
			}
		}
		update.close();
		add.close();
	}

	@Override
	public void importToCSV() throws SQLException, IOException {
		ArrayList<Master> masterList = masters.getListOfMasters("id", con.getConnnection());
		String path = Prop.getProp("masterCsvPath");
		importExport.importToCsv(masterList, path);
	}
}
