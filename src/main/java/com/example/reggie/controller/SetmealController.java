package com.example.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.R;

import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDto;
import com.example.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id) {
        log.info("id:{}", id);
        SetmealDto setmealDto = setmealService.getSetmealDtoById(id);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("setmealDto:{}", setmealDto);
        setmealService.updateSetmealDto(setmealDto);
        return R.success("套餐数据更新成功");
    }

    @PostMapping("/status/{code}")
    public R<String> updateStatus(@PathVariable Integer code, @RequestParam List<Long> ids) {
        log.info("code:{}", code);
        setmealService.updateSetmealStatus(code, ids);
        return R.success("套餐状态更新成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
