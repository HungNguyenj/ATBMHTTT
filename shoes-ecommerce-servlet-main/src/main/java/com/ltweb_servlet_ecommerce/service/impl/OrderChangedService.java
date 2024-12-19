package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IOrderChangedService;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

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
}
