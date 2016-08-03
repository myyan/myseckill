package com.freeeland.demo.repository;

import com.freeeland.demo.model.SuccessKilled;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by maxtropy on 2016/7/28.
 */
public interface SuccessKilledRepository extends JpaRepository<SuccessKilled,Long> {
    List<SuccessKilled> findBySeckillIdAndUserPhone(Long seckillId, Long userPhone);
}
