package com.freeeland.demo.controller;

import com.freeeland.demo.model.Seckill;
import com.freeeland.demo.model.SuccessKilled;
import com.freeeland.demo.service.SeckillService;
import com.freeeland.demo.service.SuccessKilledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by maxtropy on 2016/7/28.
 */
@Controller
public class SuccessKilledController {

    Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SuccessKilledService successKilledService;

    @Autowired
    private SeckillService seckillService;

    private int i = 0;
    @RequestMapping("/save2/{id}")
    public void save(@PathVariable("id") Long id){
        SuccessKilled successKilled = new SuccessKilled();
        successKilled.setCreateTime(new Date());
        successKilled.setState(1);
        successKilled.setUserPhone(Long.valueOf("1310000000"+i++));
        successKilled.setSeckillId(id);
        Seckill seckill = seckillService.getSeckillById(id);
        successKilled.setSeckill(seckill);
        successKilledService.saveSuccessKilled(successKilled);
        logger.info("current thread name {}",Thread.currentThread().getName());
        logger.info("current object {}",this.hashCode());
    }
}
