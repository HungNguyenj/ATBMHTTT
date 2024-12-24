package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.impl.DSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/digital-signature"})
public class DSController extends HttpServlet {

    IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/digital-signature.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        }
        else {
            //Sign
            try {

                dsService = new DSService();

                dsService.genKey();

                String publickey = KeyUtil.getInstance().publicKeyToBase64(dsService.getPublicKey());
                String privatekey = KeyUtil.getInstance().privateKeyToBase64(dsService.getPrivateKey());

                DSModel dsModel = new DSModel();
                dsModel.setUser_id(userModel.getId());
                dsModel.setUsedNow(1);

                DSModel tempModel = dsService.findWithFilter(dsModel);
                DSModel temp;

                dsModel.setPublic_key(publickey);
                dsModel.setPrivate_key(privatekey);

                if (tempModel == null) {
                    temp = dsService.save(dsModel);
                } else {
                    tempModel.setUsedNow(2);
                    tempModel.setExpiredTime(tempModel.getExpiredTime());
                    tempModel = dsService.update(tempModel);

                    temp = dsService.save(dsModel);
                }

                System.out.println(temp.toString());

                req.setAttribute("privatekey", privatekey);
                req.setAttribute("publickey",publickey);

                req.getRequestDispatcher("/views/shared/digital-signature.jsp").forward(req, resp);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
