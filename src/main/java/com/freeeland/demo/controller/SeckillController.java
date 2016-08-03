package com.freeeland.demo.controller;

import com.freeeland.demo.Enum.SeckillStateEnum;
import com.freeeland.demo.dto.Exposer;
import com.freeeland.demo.dto.SeckillExecution;
import com.freeeland.demo.dto.SeckillResult;
import com.freeeland.demo.exception.RepeatKillException;
import com.freeeland.demo.exception.SeckillCloseException;
import com.freeeland.demo.model.Seckill;
import com.freeeland.demo.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by maxtropy on 2016/7/28.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/list")
    public String seckill(Model model){
        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("seckills",seckills);
        return "list";
     }


     @RequestMapping("/{seckillId}/detail")
     public String detail(Model model,
                          @PathVariable("seckillId") Long seckillId){
         if(seckillId==null){
             return "redirect:/seckill/list";
         }
         Seckill seckill = seckillService.getSeckillById(seckillId);
         if(seckill==null){
             return "forward:/seckill/list";
         }
         model.addAttribute("seckill",seckill);
         return "detail";
     }

    //ajax
    @RequestMapping("/{seckillId}/exposer")
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        Exposer exposer = seckillService.exposerSeckillUrl(seckillId);
        result = new SeckillResult<>(true,exposer);
        return  result;
    }


    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone==null){
            return new SeckillResult<>(false, "未注册");
        }
        try {
            SeckillExecution execution = seckillService.executionSeckill(seckillId, phone, md5);
            return new SeckillResult<>(true, execution);
        }catch (RepeatKillException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<>(true,seckillExecution);
        }catch (SeckillCloseException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.END);
            return new SeckillResult<>(true,seckillExecution);
        }catch (Exception e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<>(true,seckillExecution);
        }

    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<>(true,now.getTime());
    }




}
