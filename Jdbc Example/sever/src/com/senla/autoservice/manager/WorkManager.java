package com.senla.autoservice.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.senla.autoservice.DBConnector.DBConnector;
import com.senla.autoservice.api.manager.IWorkManager;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.csvimporexport.CsvExportImport;
import com.senla.autoservice.dao.WorkDao;
import com.senla.autoservice.properties.Prop;
import com.senla.autoservice.utills.Convert;

public class WorkManager implements IWorkManager {

	private static final String WORK_WAS_SUCCESFUL_ADDED = "Work was succesful added";
	public static final String ADD_WORK = "INSERT INTO `mydb`.`work` (`nameOfService`, `price`, `idMaster`) VALUES (?,?,?)";
	public static final String UPDATE_WORK = "UPDATE `mydb`.`work` SET `nameOfService`='?', `price`='?', `idMaster`='?' WHERE `id`='?'";

	private CsvExportImport<Work> importExport;
	private WorkDao works;
	private DBConnector con;

	public WorkManager() throws SQLException {
		works = new WorkDao();
		con = DBConnector.getInstance();
	}

	public WorkDao getWorks() {
		return works;
	}

	public String add(String name, double price, int idMaster) throws SQLException {
		String message;
		Master master = new MasterManager().getMasterDao().getById(idMaster, con.getConnnection());
		Work work = new Work(null, name, price, master);
		works.add(work, con.getConnnection());
		message = WORK_WAS_SUCCESFUL_ADDED;
		return message;
	}

	private static ArrayList<Work> readFromCSV() throws IOException {
		ArrayList<Work> csvData = new ArrayList<>();
		FileReader fR = new FileReader(new File(Prop.getProp("workCsvPath")));
		Scanner sc = new Scanner(fR);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			csvData.add(Convert.fromStrToWork(s));
		}
		sc.close();
		return csvData;
	}

	@Override
	public void exportFromCSV() throws SQLException, IOException {
		boolean isNew = false;
		PreparedStatement update = (PreparedStatement) con.getConnnection().prepareStatement(UPDATE_WORK);
		PreparedStatement add = (PreparedStatement) con.getConnnection().prepareStatement(ADD_WORK);
		ArrayList<Work> dbData = works.getListOfWorks(con.getConnnection());
		ArrayList<Work> csvData = readFromCSV();
		for (Work csvWork : csvData) {
			isNew = true;
			for (Work sqlWork : dbData) {
				if (csvWork.getId() == sqlWork.getId()) {
					update.setInt(4, csvWork.getId());
					update.setString(1, csvWork.getNameOfService());
					update.setDouble(2, csvWork.getPrice());
					update.setInt(3, csvWork.getMaster().getId());
					update.executeUpdate();
					update.clearParameters();
					isNew = false;
					break;
				}
			}
			if (isNew == true) {
				add.setInt(4, csvWork.getId());
				add.setString(1, csvWork.getNameOfService());
				add.setDouble(2, csvWork.getPrice());
				add.setInt(3, csvWork.getMaster().getId());
				add.executeUpdate();
				add.clearParameters();
			}
		}
		update.close();
		add.close();
	}

	@Override
	public void importToCSV() throws SQLException, IOException {
		ArrayList<Work> workList = works.getListOfWorks(con.getConnnection());
		String path = Prop.getProp("workCsvPath");
		importExport.importToCsv(workList, path);
	}

}
