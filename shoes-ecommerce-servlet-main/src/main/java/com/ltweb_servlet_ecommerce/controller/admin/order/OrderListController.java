package com.ltweb_servlet_ecommerce.controller.admin.order;

import com.google.gson.JsonParser;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.CartUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.PagingUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/order/list"})
public class OrderListController extends HttpServlet {
    @Inject
    IOrderService orderService;
    @Inject
    IAddressService addressService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    IProductService productService;
    @Inject
    IProductSizeService productSizeService;
    @Inject
    ISizeService sizeService;
    @Inject
    IUserService userService;
    @Inject
    IUserOrderService userOrderService;
    @Inject
    IOrderChangedService orderChangedService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
//            Pageble orderPaging = PagingUtil.setUpPagingAndSort(req);
//            orderPaging.getSorter().setSortName("createAt");
//            orderPaging.getSorter().setSortBy("DESC");
            List<OrderModel> listOrder = orderService.findAll(null);
            for (int i = 0; i < listOrder.size(); i++) {
                OrderModel orderModel = listOrder.get(i);
                AddressModel addressModel = addressService.findById(orderModel.getAddressId());
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setOrderId(orderModel.getId());
                List<OrderDetailsModel> listOrderDetails = orderDetailsService.findAllWithFilter(orderDetailsModel, null);
                List<ProductModel> listProduct = new ArrayList<>();
                for (OrderDetailsModel orderDetails : listOrderDetails) {
                    new CartUtil().setListProductFromOrderDetails(listProduct, orderDetails, productSizeService, productService, sizeService);
                }
                orderModel.setAddressModel(addressModel);
                orderModel.setListProduct(listProduct);
                listOrder.set(i, orderModel);
            }
            req.setAttribute("LIST_ORDER", listOrder);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/order/list.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("/admin?message=error&toast=danger");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        JsonParser parser = new JsonParser();
        try {
            long id = parser.parse(request.getReader()).getAsLong();
            boolean isDeleted = orderService.softDelete(id);

            //send mail to user
            OrderModel order = orderService.findById(id);
            UserOrderModel tempuo = new UserOrderModel();
            tempuo.setOrderId(id);
            UserOrderModel userOrderModel = userOrderService.findWithFilter(tempuo);
            UserModel userModel = userService.findById(userOrderModel.getUserId());

            System.out.println("send mail");
            orderChangedService.orderBeingDeleted(userModel, order);

            response.setStatus(isDeleted ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
