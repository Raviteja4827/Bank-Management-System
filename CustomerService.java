package com.bank.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DataTransferObject.CustomerDetails;
import com.bank.DataTransferObject.TransactionDetails;
import com.bank.Exception.CustomerDetailsInvalidException;
import com.bank.DAO.CustomerDAO;
import com.bank.DAO.TransactionDetailsDAO;
import com.bank.DataTransferObject.*;

public class CustomerService {
	CustomerDAO customerDAO = new CustomerDAO();
	TransactionDetailsDAO transactionDetailsDAO = new TransactionDetailsDAO();
	Scanner sc = new Scanner(System.in);

	// Name validation method
	public static boolean nameValidation(String name) {
		return name != null && name.matches("[A-Za-z ]{3,}");
	}

	public void customerRegistration() {
		List<CustomerDetails> allCustomerDetails = customerDAO.getAllCustomerDetails();
		CustomerDetails customerDetails = new CustomerDetails();

		// Name
		while (true) {
			try {
				System.out.println("Enter Customer Name: ");
				String name = sc.nextLine();
				if (nameValidation(name)) {
					customerDetails.setName(name);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Name");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Name");
			}
		}

		// Email
		while (true) {
			try {
				System.out.println("Enter Customer EmailId: ");
				String emailId = sc.next();
				if (emailId.endsWith("@gmail.com")) {
					customerDetails.setEmailId(emailId);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Email ID");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer EmailId");
			}
		}

		// Mobile Number
		while (true) {
			try {
				System.out.println("Enter Customer Mobile Number: ");
				long mobileNumber = sc.nextLong();
				if (mobileNumber >= 6000000000L && mobileNumber <= 9999999999L) {
					customerDetails.setMobileNumber(mobileNumber);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Mobile Number");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Mobile Number");
			}
		}

		// Aadhar Number
		while (true) {
			try {
				System.out.println("Enter Customer Aadhar Number: ");
				long aadharNumber = sc.nextLong();
				if (aadharNumber >= 100000000000L && aadharNumber <= 999999999999L) {
					customerDetails.setAadharNumber(aadharNumber);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Aadhar Number");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Aadhar Number:");
			}
		}

		// Consume newline
		sc.nextLine();

		// Address
		while (true) {
			try {
				System.out.println("Enter Customer Address: ");
				String address = sc.nextLine();
				if (address.length() >= 5) {
					customerDetails.setAddress(address);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Address");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Address:");
			}
		}

		// Gender
		while (true) {
			try {
				System.out.println("Enter Customer Gender (male/female/others): ");
				String gender = sc.next();
				if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")
						|| gender.equalsIgnoreCase("others")) {
					customerDetails.setGender(gender);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Gender");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Gender:");
			}
		}

