package com.example.api.resources;

import com.example.api.dto.MessageDto;
import com.example.api.service.SMSService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms")
public class SMSController {
    private SMSService smsService;

    public SMSController (SMSService smsService) {
        this.smsService = smsService;
    }

    @PostMapping
    public void sendMessages (@RequestBody List<MessageDto> messageDtos) {
        smsService.sendMessages(messageDtos);
    }
}
