package com.devcorp.bank_proj.service.services;

import com.devcorp.bank_proj.dto.*;
import com.devcorp.bank_proj.models.User;
import com.devcorp.bank_proj.response.Response;

import java.util.List;

public interface UserService {
    Response createUser(UserDto user);
   List<User> getAllUsers();
   Response balanceEnquiry(EnquiryResponse response);
   Response emailEnquiry(EmailEnquiry enquiry);
   Response debitAccount(CreditDebitRequest request);
   Response creditAccount(CreditDebitRequest request);
   Response userLogin(UserLogin userLogin);

}
