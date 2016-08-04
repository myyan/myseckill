package com.freeeland.demo.service;

import com.freeeland.demo.tool.SeckillStateEnum;
import com.freeeland.demo.dto.Exposer;
import com.freeeland.demo.dto.SeckillExecution;
import com.freeeland.demo.exception.RepeatKillException;
import com.freeeland.demo.exception.SeckillCloseException;
import com.freeeland.demo.exception.SeckillException;
import com.freeeland.demo.model.Seckill;
import com.freeeland.demo.model.SuccessKilled;
import com.freeeland.demo.repository.SeckillRepository;
import com.freeeland.demo.repository.SuccessKilledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by maxtropy on 2016/7/28.
 */
@Component
public class SeckillService {

    @Autowired
    private SeckillRepository seckillRepository;

    @Autowired
    private SuccessKilledRepository successKilledRepository;

    public List<Seckill> getSeckillList(){
        return seckillRepository.findAll();
    }

    public Seckill getSeckillById(Long id){
        return seckillRepository.findOne(id);
    }

    public Seckill saveSeckill(Seckill seckill){
        return  seckillRepository.save(seckill);
    }

    private final String salt = "12321kdjflkds";


    public Exposer exposerSeckillUrl(Long seckillId){
        Seckill seckill = seckillRepository.findOne(seckillId);
        if(seckill == null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime=  seckill.getEndTime();
        Date now  = new Date();
        if(now.getTime()<startTime.getTime()||now.getTime()>endTime.getTime()){
            return  new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return  new Exposer(true,md5,seckillId);
    }

    public SeckillExecution executionSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException{
        if(md5==null||!md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }

        Date now = new Date();


        try {
            List<SuccessKilled> successKilleds = successKilledRepository.findBySeckillIdAndUserPhone(seckillId, userPhone);
            if (successKilleds.size() != 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存 热点商品竞争点
                int reduceState = reduceNumber(seckillId);
                if (reduceState == 1) {
                    SuccessKilled successKilled = new SuccessKilled();
                    successKilled.setCreateTime(now);
                    successKilled.setSeckillId(seckillId);
                    successKilled.setUserPhone(userPhone);
                    successKilled.setState(1);
                    successKilledRepository.save(successKilled);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                } else {
                    throw new SeckillCloseException("seckill has closed");
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e3){
            throw new SeckillException("seckill inner error:"+e3.getMessage());
        }

    }

    private String getMd5(Long seckillId){
        String base = seckillId+"/"+salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    private int reduceNumber(Long id){
        Seckill seckill = seckillRepository.findOne(id);
        int number = seckill.getNumber();
        if(number>=1){
            seckill.setNumber(number-1);
            seckillRepository.save(seckill);
            return  1;
        }else{
            return -1;
        }

    }
}
