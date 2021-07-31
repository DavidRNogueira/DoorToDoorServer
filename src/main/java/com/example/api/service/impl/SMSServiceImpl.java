package com.example.api.service.impl;

import com.example.api.dao.OrgDao;
import com.example.api.dto.MessageDto;
import com.example.api.entity.TaskEntity;
import com.example.api.service.SMSService;
import com.example.api.service.TaskService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SMSServiceImpl implements SMSService {
    public static final String ACCOUNT_SID =
            "ACe67e6a5c049f822eaa46a146b6cd1af8";
    public static final String AUTH_TOKEN =
            "7878bc749dd2ee61382888990d3768a9";

    private OrgDao orgDao;
    private TaskService taskService;

    public SMSServiceImpl (OrgDao orgDao, TaskService taskService) {
        this.orgDao = orgDao;
        this.taskService = taskService;
    }

    //Needs testing
    public void sendMessages (List<MessageDto> messageDtos) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        List<TaskEntity> taskEntities = new ArrayList<>();

        for( MessageDto messageDto: messageDtos) {
            try {
//                Only to be activated when ready for launch

//                Message message = Message
//                        .creator(new PhoneNumber(messageDto.getPhoneNumber()), // to
//                                new PhoneNumber("+12056977562"), // from
//                                "Thank you for using DoorToDoor! Here's your map:\n http://localhost:8080/task/" + messageDto.getTaskId() + "/view")
//                        .create();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TaskEntity taskEntity = orgDao.getTaskById(messageDto.getTaskId());
            taskEntity.setTimeSent(new Date().getTime());
            taskEntity.setStatus("incomplete");
            taskEntities.add(taskEntity);
        }

        orgDao.updateListOfTasks(taskEntities);
    }
}
