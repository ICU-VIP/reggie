package com.example.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.common.R;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDish;
import com.example.reggie.entity.SetmealDto;
import com.example.reggie.mapper.SetmealDishMapper;
import com.example.reggie.mapper.SetmealMapper;
import com.example.reggie.service.SetmealDishService;
import com.example.reggie.service.SetmealService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    SetmealMapper setmealMapper;



    @Autowired
    public void setSetmealMapper(SetmealMapper setmealMapper) {
        this.setmealMapper = setmealMapper;
    }

    SetmealDishService setmealDishService;
    @Autowired
    public void setSetmealDishService(SetmealDishService setmealDishService) {
        this.setmealDishService = setmealDishService;
    }

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish : list){
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(list);
    }

    @Override
    public Page<SetmealDto> setmealDtoPage( Integer page,
                                           Integer pageSize,
                                           String name)  {
        LambdaQueryWrapper<SetmealDto> qw = new LambdaQueryWrapper<>();
        qw.like(name != null, SetmealDto::getName, name);
        qw.orderByDesc(SetmealDto::getUpdateTime);
        Page<SetmealDto> pageInfo= new Page<>();
        setmealMapper
                .setmealDtoPage(pageInfo, qw);
        //setmealMapper.setmealDtoPage2(pageInfo, page, pageSize, name);

        return pageInfo;
    }


    @Override
    public void removeWithDish(List<Long> ids) {

    }

}
