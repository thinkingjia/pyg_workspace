package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class SetTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testAdd(){

        redisTemplate.boundSetOps("set_key").add("zhangsan");
        redisTemplate.boundSetOps("set_key").add("lisi");
        redisTemplate.boundSetOps("set_key").add("wangwu");
        redisTemplate.boundSetOps("set_key").add("zhaoliu");
//        zhaoliu  wangwu lisi zhangsan
    }

    @Test
    public void testGet(){
        Set list_key = redisTemplate.boundSetOps("set_key").members();
        for (Object o : list_key) {
            System.out.println(o);
        }
    }

    @Test
    public void testDele(){
//        redisTemplate.delete("set_key");
        redisTemplate.boundSetOps("set_key").remove("lisi");
    }

}
