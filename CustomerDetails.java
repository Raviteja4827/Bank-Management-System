package com.bank.DataTransferObject;

public class CustomerDetails {
	private String name;
	private String emailId;
	private long mobileNumber;
	private long aadharNumber;
	private String address;
	private String gender;
	private long accountNumber;
	private double amount;
	private int pin;

	public CustomerDetails() {
	}

	public CustomerDetails(String name, String emailId, long mobileNumber, long aadharNumber, String address,
			String gender, long accountNumber, double amount, int pin) {
		this.name = name;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.aadharNumber = aadharNumber;
		this.address = address;
		this.gender = gender;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(long aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "****Customer Details****\nCustomer Name:" + name + "\nEmail Id:" + emailId + "\nMobile Number:"
				+ mobileNumber + "\nAadhar Number:" + aadharNumber + "\nAddress:" + address + "\nGender=" + gender
				+ "\nAccountNumber:" + accountNumber + "\nAmount:" + amount + "\nPin:" + pin;
	}

} 
