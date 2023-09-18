package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    Page<SetmealDto> setmealDtoPage(Integer page,  Integer pageSize, String name);

    void removeWithDish(List<Long> ids);

    SetmealDto getSetmealDtoById(Long id);

    void updateSetmealDto(SetmealDto setmealDto);

    void updateSetmealStatus(Integer code, List<Long> ids);
}
