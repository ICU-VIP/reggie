package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.BaseContext;
import com.example.reggie.common.R;
import com.example.reggie.entity.Orders;
import com.example.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page<Orders>> page(int page, int pageSize) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        //orderService.pageOrder(ordersPage);
        orderService.page(ordersPage);
        return R.success(ordersPage);
    }

    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(int page, int pageSize) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getUserId, userId);
        orderService.page(ordersPage,  lambdaQueryWrapper);
        return R.success(ordersPage);
    }
}
