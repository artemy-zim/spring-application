package com.example.springapplication.service;

import com.example.springapplication.entity.UserAction;
import com.example.springapplication.repository.UARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UAServiceImpl implements UAService{

    private final UARepository uaRepository;

    @Autowired
    public UAServiceImpl(UARepository uaRepository){
        this.uaRepository = uaRepository;
    }

    @Override
    public void saveUserAction(String message) {
        UserAction log = new UserAction();
        log.setDescription(message);
        log.setDate_actions(new Date());
        uaRepository.save(log);
    }
}
