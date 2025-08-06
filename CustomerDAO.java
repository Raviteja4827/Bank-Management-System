package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.DataTransferObject.CustomerDetails;
import com.bank.Util.DataBaseConnection;

public class CustomerDAO {

	private final static String insert = "insert into customer_details(Customer_Name, Customer_EmailId, Customer_Mobile_Number, Customer_Aadhar_Number, Customer_Address, Customer_Gender, Customer_Amount) values (?,?,?,?,?,?,?)";
	private final static String select_all_customer_details = "select * from customer_details";
	private final static String select_all_customer_details_where_Customer_Status_Pending = "select * from customer_details where Customer_Status='pending'";
	private final static String customer_Login = "SELECT * FROM customer_details WHERE (Customer_EmailId = ? OR Customer_Account_Number = ?) AND Customer_PIN = ? AND (Customer_Status = 'active') ";
	private final static String select_all_customer_details_where_Customer_Status_Closing = "SELECT * FROM customer_details WHERE Customer_Status = 'closingprocess'";

	public boolean insertCustomerDetails(CustomerDetails customerDetails) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, customerDetails.getName());
			preparedStatement.setString(2, customerDetails.getEmailId());
			preparedStatement.setLong(3, customerDetails.getMobileNumber());
			preparedStatement.setLong(4, customerDetails.getAadharNumber());
			preparedStatement.setString(5, customerDetails.getAddress());
			preparedStatement.setString(6, customerDetails.getGender());
			preparedStatement.setDouble(7, customerDetails.getAmount());
			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<CustomerDetails> getAllCustomerDetails() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(select_all_customer_details);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<CustomerDetails> listOfCustomerDetails = new ArrayList<CustomerDetails>();
			if (resultSet.isBeforeFirst()) {
				while (resultSet.next()) {
					CustomerDetails cuDetails = new CustomerDetails();
					cuDetails.setName(resultSet.getString("Customer_Name"));
					cuDetails.setAccountNumber(resultSet.getInt("Customer_Account_Number"));
					cuDetails.setEmailId(resultSet.getString("Customer_EmailId"));
					cuDetails.setAadharNumber(resultSet.getLong("Customer_Aadhar_Number"));
					cuDetails.setMobileNumber(resultSet.getLong("Customer_Mobile_Number"));
					cuDetails.setPin(resultSet.getInt("Customer_PIN"));
					cuDetails.setAmount(resultSet.getDouble("Customer_Amount"));
					listOfCustomerDetails.add(cuDetails);
				}
				return listOfCustomerDetails;

			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<CustomerDetails> getAccountRequestDetails() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(select_all_customer_details_where_Customer_Status_Pending);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<CustomerDetails> accountRequestDetails = new ArrayList<CustomerDetails>();
			if (resultSet.isBeforeFirst()) {
				while (resultSet.next()) {
					CustomerDetails cuDetails = new CustomerDetails();
					cuDetails.setName(resultSet.getString("Customer_Name"));
					cuDetails.setEmailId(resultSet.getString("Customer_EmailId"));
					cuDetails.setAadharNumber(resultSet.getLong("Customer_Aadhar_Number"));
					cuDetails.setMobileNumber(resultSet.getLong("Customer_Mobile_Number"));
					accountRequestDetails.add(cuDetails);
				}
				return accountRequestDetails;

			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void updateAccountPinByUsingId(List<CustomerDetails> accountRequestDetails) {
		String update = "update customer_details set Customer_Account_Number=?,Customer_PIN=?,Customer_Status=? where Customer_EmailId=?";
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(update);
			for (CustomerDetails customerDetails : accountRequestDetails) {
				preparedStatement.setLong(1, customerDetails.getAccountNumber());
				preparedStatement.setLong(2, customerDetails.getPin());
				preparedStatement.setString(3, "active");
				preparedStatement.setString(4, customerDetails.getEmailId());
				preparedStatement.addBatch();
			}
			System.out.println(preparedStatement);
			int[] batch = preparedStatement.executeBatch();
			System.out.println("Updated");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CustomerDetails selectCustomerDetailsByUsingEmailOrAccountNumberAndPin(String emailidorPassword, int pin) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(customer_Login);
			preparedStatement.setString(1, emailidorPassword);
			preparedStatement.setString(2, emailidorPassword);
			preparedStatement.setInt(3, pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString("Customer_Name");
				String gender = resultSet.getString("Customer_Gender");
				CustomerDetails customerDetails = new CustomerDetails();
				customerDetails.setName(name);
				customerDetails.setGender(gender);
				return customerDetails;
			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateAmountByUsingAccountPin(int pin, double amount) {
		String updateAmountQuery = "UPDATE customer_details SET Customer_Amount = ? WHERE Customer_PIN = ?";

		boolean isUpdate = false;
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(updateAmountQuery);
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, pin);
			int rows = preparedStatement.executeUpdate();
			if (rows > 0) {
				isUpdate = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdate;
	}

	public boolean updateAccountPinByUsingAccountNumber(int accountNumber, int oldPin, int newPin) {
		String updateQuery = "UPDATE customer_details SET Customer_PIN = ? WHERE Customer_Account_Number = ? AND Customer_PIN = ?";
		boolean isUpdated = false;
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement ps = connection.prepareStatement(updateQuery);

			ps.setInt(1, newPin);
			ps.setInt(2, accountNumber);
			ps.setInt(3, oldPin);

			int rows = ps.executeUpdate();
			if (rows > 0) {
				isUpdated = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public boolean requestToCloseAccount(int accNum, int pin) {
		try {
			Connection con = DataBaseConnection.forMySqlConnection();
			PreparedStatement ps = con.prepareStatement(
					"UPDATE customer_details SET Customer_Status='closingprocess' WHERE Customer_Account_Number=? AND Customer_PIN=?");
			ps.setInt(1, accNum);
			ps.setInt(2, pin);
			int result = ps.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<CustomerDetails> getAccountClosingRequestDetails() {
		List<CustomerDetails> accountRequestDetails = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(select_all_customer_details_where_Customer_Status_Closing);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CustomerDetails cuDetails = new CustomerDetails();
				cuDetails.setName(resultSet.getString("Customer_Name"));
				cuDetails.setAccountNumber(resultSet.getLong("Customer_Account_Number"));
				cuDetails.setAadharNumber(resultSet.getLong("Customer_Aadhar_Number"));
				cuDetails.setMobileNumber(resultSet.getLong("Customer_Mobile_Number"));
				accountRequestDetails.add(cuDetails);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return accountRequestDetails;
	}

	public void closeAllAccountsWithClosingStatus() {
		try {
			Connection con = DataBaseConnection.forMySqlConnection();
			PreparedStatement ps = con.prepareStatement(
					"UPDATE customer_details SET Customer_Status='closed' WHERE Customer_Status='closingprocess'");
			int result = ps.executeUpdate();

			if (result > 0) {
				System.out.println(result + "account closed successfully.");
			} else {
				System.out.println(" No closing requests found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}