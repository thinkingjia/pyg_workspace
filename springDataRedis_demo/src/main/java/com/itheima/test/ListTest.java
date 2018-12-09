package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class ListTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testAdd(){
//        redisTemplate.boundListOps("list_key").rightPush("zhangsan");
//        redisTemplate.boundListOps("list_key").rightPush("lisi");
//        redisTemplate.boundListOps("list_key").rightPush("wangwu");
//        redisTemplate.boundListOps("list_key").rightPush("zhaoliu");
//        zhangsan  lisi  wangwu  zhaoliu

        redisTemplate.boundListOps("list_key").leftPush("zhangsan");
        redisTemplate.boundListOps("list_key").leftPush("lisi");
        redisTemplate.boundListOps("list_key").leftPush("wangwu");
        redisTemplate.boundListOps("list_key").leftPush("zhaoliu");
//        zhaoliu  wangwu lisi zhangsan
    }

    @Test
    public void testGet(){
        List list_key = redisTemplate.boundListOps("list_key").range(0, 10);
        for (Object o : list_key) {
            System.out.println(o);
        }
    }

    @Test
    public void testDele(){
//        redisTemplate.delete("list_key");
        redisTemplate.boundListOps("list_key").remove(1,"lisi");
    }

}
