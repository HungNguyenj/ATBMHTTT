package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;

public interface IOrderChangedService {
    public void orderBeingDeleted(UserModel userModel, OrderModel orderModel);
    public void orderBeingChanged(UserModel userModel, OrderModel orderModel);
    public void orderBeingChangeStatus(UserModel userModel, OrderModel orderModel);
}
