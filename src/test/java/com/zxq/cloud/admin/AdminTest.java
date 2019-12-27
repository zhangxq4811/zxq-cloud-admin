package com.zxq.cloud.admin;

import cn.hutool.core.date.DateUtil;
import com.zxq.cloud.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @author zxq
 * @date 2019/12/19 14:38
 **/
@Slf4j
@SpringBootTest
public class AdminTest {

    @Autowired
    private BaseController baseController;

    @Test
    public void testHutoolApi(){
        /*String idCard = "362322199512214811";
        String provinceByIdCard = IdcardUtil.getProvinceByIdCard(idCard);
        log.info("provinceByIdCard:{}",provinceByIdCard);
        int genderByIdCard = IdcardUtil.getGenderByIdCard(idCard);
        log.info("genderByIdCard:{}",genderByIdCard);*/

        String endPutTime = "2019-12-19 18:59:59";

        int i = DateUtil.beginOfDay(new Date()).compareTo(DateUtil.parseDateTime(endPutTime));

        System.out.println(i);

    }

    @Test
    public void testAsync(){
        for (int i = 0; i < 20; i++) {
            print(i);
        }
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void print(Integer i)  {
        System.out.println("======="+i);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
