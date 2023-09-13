package com.example.reggie.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.exception.CustomException;
import com.example.reggie.mapper.CategoryMapper;
import com.example.reggie.service.CategoryService;
import com.example.reggie.service.DishService;
import com.example.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    DishService dishService;
    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    SetmealService setmealService;

    @Autowired
    public void setSetmealService(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    @Override
    public void remove(long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long countDish
                =  dishService.count(dishLambdaQueryWrapper);
        if (countDish > 0) {
            throw new CustomException("当前类目下存在菜品，不允许删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long countSetmeal =
                setmealService.count(setmealLambdaQueryWrapper);
        if(countSetmeal > 0){
            throw new CustomException("当前类目包含在套餐，不允许删除");
        }
        super.removeById(id);
    }
}


