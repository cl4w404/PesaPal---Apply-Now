package com.devcorp.bank_proj.service.implementations;

import com.devcorp.bank_proj.dto.*;
import com.devcorp.bank_proj.models.User;
import com.devcorp.bank_proj.repository.UserRepository;
import com.devcorp.bank_proj.response.AccountInfo;
import com.devcorp.bank_proj.response.Response;
import com.devcorp.bank_proj.service.services.EmailService;
import com.devcorp.bank_proj.service.services.UserService;
import com.devcorp.bank_proj.utils.AccountUtils;
import com.devcorp.bank_proj.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public Response createUser(UserDto user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return Response.builder()
                    .messageCode(AccountUtils.USER_EXISTS_CODE)
                    .message(AccountUtils.USER_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .otherName(user.getOtherName())
                .gender(user.getGender())
                .address(user.getAddress())
                .stateOfOrigin(user.getStateOfOrigin())
                .dateOfBirth(user.getDateOfBirth())
                .accountNumber(AccountUtils.getAccountNumber())
                .phoneNumber(user.getPhoneNumber())
                .alternativePhone(user.getAlternativePhone())
                .email(user.getEmail())
                .balance(BigDecimal.ZERO)
                .status(Status.ACTIVE.toString())
                .build();
        User saveUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveUser.getEmail())
                .subject("User Registered Successfully")
                .message("Dear "+saveUser.getFirstName()+" Welcome to Githunguri east welfare and sacco \n Your account number is"+ saveUser.getAccountNumber())
                .build();
        emailService.sendEmail(emailDetails);
        return Response.builder()
                .messageCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .message(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .data(AccountInfo.builder()
                        .accountName(saveUser.getFirstName()  + " " + saveUser.getLastName()+" "+saveUser.getOtherName())
                        .accountNumber(saveUser.getAccountNumber())
                        .accountBalance(saveUser.getBalance()
                        )
                        .build())
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Response balanceEnquiry(EnquiryResponse response) {
        //boolean isAccountExist = userRepository.existsByAccountNumber(response.getAccountNumber());
        User foundUser= userRepository.findByAccountNumber(response.getAccountNumber());
        if(foundUser == null) {
            return Response.builder()
                    .messageCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST)
                    .message(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }

        return Response.builder()
                .messageCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .message(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(foundUser.getBalance())
                        .accountName(foundUser.getFirstName())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public Response emailEnquiry(EmailEnquiry enquiry) {
        User foundUser = userRepository.findByEmail(enquiry.getEmail());
        if(foundUser == null) {
            return Response.builder()
                    .messageCode(AccountUtils.EMAIL_DOES_NOT_EXIST_CODE)
                    .message(AccountUtils.EMAIL_DOES_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }
        return Response.builder()
                .messageCode(AccountUtils.EMAIL_EXIST_CODE)
                .message(AccountUtils.EMAIL_EXIST_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(foundUser.getBalance())
                        .accountName(foundUser.getFirstName())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public Response debitAccount(CreditDebitRequest request) {
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        if(foundUser == null) {
            return Response.builder()
                    .messageCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST)
                    .message(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }
        foundUser.setBalance(foundUser.getBalance().add(request.getAmount()));
        userRepository.save(foundUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(foundUser.getEmail())
                .subject("Successfully debited")
                .message("Your Account " + foundUser.getAccountNumber() + "has been debited Successfully with" + request.getAmount() + "Ksh")
                .build();
        emailService.sendEmail(emailDetails);
        return Response.builder()
                .messageCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                .message(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .data(AccountInfo.builder()
                        .accountBalance(foundUser.getBalance())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public Response creditAccount(CreditDebitRequest request) {
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        if(foundUser == null) {
            return Response.builder()
                    .messageCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST)
                    .message(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }
        if(foundUser.getBalance().compareTo(request.getAmount()) < 0) {
            return Response.builder()
                    .messageCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .message(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .data(null)
                    .build();
        }
        foundUser.setBalance(foundUser.getBalance().subtract(request.getAmount()));
        userRepository.save(foundUser);
        return Response.builder()
                .messageCode(AccountUtils.CREDITED_SUCCESFULLY_CODE)
                .message(AccountUtils.CREDITED_SUCCESFULLY_MESSAGE)
                .data(AccountInfo.builder()
                        .accountNumber(foundUser.getAccountNumber())
                        .accountBalance(foundUser.getBalance())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                        .build())
                .build();
    }


}
