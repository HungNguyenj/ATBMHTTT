package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.service.IReportKeyService;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;
import com.ltweb_servlet_ecommerce.utils.StatusMapUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReportKeyService implements IReportKeyService {
    @Inject
    IDSService dsService;
    @Inject
    IOrderService orderService;

    @Override
    public void reportKey(DSModel dsModel, UserModel userModel) throws SQLException {
        //find order sign by this dsmodel
        List<OrderModel> listOrder = findListBySignId(dsModel.getId());
        System.out.println(listOrder.size());

        //check date create of order and handle
        for (OrderModel order : listOrder) {
            if (checkDateIsValid(order.getCreateAt(), dsModel.getReportedAt())) {
                if (!order.getStatus().equals("ORDER_DELIVERED")) {
                    order.setStatus("ORDER_CANCEL");

                    order = orderService.update(order);

                    //send mail to user
                    SendMailUtil.templateCancelOrderByRevealKey(userModel.getFullName(), order, userModel.getEmail());
                }
            }
        }


    }

    private boolean checkDateIsValid(Timestamp createAtTimestamp, Timestamp reportedAtTimestamp) {
        // Convert Timestamps to LocalDateTime
        LocalDateTime createAt = createAtTimestamp.toLocalDateTime();
        LocalDateTime reportedAt = reportedAtTimestamp.toLocalDateTime();

        // Compare dates
        return !createAt.isBefore(reportedAt); // True if createAt >= reportedAt
    }

    private List<OrderModel> findListBySignId(Long id) throws SQLException {
        List<OrderModel> o = orderService.findAll(null);
        List<OrderModel> result = new ArrayList<>();
        for (OrderModel order : o) {
            if (order.getSignByDSId() !=null && order.getSignByDSId().equals(id)) {
                result.add(order);
            }
        }
        return result;
    }

}
