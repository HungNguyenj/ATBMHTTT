package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.impl.OrderDAO;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IOrderChangedService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

import java.sql.SQLException;

public class OrderChangedService implements IOrderChangedService {
    @Override
    public void orderBeingDeleted(UserModel userModel, OrderModel orderModel) {
        SendMailUtil.templateWarningOrderChanged(userModel.getFullName(), orderModel.getSlug(), userModel.getEmail(), "Xóa");
    }

    @Override
    public void orderBeingCanceled(UserModel userModel, OrderModel orderModel) {
        SendMailUtil.templateWarningOrderChanged(userModel.getFullName(), orderModel.getSlug(), userModel.getEmail(), "Hủy");
    }

    @Override
    public void orderBeingChanged(UserModel userModel, OrderModel orderModel) {
        SendMailUtil.templateWarningOrderChanged(userModel.getFullName(), orderModel.getSlug(), userModel.getEmail(), "Thay đổi");
    }

    @Override
    public void orderBeingChangeStatus(UserModel userModel, OrderModel orderModel) {
        SendMailUtil.templateWarningOrderChanged(userModel.getFullName(), orderModel.getSlug(), userModel.getEmail(), "Thay đổi trạng thái");
    }

    public static void main(String[] args) throws SQLException {
        OrderModel orderModel = new OrderModel();
        orderModel.setSlug("rerrerere");
        orderModel.setTotalAmount(34343.3);
        orderModel.setSign("sign");

        System.out.println(orderModel.toString());

        SendMailUtil.templateConfirmOrder("Hung", orderModel, "nghungg2053@gmail.com");
    }
}
