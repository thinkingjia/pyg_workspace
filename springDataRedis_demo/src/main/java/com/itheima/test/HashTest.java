package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class HashTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testAdd(){

        redisTemplate.boundHashOps("hash_key").put("a","AAAAA");
        redisTemplate.boundHashOps("hash_key").put("b","BBBBB");
        redisTemplate.boundHashOps("hash_key").put("c","CCCCC");
        redisTemplate.boundHashOps("hash_key").put("d","DDDDD");
//        zhaoliu  wangwu lisi zhangsan
    }

    @Test
    public void testGet(){
        Set list_key = redisTemplate.boundHashOps("hash_key").keys();
        for (Object o : list_key) {
            System.out.println(o);
            Object hash_key = redisTemplate.boundHashOps("hash_key").get(o);
            System.out.println(hash_key);
        }
    }

    @Test
    public void testDele(){
//        redisTemplate.delete("hash_key");
        redisTemplate.boundHashOps("hash_key").delete("c");
    }

}
