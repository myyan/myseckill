package com.freeeland.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by maxtropy on 2016/7/28.
 */

@Entity
public class SuccessKilled {

    @Id
    @GeneratedValue
    private Long id;

    private Long seckillId;

    private Long userPhone;

    private Integer state;

    private Date createTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private Seckill seckill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }
}
