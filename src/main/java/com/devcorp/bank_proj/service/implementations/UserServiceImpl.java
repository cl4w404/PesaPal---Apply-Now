package com.devcorp.bank_proj.service.implementations;

import com.devcorp.bank_proj.dto.EmailDetails;
import com.devcorp.bank_proj.dto.UserDto;
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
}
