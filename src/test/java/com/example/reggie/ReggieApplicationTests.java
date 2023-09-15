package com.example.reggie;

import com.example.reggie.service.SetmealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReggieApplicationTests {

    SetmealService setmealService;

    @Autowired
    public void setSetmealService(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    @Test
    void contextLoads() {
      setmealService.setmealDtoPage(1,10, null);
    }

}
