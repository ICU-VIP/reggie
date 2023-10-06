package com.example.reggie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serial;
import java.util.*;


@SpringBootTest
class ReggieApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads(){
        //String[] str = new String[] { "yang", "hao" };
        stringRedisTemplate.opsForValue().set("show:1001",  "yang");
        System.out.println(stringRedisTemplate.opsForValue().get("show:1001"));
        stringRedisTemplate.opsForValue().set("show:1002",  "hao");
        System.out.println(stringRedisTemplate.opsForValue().get("show:1002"));
      
        String patternKey = "show:"+"*";
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).count(10000).build();
        Cursor<String> scan = stringRedisTemplate.scan(options);
        List<String> keysList = new ArrayList<>();
        while(scan.hasNext()){
             String key = scan.next();
             keysList.add(key);
        }
        System.out.println(keysList);
        List<String> valueList = stringRedisTemplate.opsForValue().multiGet(keysList);
        
        System.out.println(valueList);
        //stringRedisTemplate.delete(keysList);
        System.out.println(stringRedisTemplate.opsForValue().get("show:1001"));
        System.out.println(stringRedisTemplate.opsForValue().get("show:1002"));
        scan.close();
    }
}
