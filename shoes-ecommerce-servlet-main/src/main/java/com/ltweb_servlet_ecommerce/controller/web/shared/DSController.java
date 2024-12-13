package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.service.IDSService;
import com.ltweb_servlet_ecommerce.service.impl.DSService;
import com.ltweb_servlet_ecommerce.utils.KeyUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

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
        //Sign
        try {
            dsService = new DSService();

            DSModel dsModel = new DSModel();
            String email = "testemail@gmail.com";

            dsService.genKey();
            String sign = dsService.sign(email);

            String publickey = KeyUtil.getInstance().publicKeyToBase64(dsService.getPublicKey());
            String privatekey = KeyUtil.getInstance().privateKeyToBase64(dsService.getPrivateKey());

            dsModel.setSign(sign);
            dsModel.setPublicKey(publickey);
            dsModel.setPrivateKey(privatekey);

            System.out.println(dsModel.toString());

            req.setAttribute("sign", sign);
            req.setAttribute("publickey",publickey);

            req.getRequestDispatcher("/views/shared/digital-signature.jsp").forward(req, resp);

        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }
}
