package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.service.impl.DSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/sign-order"})
public class SignIntoOrderController extends HttpServlet {
    IDSService dsService;
    @Inject
    IOrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/sign-into-order.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean handleSign = Boolean.parseBoolean(req.getParameter("handlesign"));

        String slug = req.getParameter("slug");
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        } else {
            try {
                //find orders
                OrderModel tempOrder = new OrderModel();
                tempOrder.setSlug(slug);
                OrderModel orderModel = orderService.findWithFilter(tempOrder);

                if (handleSign) {
                    dsService = new DSService();

                    DSModel temp = new DSModel();
                    temp.setUser_id(userModel.getId());
                    temp.setUsedNow(1);
                    DSModel dsModel = dsService.findWithFilter(temp);
                    System.out.println("ds to sign: " + dsModel.toString());

//                    if (dsModel.getPublic_key().equals(publickeyStr)
//                            && dsModel.getPrivate_key().equals(privatekeyStr)) {
                    PublicKey publicKey = KeyUtil.getInstance().base64ToPublicKey(dsModel.getPublic_key());
                    PrivateKey privateKey = KeyUtil.getInstance().base64ToPrivateKey(dsModel.getPrivate_key());

                    String signContent = slug;

                    dsService.loadPublic(publicKey);
                    dsService.loadPrivate(privateKey);

                    String sign = dsService.sign(signContent);
                    System.out.println(sign);

                    orderModel.setSign(sign);
                    orderModel.setSignByDSId(dsModel.getId());
                    orderModel.setVerified(true);

                    OrderModel or = orderService.update(orderModel);
                    System.out.println(or.toString());
                    SendMailUtil.templateConfirmOrder(userModel.getFullName(), orderModel, userModel.getEmail());

                    resp.sendRedirect("/success-order/" + orderModel.getSlug());
//                    }
                } else {
                    resp.sendRedirect("/success-order/" + orderModel.getSlug());
                }

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
