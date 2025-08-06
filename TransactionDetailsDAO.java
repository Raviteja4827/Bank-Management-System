package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import com.bank.DataTransferObject.*;
import com.bank.Util.DataBaseConnection;

public class TransactionDetailsDAO {
	private static final String transation_details = "insert into transation_details(transation_type,transation_amount,transation_time,transation_date,balance_amount,transation_status,customer_account_number) values (?,?,?,?,?,?,?)";
	private static final String select_transaction_details = "SELECT * FROM transation_details WHERE customer_account_number=?";
	public boolean insertTransactionDetails(TransactionDetails transactionDetails) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(transation_details);
			preparedStatement.setString(1, transactionDetails.getTransactiontype());
			preparedStatement.setDouble(2, transactionDetails.getTransactionamount());
			preparedStatement.setTime(3, Time.valueOf(transactionDetails.getTransactiontime()));
			preparedStatement.setDate(4, Date.valueOf(transactionDetails.getTransactiondate()));
			preparedStatement.setDouble(5, transactionDetails.getBalanceamount());
			preparedStatement.setString(6, transactionDetails.getTransactionstatus());
			preparedStatement.setLong(7, transactionDetails.getCustomeraccountnumber());
			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean selectTransactionDetails(int accountNumber) {
	    try {
	        Connection connection = DataBaseConnection.forMySqlConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(select_transaction_details);
	        preparedStatement.setLong(1, accountNumber); // long because account number is bigint
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.isBeforeFirst()) {
	            System.out.println("========== Transaction Statement ==========");
	            System.out.printf("%-5s %-10s %-10s %-12s %-10s %-15s %-15s\n", 
	                              "ID", "TYPE", "AMOUNT", "DATE", "TIME", "BALANCE", "STATUS");

	            while (resultSet.next()) {
	                int id = resultSet.getInt("transation_id");
	                String type = resultSet.getString("transation_type");
	                double amount = resultSet.getDouble("transation_amount");
	                String date = resultSet.getString("transation_date");
	                String time = resultSet.getString("transation_time");
	                double balance = resultSet.getDouble("balance_amount");
	                String status = resultSet.getString("transation_status");

	                System.out.printf("%-5d %-10s %-10.2f %-12s %-10s %-15.2f %-15s\n", 
	                                  id, type, amount, date, time, balance, status);
	                try {
	                    Thread.sleep(2000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	            }
	            return true;
	        } else {
	            System.out.println("No transactions found for account: " + accountNumber);
	            return false;
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    return false;
	    }
	}

	}