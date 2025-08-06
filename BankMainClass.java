package com.bank.Main;

import java.util.Scanner;

import com.bank.Service.AdminService;
import com.bank.Service.CustomerService;

public class BankMainClass {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		CustomerService customerService = new CustomerService();
		AdminService adminService = new AdminService();
		System.out.println("****** Choose Options******\n1.Customer Registration\n2.Customer Login\n3.Admin Login");
		switch (scan.nextInt()) {
		case 1: {
			System.out.println("------------Welcome to Customer Registaration-----------");
			customerService.customerRegistration();
		}
			break;
		case 2: {
			System.out.println("------------Welcome To Customer Login------------");
			customerService.customerLogin();
		}
			break;
		case 3: {
			System.out.println("------------Welcome To Admin Login---------------");
			adminService.adminLogin();
		}
			break;
		default:
			System.out.println("Invalid Option!");
		}
	}
}