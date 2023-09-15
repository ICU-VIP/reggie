package com.example.reggie.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    Page<SetmealDto> setmealDtoPage(Page<SetmealDto> pageInfo,
                                    @Param("ew") LambdaQueryWrapper<SetmealDto> ew);
    Page<SetmealDto> setmealDtoPage2(Page<SetmealDto> pageInfo,
                                    @Param("page") Integer page,
                                     @Param("pageSize") Integer pageSize,
                                     @Param("name") String name);
}
