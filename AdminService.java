package com.bank.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.CustomerDAO;
import com.bank.DataTransferObject.CustomerDetails;

public class AdminService {
	AdminDAO adminDAO = new AdminDAO();
	CustomerDAO customerDAO = new CustomerDAO();
	Scanner scan = new Scanner(System.in);

	public void adminLogin() {
		System.out.println("Enter Admin EmailID:");
		String emailId = scan.next();
		System.out.println("Enter Admin Password:");
		String password = scan.next();
		if (adminDAO.selectAdminDetailsByUsingEmailAndPassword(emailId, password)) {
			System.out.println("---Login Success---");
			System.out.println(
						"Admin Services\n1.All Customer Details\n2.All Account request Details\n3.Account Closing Request");
				switch (scan.nextInt()) {
				case 1: {
					System.out.println("***All Customer Details***");
					List<CustomerDetails> customerList = customerDAO.getAllCustomerDetails();

					if (customerList != null && !customerList.isEmpty()) {
						for (CustomerDetails c : customerList) {
							System.out.println("Account Holder:" + c.getName());
							System.out.println("Account Number: " + c.getAccountNumber());
							System.out.println("Avaliable Balance: " + c.getAmount());
							System.out.println("Email Id: " + c.getEmailId());
							System.out.println("Aadhar Number: " + c.getAadharNumber());
							System.out.println("Mobile Number: " + c.getMobileNumber());
							System.out.println("Account Pin: " + c.getPin());
							System.out.println("------------------------------");
						}
					} else {
						System.out.println("No customer records found.");
					}
				}
					break;
				case 2: {
					System.out.println("All New Account Opening Request Details");
					List<CustomerDetails> accountRequestDetails = customerDAO.getAccountRequestDetails();

					if (accountRequestDetails != null && !accountRequestDetails.isEmpty()) {
						for (CustomerDetails c : accountRequestDetails) {
							System.out.println("Account Holder:" + c.getName());
							System.out.println("Email: " + c.getEmailId());
							long mobileNumber = c.getMobileNumber();
							String mb = "" + mobileNumber;
							System.out.println(
									"Customer Mobile Number:" + mb.substring(0, 3) + "" + mb.substring(7, 10));
							long aadharNumber = c.getAadharNumber();
							String an = "" + aadharNumber;
							System.out.println(
									"Customer Aadhar Number:" + an.substring(0, 3) + "" + an.substring(9, 12));

							System.out.println("------------------------------");
						}
						System.out.println(
								"Choose Option\n1.Generate Account Number For One Person\n2.Generate Account Number For All Persons");
						switch (scan.nextInt()) {
						case 1:
							break;
						case 2:
							List<CustomerDetails> acceptedDetails = new ArrayList<CustomerDetails>();
							for (int i = 0; i < accountRequestDetails.size(); i++) {
								CustomerDetails customerDetails = accountRequestDetails.get(i);
								Random random = new Random();
								int ac = random.nextInt(10000000);
								if (ac < 1000000) {
									ac += 1000000;
								}
								customerDetails.setAccountNumber(ac);
								int pin = random.nextInt(10000);
								if (pin < 1000) {
									pin += 1000;
								}
								customerDetails.setPin(pin);
								acceptedDetails.add(customerDetails);
							}
							customerDAO.updateAccountPinByUsingId(acceptedDetails); 
							System.out.println(acceptedDetails);
							break;
						default:
							break;
						}
						break;
					} else {
						System.out.println("No customer records found.");
					}
				}
					break;
				case 3: {
					System.out.println("All Account Closing Request Details");
					List<CustomerDetails> accountRequestDetails = customerDAO.getAccountClosingRequestDetails();

					if (accountRequestDetails != null && !accountRequestDetails.isEmpty()) {
						for (CustomerDetails c : accountRequestDetails) {
							System.out.println("Account Holder Name:"+c.getName());
							System.out.println("Account Number:"+c.getAccountNumber());
							System.out.println("Mobile Number:"+c.getMobileNumber());
							System.out.println("Aadhar Number:"+c.getAadharNumber());
							System.out.println("-----------------------------------");
						}
						System.out.println("Choose Options\n1.Request Accepted");
						int choice=scan.nextInt();
						switch (choice) {
						case 1: {
					        customerDAO.closeAllAccountsWithClosingStatus();
						}
						break;
						default:
							System.out.println("Invalid Option!");
						}
						}
					else {
						System.out.println("No customer records found.");
					}

				}
					break;
				default:
					System.out.println("Invalid Option");
				}
			}
		 else {
			System.err.println("Invalid EmailID or Password");
		}
	}

}