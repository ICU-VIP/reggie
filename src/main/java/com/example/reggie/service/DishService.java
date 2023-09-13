package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.DishDto;
import org.springframework.transaction.annotation.Transactional;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    @Transactional
    void updateWithFlavor(DishDto dishDto);
}
