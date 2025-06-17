package com.devcorp.bank_proj.service.services;

import com.devcorp.bank_proj.dto.EmailDetails;

public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