		// Amount
		while (true) {
			try {
				System.out.println("Enter Customer Amount: ");
				double amount = sc.nextDouble();
				if (amount >= 0) {
					customerDetails.setAmount(amount);
					break;
				} else {
					throw new CustomerDetailsInvalidException("❌ Invalid Amount");
				}
			} catch (CustomerDetailsInvalidException e) {
				System.out.println("🔁 Re-enter Customer Amount:");
			}
		}
		// Insert into DB
		if (customerDAO.insertCustomerDetails(customerDetails)) {
			System.out.println("✅ Data Inserted Successfully!");
		} else {
			System.out.println("❌ Server Error! Try Again.");
		}
	}

	public void customerLogin() {
		System.out.println("Enter The EmailID or Account Number:");
		String emailorPin = sc.nextLine();
		System.out.println("Enter The Password:");
		int pin = sc.nextInt();
		CustomerDetails customerDetails = customerDAO.selectCustomerDetailsByUsingEmailOrAccountNumberAndPin(emailorPin,
				pin);
		if (customerDetails != null) {
			while (true) {
				String captchaGenarate = captchaGenarate();
				System.out.println("CAPTCHA:" + captchaGenarate);
				System.out.println("Enter Captcha:");
				String captcha = sc.next();
				if (captcha.equals(captchaGenarate)) {
					System.out.println("--Login Success--");
					if (customerDetails.getGender().equalsIgnoreCase("Male")) {
						System.out.println("Hello \n Mr." + customerDetails.getName());
					} else if (customerDetails.getGender().equalsIgnoreCase("female")) {
						System.out.println("Hello \n Miss." + customerDetails.getName());
					}
					customerOperations();
					break;
				} else {
					System.err.println("Invalid Captcha");
				}
			}
		} else {
			System.out.println("Invalid Credential");
		}
	}

	public String captchaGenarate() {
		char[] ch = new char[6];
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		for (int i = 0; i < ch.length; i++) {
			ch[i] = characters.charAt(random.nextInt(characters.length()));
		}
		String genarateCaptcha = new String(ch);
		return genarateCaptcha;
	}
	public void customerOperations() {
		boolean isStart = true;
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		while (isStart) {
			System.err.println(
					"Enter \n1.Deposit\n2.Withdrawal\n3.Check Balance\n4.Check Statement\n5.Update Pin\n6.Close Account\n7.Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				depositBalance();
				
				break;
			}
			case 2: {
				withdrawBalance();
				break;
			}
			case 3: {
				checkBalance();
				break;
			}
			case 4: {
				accountStatement();
				break;
			}
			case 5: {
				updateAccountPin();
				break;
			}
			case 6: {
				accountClosingRequest();
				break;
			}
			case 7: {
				System.out.println("ThankYou Choosing **** Bank!");
				isStart = false;
				break;
			}

			default:
				System.err.println("Invalid Option!");
			}
		}
	}

	public void checkBalance() {
		System.out.println("Enter Pin Number:");
		int pin = sc.nextInt();
		List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();
		boolean isFound = false;
		for (CustomerDetails c : customerList) {
			if (pin == c.getPin()) {
				System.out.println("Name:" + c.getName());
				System.out.println("Current Balance:" + c.getAmount());
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			System.out.println("Invalid Pin!");
		}
	}

	public void depositBalance() {
		System.out.println("Enter The Amount:");
		double amount = sc.nextDouble();
		System.out.println("Enter The Account Pin:");
		int pin = sc.nextInt();
		List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();
		boolean isFound = false;
		for (CustomerDetails c : customerList) {
			if (pin != 0 && pin == c.getPin()) {
				isFound = true;
				double addAmount = c.getAmount() + amount;
				c.setAmount(addAmount);
				boolean updated = customerDAO.updateAmountByUsingAccountPin(pin, addAmount);
				if (updated) {
					TransactionDetails transactionDetails = new TransactionDetails();
					transactionDetails.setTransactiontype("Credit");
					transactionDetails.setTransactionamount(amount);
					transactionDetails.setBalanceamount(addAmount);
					transactionDetails.setTransactiondate(LocalDate.now());
					transactionDetails.setTransactiontime(LocalTime.now());
					transactionDetails.setCustomeraccountnumber(c.getAccountNumber());
					transactionDetails.setTransactionstatus("Success");
					transactionDetailsDAO.insertTransactionDetails(transactionDetails);
					System.out.println("Amount Deposited Successfully.");
				} else {
					System.out.println("Failed to update amount in database.");
				}
				break;
			}
		}
		if (!isFound) {
			System.out.println("Invalid Pin!");
		}
	}
	public void withdrawBalance() {
		System.out.println("Enter The Amount:");
		double amount = sc.nextDouble();
		System.out.println("Enter The Account Pin:");
		int pin = sc.nextInt();
		List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();
		boolean isFound = false;
		for (CustomerDetails c : customerList) {
			if (pin != 0 && pin == c.getPin()) {
				isFound = true;
				if (c.getAmount() > amount) {
					double addAmount = c.getAmount() - amount;
					c.setAmount(addAmount);
					boolean updated = customerDAO.updateAmountByUsingAccountPin(pin, addAmount);
					if (updated) {
						TransactionDetails transactionDetails = new TransactionDetails();
						transactionDetails.setTransactiontype("Debit");
						transactionDetails.setTransactionamount(amount);
						transactionDetails.setBalanceamount(addAmount);
						transactionDetails.setTransactiondate(LocalDate.now());
						transactionDetails.setTransactiontime(LocalTime.now());
						transactionDetails.setCustomeraccountnumber(c.getAccountNumber());
						transactionDetails.setTransactionstatus("Success");
						transactionDetailsDAO.insertTransactionDetails(transactionDetails);

						System.out.println("Amount Withdrawal Successfully");
					} else {
						System.out.println("Failed to update amount in database");
					}
					break;
				} else {
					System.err.println("Insufficent Balance");
				}
			}
		}
		if (!isFound) {
			System.out.println("Invalid Pin!");
		}
	}
	public void updateAccountPin() {
		System.out.println("Enter Account Number:");
		int accountNumber = sc.nextInt();
		System.out.println("Enter Old Pin:");
		int oldPin = sc.nextInt();
		System.out.println("Enter New Pin:");
		int newPin = sc.nextInt();
		List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();
		boolean isFound = false;
		for (CustomerDetails c : customerList) {
			if (c.getAccountNumber() == accountNumber && c.getPin() == oldPin) {
				c.setPin(newPin);
				boolean update = customerDAO.updateAccountPinByUsingAccountNumber(accountNumber, oldPin, newPin);
				if (update) {
					System.out.println("Pin Updated Successfully");
				} else {
					System.out.println(" Server Error");
				}
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			System.out.println("Account Number or Old Pin is Incorrect");
		}
	}
	public void accountClosingRequest() {
		System.out.println("Enter Account Number:");
		int accountNumber = sc.nextInt();
		System.out.println("Enter Account PIN:");
		int pin = sc.nextInt();
		boolean closed = customerDAO.requestToCloseAccount(accountNumber, pin);
		if (closed) {
			System.out.println(
					"Your account closing request has been sent successfully. We will process it within 24 hours.");
		} else {
			System.out.println("Invalid Account Number or PIN. Please try again.");
		}
	}
	public void accountStatement() {
		System.out.println("Enter Account Number:");
		int accountNumber = sc.nextInt();
		System.out.println("Enter Account Pin:");
		int pin = sc.nextInt();
		List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();
		boolean isFound = false;
		for (CustomerDetails c : customerList) {
			if (pin != 0 && pin == c.getPin() && accountNumber == c.getAccountNumber()) {
				TransactionDetailsDAO transactionDetailsDAO = new TransactionDetailsDAO();
				boolean hasTransactions = transactionDetailsDAO.selectTransactionDetails(accountNumber);
				if (!hasTransactions) {
					System.out.println("No transactions found for this account.");
				}
				isFound = true;
				break; // Exit loop once account is found
			}
		}
		if (!isFound) {
			System.out.println("Invalid Pin or Account Number!");
		}
	}

}