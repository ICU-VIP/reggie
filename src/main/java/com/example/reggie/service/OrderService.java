package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

    void pageOrder(Page<Orders> ordersPage);
}
