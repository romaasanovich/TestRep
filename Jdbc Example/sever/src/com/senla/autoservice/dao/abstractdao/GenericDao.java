package com.senla.autoservice.dao.abstractdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.senla.autoservice.api.bean.AEntity;

public abstract class GenericDao<T extends AEntity> {
	public static final String ERROR = "Error!!!";
	public static final String SUCCESS= "Success";
	
	public static final String GET_BY_ID = "SELECT * FROM mydb.? where id= ?;";
	public static final String GET_BY_ID_WITH_MASTERS = "select * FROM mydb.work JOIN mydb.master ON mydb.work.idMaster = mydb.master.id";

	public abstract PreparedStatement prepareStmtForGetById(int id, Connection con) throws SQLException;
	public abstract PreparedStatement prepareStmtToAdd(Connection con,T t) throws SQLException;
	public abstract T parseFromSql(ResultSet rs) throws SQLException;

	public T getById(int id, Connection con) throws SQLException {
		T entity = null;
		PreparedStatement pstmt = prepareStmtForGetById(id, con);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			entity = parseFromSql(rs);
		}
		pstmt.close();
		return entity;
	}
	
	public String add(T entity,Connection con) throws SQLException {
		try(PreparedStatement pstmt = prepareStmtToAdd(con, entity)) {
		pstmt.executeUpdate();
		return SUCCESS;
		}
		catch(SQLException ex) {
			return ERROR;
		}
	}

}
