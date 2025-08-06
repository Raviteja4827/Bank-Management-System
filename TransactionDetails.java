package com.bank.DataTransferObject;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionDetails 
{
	private int transactionid;
	private String transactiontype;
	private double transactionamount;
	private LocalDate transactiondate;
	private LocalTime transactiontime;
	private double balanceamount;
	private String transactionstatus;
	private long customeraccountnumber;
	
	public  TransactionDetails() {
		
	}

	public TransactionDetails(int transactionid, String transactiontype, double transactionamount,
			LocalDate transactiondate, LocalTime transactiontime, double balanceamount, String transactionstatus,
			long customeraccountnumber) {
		super();
		this.transactionid = transactionid;
		this.transactiontype = transactiontype;
		this.transactionamount = transactionamount;
		this.transactiondate = transactiondate;
		this.transactiontime = transactiontime;
		this.balanceamount = balanceamount;
		this.transactionstatus = transactionstatus;
		this.customeraccountnumber = customeraccountnumber;
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public double getTransactionamount() {
		return transactionamount;
	}

	public void setTransactionamount(double transactionamount) {
		this.transactionamount = transactionamount;
	}

	public LocalDate getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}

	public LocalTime getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(LocalTime transactiontime) {
		this.transactiontime = transactiontime;
	}

	public double getBalanceamount() {
		return balanceamount;
	}

	public void setBalanceamount(double balanceamount) {
		this.balanceamount = balanceamount;
	}

	public String getTransactionstatus() {
		return transactionstatus;
	}

	public void setTransactionstatus(String transactionstatus) {
		this.transactionstatus = transactionstatus;
	}

	public long getCustomeraccountnumber() {
		return customeraccountnumber;
	}

	public void setCustomeraccountnumber(long customeraccountnumber) {
		this.customeraccountnumber = customeraccountnumber;
	}

	@Override
	public String toString() {
		return "TransactionDetails [transactionid=" + transactionid + ", transactiontype=" + transactiontype
				+ ", transactionamount=" + transactionamount + ", transactiondate=" + transactiondate
				+ ", transactiontime=" + transactiontime + ", balanceamount=" + balanceamount + ", transactionstatus="
				+ transactionstatus + ", customeraccountnumber=" + customeraccountnumber + "]";
	}
	

}
