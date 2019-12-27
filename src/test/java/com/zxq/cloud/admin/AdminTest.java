package com.zxq.cloud.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author zxq
 * @date 2019/12/19 14:38
 **/
@Slf4j
public class AdminTest {

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
}
