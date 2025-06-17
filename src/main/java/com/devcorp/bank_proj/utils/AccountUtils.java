package com.devcorp.bank_proj.utils;

import java.time.Year;

public class AccountUtils {
    public final static String USER_EXISTS_CODE ="001";
    public final static String USER_EXISTS_MESSAGE="User already exists";
    public final static String ACCOUNT_CREATION_SUCCESS = "002";
    public final static String ACCOUNT_CREATION_MESSAGE = "User created successfully";

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
