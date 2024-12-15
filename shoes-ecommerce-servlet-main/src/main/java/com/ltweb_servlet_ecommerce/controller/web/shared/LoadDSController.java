package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.impl.DSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

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

@WebServlet(urlPatterns = {"/load-ds"})
public class LoadDSController extends HttpServlet {
    IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/load-ds.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        } else {
            //load
            String publickeyStr = req.getParameter("publickey");
            String privatekeyStr = req.getParameter("privatekey");

            if (publickeyStr.isEmpty() || privatekeyStr.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/load-ds?message=field_is_blank&toast=danger");
            } else {
                try {
                    dsService = new DSService();

                    PublicKey publicKey = KeyUtil.base64ToPublicKey(publickeyStr);
                    PrivateKey privateKey = KeyUtil.base64ToPrivateKey(privatekeyStr);

                    dsService.loadPublic(publicKey);
                    dsService.loadPrivate(privateKey);

                    DSModel dsModel = new DSModel();
                    dsModel.setUser_id(userModel.getId());

                    DSModel tempModel = dsService.findWithFilter(dsModel);
                    DSModel temp;

                    if (tempModel == null) {
                        temp = dsService.save(dsModel);
                    } else {
                        dsModel.setId(tempModel.getId());
                        temp = dsService.update(dsModel);
                    }
                    System.out.println(temp.toString());

                    resp.sendRedirect(req.getContextPath() + "/user-info?message=update_info_success&toast=success");

                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchProviderException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    resp.sendRedirect(req.getContextPath() + "/load-ds?message=key_not_valid&toast=danger");
                }
            }
        }
    }
}
