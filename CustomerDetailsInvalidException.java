package com.bank.Exception;

public class CustomerDetailsInvalidException extends RuntimeException {

	private String exception;

	public CustomerDetailsInvalidException() {
		// TODO Auto-generated constructor stub
	}

	public CustomerDetailsInvalidException(String exception) {
		this.exception = exception;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "CustomerDetailsInvalidException [exception=" + exception + "]";
	}

}
