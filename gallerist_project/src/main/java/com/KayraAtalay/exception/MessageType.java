package com.KayraAtalay.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	NO_RECORD_EXIST("1004", "No records found"),
	GENERAL_EXCEPTION("9999", "There is an error"),
	INVALID_STATUS_CHANGE("1004", "Status change is not valid"),
	TOKEN_EXPIRED("1005", "Token is expired"),
	WRONG_TOKEN("1006", "This token is not exist"),
	USERNAME_NOT_FOUND("1007", "Could not find the username"),
	USERNAME_NOT_CREATED("1008", "This username is already exists"),
	USERNAME_OR_PASSWORD_INVALID("1009", "Wrong username or password"),
	REFRESH_TOKEN_NOT_FOUND("1010", "Could not find the refresh token"),
	REFRESH_TOKEN_EXPIRED("1011", "This refresh token is expired"),
	ADDRESS_NOT_FOUND("1012", "Could not find the address"),
	ACCOUNT_NOT_FOUND("1013", "Could not find the account"),
	GALLERIST_NOT_FOUND("1014", "Could not find the gallerist"),
	CAR_NOT_FOUND("1015", "Could not find the car"),
	CUSTOMER_NOT_FOUND("1016", "Could not find the customer"),
	TCKN_ALREADY_EXISTS("1017", "This tckn is already created"),
	CURRENCY_RATES_IS_OCCURED("1018", "Could not get exchange rate"),
	CUSTOMER_AMOUNT_IS_NOT_ENOUGH("1019", "The customer's money is not enough to buy this car"),
	CAR_IS_ALREADY_SOLD("1020", "This car is already sold"),
	CAR_IS_NOT_BELONG_TO_GALLERIST("1021", "This car is not belong this gallerist"),
	ACCOUNT_NO_ALREADY_EXISTS("1022", "This Account Number is already belongs to another account : ");
	
	
	
	 private String code;
	
	private String message;
	
	 MessageType(String code, String message) {
		this.code = code;
		this.message = message;
		
	}
	
}
