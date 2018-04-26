package com.senla.autoservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Work;
import com.senla.autoservice.dao.abstractdao.GenericDao;
import com.senla.autoservice.utills.Convert;

public class WorkDao extends GenericDao<Work> {
	public static final String ADD_WORK = "INSERT INTO `mydb`.`work` (`nameOfService`, `price`, `idMaster`) VALUES (?,?,?)";
	public static final String GET_WORKS = "SELECT * FROM mydb.work;";
	

	public WorkDao() {

	}

	public ArrayList<Work> getListOfWorks(Connection con) throws SQLException {
		ArrayList<Work> orders = new ArrayList<Work>();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(GET_WORKS);
		while (rs.next()) {
			orders.add(parseFromSql(rs));
		}
		st.close();
		return orders;
	}



	@Override
	public PreparedStatement prepareStmtForGetById(int id, Connection con) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(GET_BY_ID_WITH_MASTERS);
		pstmt.setString(0, "order");
		pstmt.setInt(1, id);
		return pstmt;
	}

	@Override
	public Work parseFromSql(ResultSet rs) throws SQLException {
		Master master = new Master(rs.getInt("idMaster"), rs.getString("name"),
				Convert.fromIntToBooleanSQL(rs.getString("isWork")));
		return new Work(rs.getInt("idService"), rs.getString("nameOfService"), rs.getDouble("price"), master);
	}

	@Override
	public PreparedStatement prepareStmtToAdd(Connection con, Work work) throws SQLException {
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(ADD_WORK);
		ps.setString(1, work.getNameOfService());
		ps.setDouble(2, work.getPrice());
		ps.setInt(3, work.getMaster().getId());
		return ps;

	}

}
