package com.example.reggie.entity;

import com.example.reggie.mapper.AddressBookMapper;
import com.example.reggie.mapper.OrderMapper;
import com.example.reggie.mapper.ShoppingCartMapper;
import com.example.reggie.mapper.UserMapper;
import com.example.reggie.service.OrderDetailService;

import lombok.Data;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//@Data
public class FullOrderData {
    private Orders orders;
    private List<OrderDetail> orderDetailList;

    public FullOrderDataBuilder Builder(UserMapper userMapper, ShoppingCartMapper shoppingCartMapper
            , AddressBookMapper addressBookMapper,
                                        OrderMapper orderMapper, OrderDetailService orderDetailService){
        return new FullOrderDataBuilder(userMapper, shoppingCartMapper, addressBookMapper, orderMapper, orderDetailService);
    }

    public static class FullOrderDataBuilder {
        private UserMapper userMapper;
        private ShoppingCartMapper shoppingCartMapper;
        private OrderMapper orderMapper;
        private OrderDetailService orderDetailService;
        private AddressBookMapper addressBookMapper;

        private Long orderId;
        private Long userId;
        private AtomicInteger amount;

        private FullOrderData fullOrderData;
        public FullOrderDataBuilder(UserMapper userMapper, ShoppingCartMapper shoppingCartMapper, AddressBookMapper addressBookMapper, OrderMapper orderMapper, OrderDetailService orderDetailService) {
             this.userMapper = userMapper;
             this.shoppingCartMapper = shoppingCartMapper;
             this.addressBookMapper = addressBookMapper;
             this.orderMapper = orderMapper;
             this.orderDetailService = orderDetailService;
        }

        public FullOrderData build(){
            return fullOrderData;
        }

    }
}
