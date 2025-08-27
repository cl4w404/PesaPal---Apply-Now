package com.devcorp.bank_proj.utils;

import java.time.Year;

public class AccountUtils {
    public final static String USER_EXISTS_CODE ="001";
    public final static String USER_EXISTS_MESSAGE="User already exists";
    public final static String ACCOUNT_CREATION_SUCCESS = "002";
    public final static String ACCOUNT_CREATION_MESSAGE = "User created successfully";
    public final static String ACCOUNT_DOES_NOT_EXIST = "003";
    public final static String ACCOUNT_DOES_NOT_EXIST_MESSAGE= "Account does not exist";
    public final static String ACCOUNT_FOUND_CODE = "004";
    public final static String ACCOUNT_FOUND_MESSAGE = "Account found";
    public final static String EMAIL_DOES_NOT_EXIST_CODE = "005";
    public final static String EMAIL_DOES_NOT_EXIST_MESSAGE = "Account with email not found";
    public final static String EMAIL_EXIST_CODE= "006";
    public final static String EMAIL_EXIST_MESSAGE = "Account with email found";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account debited successfully";
    public static final String INSUFFICIENT_FUNDS_CODE = "008";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "You do not have enough funds";
    public static final String CREDITED_SUCCESFULLY_CODE = "009";
    public static final String CREDITED_SUCCESFULLY_MESSAGE = "Account credited successfully";

    public static String getAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int random = (int) Math.floor(Math.random() * (max - min +1) - min);
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(random);
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(year).append(randomNumber);

        return accountNumber.toString();
    }
}
