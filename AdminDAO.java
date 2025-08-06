package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.Util.DataBaseConnection;

public class AdminDAO {
	private static final String admin_login = "select * from admindetails where emailId=? and password=?";

	public boolean selectAdminDetailsByUsingEmailAndPassword(String emailId, String password) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(admin_login);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
