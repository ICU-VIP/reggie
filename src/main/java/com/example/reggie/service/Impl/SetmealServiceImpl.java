package com.example.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDish;
import com.example.reggie.entity.SetmealDto;
import com.example.reggie.exception.CustomException;
import com.example.reggie.mapper.SetmealMapper;
import com.example.reggie.service.SetmealDishService;
import com.example.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
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
    public void saveWithDish( SetmealDto setmealDto) {
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
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);
        lambdaQueryWrapper.eq(Setmeal::getStatus, 1);

        if(setmealMapper.selectCount(lambdaQueryWrapper) > 0){
            throw new CustomException("存在正在售卖的套餐，无法删除");
        }
        //this.removeByIds(ids);
        setmealMapper.deleteBatchIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper1);
    }


    @Override
    public SetmealDto getSetmealDtoById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        setmealDto.setSetmealDishes(setmealDishService.list(lambdaQueryWrapper));
        return setmealDto;
    }

    @Override
    @Transactional
    public void updateSetmealDto(SetmealDto setmealDto) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        setmealMapper.updateById(setmeal);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);

        for(SetmealDish setmealDish : setmealDto.getSetmealDishes()){
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDto.getSetmealDishes());


    }

    @Override
    @Transactional
    public void updateSetmealStatus(Integer code, List<Long> ids) {
        List<Setmeal> setmealList = setmealMapper.selectBatchIds(ids);

        for(Setmeal setmeal : setmealList){
            setmeal.setStatus(code);
            setmealMapper.updateById(setmeal);
        }

    }
}
