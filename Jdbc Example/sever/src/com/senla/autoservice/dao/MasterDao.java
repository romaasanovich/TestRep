package com.senla.autoservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.senla.autoservice.bean.Master;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import com.senla.autoservice.utills.Convert;

public class MasterDao extends GenericDao<Master> {
	
	public static final String ADD_MASTER = "INSERT INTO `mydb`.`master` (`nameMaster`, `isWork`) VALUES (?,?)";
	public static final String UPDATE_MASTER= "UPDATE `mydb`.`master` SET `nameMaster`='?', `isWork	`='?' WHERE `id`='?'";
	public static final String GET_MASTERS ="SELECT * FROM mydb.master order by";
	public static final String GET_MASTER_ON_ORDER =  "select mydb.master.id, mydb.master.name FROM mydb.order JOIN mydb.work JOIN mydb.master ON mydb.order.idService = mydb.work.id AND mydb.order.idMaster= mydb.master.id AND mydb.order.id =";
	
	public MasterDao() {
	}

	public void changeBusying(Connection con,boolean busying,int id) throws SQLException {
		Master master =getById(id, con);
		PreparedStatement update = (PreparedStatement) con.prepareStatement(UPDATE_MASTER);
		update.setInt(0, master.getId());
		update.setString(1, master.getName());
		update.setInt(2, Convert.fromBooleanToIntSQL(busying));
		update.executeUpdate();
		update.clearParameters();
		update.close();
	}

	public ArrayList<Master> getListOfMasters(String comp, Connection con) throws SQLException {
		ArrayList<Master> masters = new ArrayList<Master>();
		Statement st =con.createStatement();
		ResultSet rs = st.executeQuery(GET_MASTERS + "(" + comp + ")");
		while (rs.next()) {
			masters.add(parseFromSql(rs));
		}
		st.close();
		return masters;
	}

	public Master getMasterCarriedOutOrder(int idOrder, Connection con) throws SQLException {
		Master master = null;
		Statement st =con.createStatement();
		ResultSet rs = st.executeQuery(GET_MASTER_ON_ORDER + idOrder);
		while (rs.next()) {
			master = parseFromSql(rs);
		}
		st.close();
		return master;
	}



	@Override
	public PreparedStatement prepareStmtForGetById(int id,Connection con) throws SQLException {
		PreparedStatement  pstmt= con.prepareStatement(GET_BY_ID);
		pstmt.setString(0, "master");
		pstmt.setInt(1, id);	
		return pstmt;
	}

	@Override
	public Master parseFromSql(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("nameMaster");
		boolean isWork = Convert.fromIntToBooleanSQL(rs.getString("isWork"));
		Master master = new Master(id, name, isWork);
		return master;
	
	}

	@Override
	public PreparedStatement prepareStmtToAdd(Connection con, Master master) throws SQLException {
		PreparedStatement ps = con.prepareStatement(ADD_MASTER);
		ps.setString(1, master.getName());
		ps.setInt(2, 0);
		return ps;
	}
}
