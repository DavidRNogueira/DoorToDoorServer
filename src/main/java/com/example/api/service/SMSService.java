package com.example.api.service;

import com.example.api.dto.MessageDto;

import java.util.List;

public interface SMSService {
    public void sendMessages (List<MessageDto> messageDto);
}
