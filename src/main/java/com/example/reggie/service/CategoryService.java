package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(long id);//删除菜品分类
}
