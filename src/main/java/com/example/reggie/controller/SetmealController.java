package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.R;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDto;
import com.example.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    SetmealService setmealService;
    @Autowired
    public void setSetmealService(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    @PostMapping
    public R<String> save(@RequestBody  SetmealDto setmeal) {
        log.info("套餐信息:{}", setmeal);
        setmealService.saveWithDish(setmeal);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> page(Integer page, Integer pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
//        Page<Setmeal> pageInfo = new Page<>();
//        Page<SetmealDto> resPage = new Page<>();
//        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(name != null,Setmeal::getName, name);
//        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
//        setmealService.page(pageInfo,  lambdaQueryWrapper);

         Page<SetmealDto> resPage =
                       setmealService.setmealDtoPage(page, pageSize, name);


        return R.success(resPage);
    }



    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }
}
