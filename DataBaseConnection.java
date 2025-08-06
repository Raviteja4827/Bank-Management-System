package com.bank.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection 
{
	public static Connection forMySqlConnection() throws ClassNotFoundException,SQLException
	{
	Class.forName("com.mysql.cj.jdbc.Driver");
	return DriverManager.getConnection("jdbc:mysql://localhost:3306/e8_bank_management_system?user=root&password=2704");
	}
}