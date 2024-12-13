package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.service.IDSService;
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
import java.security.PublicKey;

@WebServlet(urlPatterns = {"/verify-ds"})
public class VerifyDSController extends HttpServlet {

    @Inject
    IDSService dsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared//verify-ds.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Verify
        String signature = req.getParameter("mysignature");
        String publicKeyStr = req.getParameter("mypublickey");
        String email = "testemail@gmail.com";

        boolean isVerified = false;

        try {
            PublicKey publicKey = KeyUtil.base64ToPublicKey(publicKeyStr);

            dsService.loadPublic(publicKey);
             isVerified = dsService.verify(email, signature);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String verificationResult = isVerified ? "Chữ ký hợp lệ" : "Chữ ký không hợp lệ";
        req.setAttribute("verifymessage", verificationResult);
        req.getRequestDispatcher("/views/shared//verify-ds.jsp").forward(req, resp);
    }
}
