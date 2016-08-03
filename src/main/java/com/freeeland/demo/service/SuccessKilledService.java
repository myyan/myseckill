package com.freeeland.demo.service;

import com.freeeland.demo.model.SuccessKilled;
import com.freeeland.demo.repository.SuccessKilledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by maxtropy on 2016/7/28.
 */
@Component
public class SuccessKilledService {

    @Autowired
    private SuccessKilledRepository successKilledRepository;

    public List<SuccessKilled> getList(){
        return successKilledRepository.findAll();
    }

    public SuccessKilled getSuccessKilledById(Long id){
        return successKilledRepository.findOne(id);
    }

    public SuccessKilled saveSuccessKilled(SuccessKilled successKilled){
        return successKilledRepository.save(successKilled);
    }
}
